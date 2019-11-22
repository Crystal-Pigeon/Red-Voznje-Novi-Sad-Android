package com.crystalpigeon.busnovisad.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.crystalpigeon.busnovisad.R
import com.crystalpigeon.busnovisad.model.ScheduleResponse
import kotlinx.android.synthetic.main.schedule_item.view.*

class ScheduleAdapter(schedules: ArrayList<ScheduleResponse>?) :
    RecyclerView.Adapter<ScheduleAdapter.ViewHolder>() {

    private var schedules: ArrayList<ScheduleResponse>? = null

    init {
        this.schedules = schedules
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
        fun bind(schedule: ScheduleResponse?) {
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

}