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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        val listOfSchedule = mockListOfSchedules()

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

    private fun mockListOfSchedules(): ArrayList<Schedule>{
        val listOfSchedules = ArrayList<Schedule>()
        val hashMapA: LinkedHashMap<String, ArrayList<String>> = linkedMapOf()
        val hashMapB: LinkedHashMap<String, ArrayList<String>> = linkedMapOf()
        val hashMapC: LinkedHashMap<String, ArrayList<String>> = linkedMapOf()
        hashMapA["10"] = arrayListOf("10", "30")
        hashMapA["11"] = arrayListOf("10", "30")
        hashMapA["13"] = arrayListOf("10", "16", "23", "30", "32", "36", "45", "56", "57")
        hashMapA["18"] = arrayListOf("20", "30", "40")
        hashMapA["23"] = arrayListOf("20", "30", "40")
        hashMapA["00"] = arrayListOf("05", "50")

        hashMapB["10"] = arrayListOf("20", "30", "40")
        hashMapB["22"] = arrayListOf("15", "45")

        hashMapC["13"] = arrayListOf("03", "40")


        listOfSchedules.add(
            Schedule(
                "52",
                "52",
                "VETERNIK",
                null,
                "Polasci za VETERNIK",
                "Polasci iz VETERNIK",
                "R",
                null,
                hashMapA,
                hashMapB,
                null
            )
        )
        listOfSchedules.add(
            Schedule(
                "69",
                "69",
                "CARDAK",
                "CARDAK-KAMENICA",
                null,
                null,
                "S",
                hashMapC,
                null,
                null,
                ""
            )
        )
        return listOfSchedules
    }
}
