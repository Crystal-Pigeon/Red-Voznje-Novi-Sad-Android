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

    private var scheduleAdapter: ScheduleAdapter? = null
    private var listOfSchedules: ArrayList<Schedule> = ArrayList()
    private var hashMapA: LinkedHashMap<String, ArrayList<String>> = linkedMapOf()
    private var hashMapB: LinkedHashMap<String, ArrayList<String>> = linkedMapOf()
    private var hashMapC: LinkedHashMap<String, ArrayList<String>> = linkedMapOf()

    companion object {
        fun newInstance(): ScheduleFragment {
            return ScheduleFragment()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hashMapA["10"] = arrayListOf("10", "30")
        hashMapA["18"] = arrayListOf("20", "30", "40")
        hashMapA["0"] = arrayListOf("05", "50")

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
                null
            )
        )

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

        view.rv_schedule_for_lines.layoutManager = LinearLayoutManager(context)
        if (listOfSchedules.isNotEmpty()) {
            rv_schedule_for_lines.adapter = scheduleAdapter
            rv_schedule_for_lines.visibility = View.VISIBLE
            scheduleAdapter = ScheduleAdapter(listOfSchedules, context!!)
        }

    }
}
