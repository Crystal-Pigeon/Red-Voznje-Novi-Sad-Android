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
import com.crystalpigeon.busnovisad.view.MainActivity
import com.crystalpigeon.busnovisad.view.adapter.ScheduleAdapter
import com.crystalpigeon.busnovisad.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_schedule.*
import kotlinx.android.synthetic.main.fragment_schedule.view.*
import javax.inject.Inject

class ScheduleFragment : Fragment() {

    @Inject
    lateinit var viewModel: MainViewModel
    private lateinit var scheduleAdapter: ScheduleAdapter
    private var day: String? = ""
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
        day = arguments?.getString("DAY")

        viewModel.getFavorites(day?:"R").observe(this,
            Observer { listOfSchedule ->
                if (listOfSchedule.isNotEmpty()) {
                    noLinesGroup.visibility = View.GONE
                    rv_schedule_for_lines.visibility = View.VISIBLE
                    scheduleAdapter.updateSchedule(ArrayList(listOfSchedule))
                } else {
                    noLinesGroup.visibility = View.VISIBLE
                }
            })

        viewModel.isLoading.observe(this, Observer {
            if (it){
                noLinesGroup.visibility = View.GONE
                rv_schedule_for_lines.visibility = View.VISIBLE
            } else {
                noLinesGroup.visibility = View.VISIBLE
                rv_schedule_for_lines.visibility = View.GONE
            }
            scheduleAdapter.loadingStarted(it)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.rv_schedule_for_lines.layoutManager = LinearLayoutManager(context)
        scheduleAdapter = ScheduleAdapter(arrayListOf(), context!!)
        rv_schedule_for_lines.adapter = scheduleAdapter
        navController =
            Navigation.findNavController(activity as MainActivity, R.id.nav_host_fragment)

        (activity as MainActivity).getSettingsButton().visibility = View.VISIBLE
        (activity as MainActivity).getSettingsButton().setOnClickListener {
            navController.navigate(R.id.action_mainFragment_to_settingsFragment)
        }
    }
}