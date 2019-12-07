package com.crystalpigeon.busnovisad.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        day = arguments?.getString("DAY")

        view.rv_schedule_for_lines.layoutManager = LinearLayoutManager(context)
        scheduleAdapter = ScheduleAdapter(arrayListOf(), context!!)
        rv_schedule_for_lines.adapter = scheduleAdapter

        viewModel.favorites.observe(this,
            Observer { listOfSchedule ->
                if (listOfSchedule.isNotEmpty()) {
                    noLinesGroup.visibility = View.GONE
                    rv_schedule_for_lines.visibility = View.VISIBLE
                    scheduleAdapter.schedules = ArrayList(listOfSchedule)
                    scheduleAdapter.notifyDataSetChanged()
                } else {
                    noLinesGroup.visibility = View.VISIBLE
                }
            })

        day?.let { viewModel.getFavorites(it) }
    }
}