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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.schedule_hour,
                parent,
                false
            )
        )

    override fun getItemCount() = schedule?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(hours?.get(position))

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        fun bind(hour: String?) {
            view.hour.text = hour
            val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
            val hourColor =
                if (currentHour.toString() == hour)
                    R.color.colorOrange
                else
                    R.color.textColor

            view.hour.setTextColor(ContextCompat.getColor(view.context, hourColor))
            view.minutes.text = schedule?.get(hour)?.joinToString(" ")
        }
    }

    fun setSchedule(schedule: LinkedHashMap<String, ArrayList<String>>) {
        this.schedule = schedule
        this.hours = ArrayList(schedule.keys)
        notifyDataSetChanged()
    }
}