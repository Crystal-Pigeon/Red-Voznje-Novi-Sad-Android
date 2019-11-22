package com.crystalpigeon.busnovisad.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.crystalpigeon.busnovisad.R
import com.crystalpigeon.busnovisad.model.ScheduleResponse
import com.crystalpigeon.busnovisad.view.adapter.ScheduleAdapter
import kotlinx.android.synthetic.main.fragment_schedule.*
import kotlinx.android.synthetic.main.fragment_schedule.view.*


class ScheduleFragment : Fragment() {

    private var scheduleAdapter: ScheduleAdapter? = null
    private var listOfSchedules: ArrayList<ScheduleResponse>? = null

    companion object Test {
        fun newInstance(): ScheduleFragment {
            return ScheduleFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_schedule, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.rv_schedule_for_lines.layoutManager = LinearLayoutManager(context)
        if (listOfSchedules != null && listOfSchedules!!.isNotEmpty()) {
            rv_schedule_for_lines.adapter = scheduleAdapter
            rv_schedule_for_lines.visibility = View.VISIBLE
            scheduleAdapter = ScheduleAdapter(listOfSchedules)
        }

    }
}
