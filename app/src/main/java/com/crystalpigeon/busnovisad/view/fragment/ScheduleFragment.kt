package com.crystalpigeon.busnovisad.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.crystalpigeon.busnovisad.R
import com.crystalpigeon.busnovisad.model.Schedule
import com.crystalpigeon.busnovisad.view.adapter.ScheduleAdapter
import kotlinx.android.synthetic.main.fragment_schedule.*
import kotlinx.android.synthetic.main.fragment_schedule.view.*

import com.crystalpigeon.busnovisad.view.MainActivity

class ScheduleFragment : Fragment() {

    private lateinit var scheduleAdapter: ScheduleAdapter

    companion object {
        fun newInstance(): ScheduleFragment {
            return ScheduleFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as MainActivity).hideBackButton()
        return inflater.inflate(R.layout.fragment_schedule, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val listOfSchedule = arrayListOf<Schedule>()

        view.rv_schedule_for_lines.layoutManager = LinearLayoutManager(context)
        scheduleAdapter = ScheduleAdapter(arrayListOf(), context!!)
        rv_schedule_for_lines.adapter = scheduleAdapter

        if (listOfSchedule.isNotEmpty()) {
            noLinesGroup.visibility = View.GONE
            rv_schedule_for_lines.visibility = View.VISIBLE
            scheduleAdapter.schedules = listOfSchedule
            scheduleAdapter.notifyDataSetChanged()
        } else {
            noLinesGroup.visibility = View.VISIBLE
        }

    }
}
