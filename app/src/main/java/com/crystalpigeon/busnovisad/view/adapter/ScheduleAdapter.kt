package com.crystalpigeon.busnovisad.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.crystalpigeon.busnovisad.R
import com.crystalpigeon.busnovisad.model.Schedule
import kotlinx.android.synthetic.main.schedule_item.view.*
import kotlin.collections.ArrayList
import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager

class ScheduleAdapter(var schedules: ArrayList<Schedule>, val context: Context) :
    RecyclerView.Adapter<ScheduleAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.schedule_item,
                parent,
                false
            )
        )

    override fun getItemCount(): Int = schedules.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(schedules.elementAt(position))

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        private val scheduleAAdapter = ScheduleHoursAdapter()
        private val scheduleBAdapter = ScheduleHoursAdapter()

        init {
            view.scheduleA.layoutManager = LinearLayoutManager(context)
            view.scheduleB.layoutManager = LinearLayoutManager(context)
            view.scheduleA.adapter = scheduleAAdapter
            view.scheduleB.adapter = scheduleBAdapter
        }

        fun bind(schedule: Schedule) {
            view.circle_id.text = schedule.number
            view.lineName.text = schedule.name

            if (schedule.lane != null) {
                //Bus with one direction
                view.groupB.visibility = View.GONE
                view.directionA.text = schedule.lane
                schedule.schedule?.let { scheduleAAdapter.setSchedule(it) }
            } else {
                //Bus with direction A and B
                view.groupB.visibility = View.VISIBLE
                view.directionA.text = schedule.directionA
                view.directionB.text = schedule.directionB
                schedule.scheduleA?.let { scheduleAAdapter.setSchedule(it) }
                schedule.scheduleB?.let { scheduleBAdapter.setSchedule(it) }
            }

            if (schedule.extras != null) {
                view.extras.visibility = View.VISIBLE
                view.extras.text = schedule.extras
            } else {
                view.extras.visibility = View.GONE
            }
        }
    }
}