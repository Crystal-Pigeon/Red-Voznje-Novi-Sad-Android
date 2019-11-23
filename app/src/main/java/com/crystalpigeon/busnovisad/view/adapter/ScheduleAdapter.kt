package com.crystalpigeon.busnovisad.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.crystalpigeon.busnovisad.R
import com.crystalpigeon.busnovisad.model.Schedule
import kotlinx.android.synthetic.main.schedule_item.view.*
import java.util.*
import kotlin.collections.ArrayList


class ScheduleAdapter(schedules: ArrayList<Schedule>?) :
    RecyclerView.Adapter<ScheduleAdapter.ViewHolder>() {

    private var schedules: ArrayList<Schedule>? = null
    private var currentHours: Int? = null

    init {
        this.schedules = schedules
        currentHours = Calendar.getInstance().time.hours
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.schedule_item, parent, false)
        )
    }

    override fun getItemCount(): Int = schedules?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(schedules?.elementAt(position))
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(schedule: Schedule?) {
            view.circle_id.text = schedule?.number
            view.lineName.text = schedule?.name

            if (schedule?.lane != null) {
                view.dir.visibility = View.VISIBLE
                view.dir1.visibility = View.GONE
                view.dir2.visibility = View.GONE
                view.layoutRasp.visibility = View.VISIBLE
                view.layoutRaspA.visibility = View.GONE
                view.layoutRaspB.visibility = View.GONE
                view.dir.text = schedule.lane
                val sb = StringBuilder()
                val keys = schedule.schedule?.keys
                val hour = findClosestHour(ArrayList(keys))
                keys?.forEach { key ->
                    sb.append("$key: ")
                    schedule.schedule[key]?.forEach { value ->
                        sb.append("$value ")
                    }
                    sb.append("\n")
                }
                view.rasp.text = sb.toString()

            } else {
                view.dir.visibility = View.GONE
                view.dir1.visibility = View.VISIBLE
                view.dir2.visibility = View.VISIBLE
                view.layoutRasp.visibility = View.GONE
                view.layoutRaspA.visibility = View.VISIBLE
                view.layoutRaspB.visibility = View.VISIBLE
                view.dir1.text = schedule?.directionA
                view.dir2.text = schedule?.directionB
                val sb = StringBuilder()
                val keysA = schedule?.scheduleA?.keys
                val hour = findClosestHour(ArrayList(keysA))
                keysA?.forEach { key ->
                    sb.append("$key: ")
                    schedule.scheduleA[key]?.forEach { value ->
                        sb.append("$value ")
                    }
                    sb.append("\n")
                }
                view.raspA.text = sb.toString()

                val sb1 = StringBuilder()
                val keysB = schedule?.scheduleB?.keys
                keysB?.forEach { key ->
                    sb1.append("$key: ")
                    schedule.scheduleB[key]?.forEach { value ->
                        sb1.append("$value ")
                    }
                    sb1.append("\n")
                }
                view.raspB.text = sb1.toString()
            }


        }
    }

    fun findClosestHour(hours: ArrayList<String>?): Int {
        val hoursArrayList: ArrayList<Int> = ArrayList()
        hours?.forEach {
            hoursArrayList.add(Integer.parseInt(it))
        }
        var start = 0
        var ans = -1
        var end = 0
        if (hoursArrayList.size > 0) {
            end = hoursArrayList.size - 1
        }
        while (start <= end) {
            val mid = (start + end) / 2
            if (hoursArrayList[mid] < currentHours!!)
                start = mid + 1
            else {
                ans = mid
                end = mid - 1
            }
        }
        return hoursArrayList[ans]
    }

}