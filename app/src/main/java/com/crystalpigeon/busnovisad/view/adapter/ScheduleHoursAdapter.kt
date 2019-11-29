package com.crystalpigeon.busnovisad.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.crystalpigeon.busnovisad.R
import kotlinx.android.synthetic.main.schedule_hour.view.*
import java.lang.IndexOutOfBoundsException
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.LinkedHashMap


class ScheduleHoursAdapter : RecyclerView.Adapter<ScheduleHoursAdapter.ViewHolder>() {
    private var schedule: LinkedHashMap<String, ArrayList<String>>? = null
    private var hours: ArrayList<String>? = null
    private var hoursCollapsed: ArrayList<String> = ArrayList()
    private var collapsed: Boolean = true
    private var currentHour: Int? = null

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
        //prvi put ce biti collapsovano
        val arrListHours: ArrayList<String> = ArrayList()
        if (collapsed) {
            this.schedule!!.keys.forEach { hour ->
                arrListHours.add(hour)
            }
            arrListHours.forEach { hour ->
                if (currentHour == hour.toInt()) {
                    val index = arrListHours.indexOf(hour)
                    if (index != -1) {
                        if (index - 1 >= 0) {
                            val smallerHour = arrListHours[index - 1]
                            hoursCollapsed.add(smallerHour)
                        }
                        hoursCollapsed.add(hour)
                        if (index + 1 < arrListHours.size) {
                            val biggerHour = arrListHours[index + 1]
                            hoursCollapsed.add(biggerHour)
                        }
                    }
                }
            }
            if (hoursCollapsed.isEmpty()) {
                arrListHours.forEach { hour ->
                    if (currentHour!! < hour.toInt()) {
                        if (hoursCollapsed.size < 3) {
                            hoursCollapsed.add(hour)
                        }
                    }
                }
            }
            //ako je jos uvek prazna..
            if (hoursCollapsed.size < 3) {
                arrListHours.forEach {
                    if (hoursCollapsed.size < 3) {
                        hoursCollapsed.add(it)
                    }
                }
            }
            this.hours = hoursCollapsed
        } else {
            this.hours = ArrayList(schedule.keys)
        }
        notifyDataSetChanged()
    }

    fun collapseExpand() {
        if (collapsed) {
            collapsed = false
            this.hours = ArrayList(schedule!!.keys)
            notifyDataSetChanged()
        } else {
            collapsed = true
            this.hours = hoursCollapsed
            notifyDataSetChanged()
        }
    }
}