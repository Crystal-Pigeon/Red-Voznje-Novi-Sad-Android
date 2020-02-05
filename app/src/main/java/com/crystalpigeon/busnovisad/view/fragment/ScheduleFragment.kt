package com.crystalpigeon.busnovisad.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.crystalpigeon.busnovisad.BusNsApp
import com.crystalpigeon.busnovisad.R
import com.crystalpigeon.busnovisad.model.entity.Schedule
import com.crystalpigeon.busnovisad.view.MainActivity
import com.crystalpigeon.busnovisad.view.adapter.ScheduleAdapter
import com.crystalpigeon.busnovisad.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_schedule.*
import kotlinx.android.synthetic.main.fragment_schedule.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class ScheduleFragment : Fragment(), ScheduleAdapter.OnScheduleClicked {
    @Inject
    lateinit var viewModel: MainViewModel
    private lateinit var scheduleAdapter: ScheduleAdapter
    private var day: String = ""
    lateinit var navController: NavController

    companion object {
        fun newInstance(day: String): ScheduleFragment {
            val fragment = ScheduleFragment()
            val bundle = Bundle()
            bundle.putString("DAY", day)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as MainActivity).hideBackButton()
        BusNsApp.app.component.inject(this)
        return inflater.inflate(R.layout.fragment_schedule, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        day = arguments?.getString("DAY") ?: ""

        viewModel.getFavorites(day).observe(this,
            Observer { listOfSchedule ->
                if (listOfSchedule.isNotEmpty()) {
                    listOfSchedule.map { schedule ->
                        schedule.directionA = translateDirection(schedule.directionA ?: "")
                        schedule.directionB = translateDirection(schedule.directionB ?: "")
                    }

                    noLinesGroup.visibility = View.GONE
                    rv_schedule_for_lines.visibility = View.VISIBLE
                    for (schedule in listOfSchedule) {
                        schedule.extras = formattedExtras(schedule.extras)
                    }
                    scheduleAdapter.updateSchedule(ArrayList(listOfSchedule))
                } else {
                    noLinesGroup.visibility = View.VISIBLE
                    rv_schedule_for_lines.visibility = View.GONE
                }
            })

        viewModel.isLoading.observe(this, Observer { loading ->
            loading.getContentIfNotHandled()?.let {
                if (it) {
                    noLinesGroup.visibility = View.GONE
                    rv_schedule_for_lines.visibility = View.VISIBLE
                } else {
                    noLinesGroup.visibility = View.VISIBLE
                    if (scheduleAdapter.itemCount == 0) {
                        rv_schedule_for_lines.visibility = View.GONE
                    }
                }
                scheduleAdapter.loadingStarted(it)
            }
        })
    }

    private fun formattedExtras(extras: String?): String {
        val extrasList = extras?.split(",")?.toTypedArray()
        var returnValue = ""
        if (extrasList != null) {
            for (element in extrasList) {
                if (element.contains("="))
                    returnValue += "$element, "
            }
        } else return ""
        if (returnValue != "" && returnValue.last() == ' ') returnValue = returnValue.dropLast(2)

        return returnValue
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.rv_schedule_for_lines.layoutManager = LinearLayoutManager(context)
        scheduleAdapter = ScheduleAdapter(arrayListOf(), context!!, this)
        rv_schedule_for_lines.adapter = scheduleAdapter
        navController =
            Navigation.findNavController(activity as MainActivity, R.id.nav_host_fragment)

        (activity as MainActivity).getSettingsButton().visibility = View.VISIBLE
        (activity as MainActivity).getSettingsButton().setOnClickListener {
            navController.navigate(R.id.action_mainFragment_to_settingsFragment)
        }
    }

    override fun onScheduleClicked(schedule: Schedule, position: Int) {
        GlobalScope.launch {
            viewModel.removeSchedule(schedule)
        }
    }

    private fun translateDirection(direction: String): String? {
        var res: String? = null
        if (direction.contains("Polasci za")) {
            res = direction.replace("Polasci za", getString(R.string.departure_to))
        } else if (direction.contains("Polasci iz")) {
            res = direction.replace("Polasci iz", getString(R.string.departure_from))
        }

        return res ?: direction
    }
}