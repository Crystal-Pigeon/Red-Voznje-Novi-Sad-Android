package com.crystalpigeon.busnovisad.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.crystalpigeon.busnovisad.R
import kotlinx.android.synthetic.main.schedule_hour.view.*
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.LinkedHashMap

class ScheduleHoursAdapter : RecyclerView.Adapter<ScheduleHoursAdapter.ViewHolder>() {
    private var schedule: LinkedHashMap<String, ArrayList<String>>? = null
    private var hours: ArrayList<String>? = null
    private var hoursCollapsed: ArrayList<String> = ArrayList()
    private var hoursExpanded: ArrayList<String> = ArrayList()
    private var collapsed: Boolean = true
    private var currentHour: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.schedule_hour,
                parent,
                false
            )
        )

    override fun getItemCount() = hours?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(hours?.get(position))

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(hour: String?) {
            view.hour.text = hour
            currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
            val hourColor =
                if (currentHour == hour?.toInt()) {
                    R.color.colorOrange
                } else
                    R.color.textColor

            view.hour.setTextColor(ContextCompat.getColor(view.context, hourColor))
            view.minutes.text = schedule?.get(hour)?.joinToString(" ")
        }
    }

    fun setSchedule(schedule: LinkedHashMap<String, ArrayList<String>>) {
        this.schedule = schedule
        currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        hoursExpanded = ArrayList(schedule.keys)
        hoursCollapsed = getCollapsedList(hoursExpanded, currentHour)

        if (collapsed) {
            this.hours = hoursCollapsed
        } else {
            this.hours = hoursExpanded
        }
        notifyDataSetChanged()
    }

    fun getCollapsedList(hoursExpanded: ArrayList<String>, currentHour: Int): ArrayList<String> {
        val collapsed: ArrayList<String> = ArrayList()
        for (i in 0 until hoursExpanded.size) {
            if (currentHour == hoursExpanded[i].toInt()) {//There is exact hour
                if (i > 0) {//There is one before
                    collapsed.add(hoursExpanded[i - 1])
                    collapsed.add(hoursExpanded[i])
                    collapsed.add(hoursExpanded.getOrNull(i + 1) ?: break)
                } else {//There is no one before
                    collapsed.add(hoursExpanded[i])
                    collapsed.add(hoursExpanded.getOrNull(i + 1) ?: break)
                    collapsed.add(hoursExpanded.getOrNull(i + 2) ?: break)
                }
                break
            } else if (hoursExpanded[i].toInt() > currentHour) {
                //There is no current hour, we need to get the first next
                collapsed.add(hoursExpanded[i])
                collapsed.add(hoursExpanded.getOrNull(i + 1) ?: break)
                collapsed.add(hoursExpanded.getOrNull(i + 2) ?: break)
                break
            }
        }

        if (collapsed.size < 3) {
            hoursExpanded.forEach {
                if (collapsed.size < 3) {
                   if(collapsed.indexOf(it) == -1)
                       collapsed.add(it)
                } else {
                    return@forEach
                }
            }
        }

        return collapsed
    }

    fun collapseExpand() {
        collapsed = !collapsed
        if (collapsed) {
            this.hours = hoursCollapsed
        } else {
            this.hours = hoursExpanded
        }
        notifyDataSetChanged()
    }
}