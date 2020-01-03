package com.crystalpigeon.busnovisad.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.crystalpigeon.busnovisad.model.entity.Schedule
import kotlinx.android.synthetic.main.schedule_item.view.*
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.DiffUtil
import com.crystalpigeon.busnovisad.R
import com.crystalpigeon.busnovisad.ScheduleDiffUtil
import kotlinx.android.synthetic.main.loader.view.*
import androidx.appcompat.app.AlertDialog
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class ScheduleAdapter(
    var schedules: ArrayList<Schedule>,
    val context: Context,
    onScheduleClicked: OnScheduleClicked
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var loading: Boolean = false
    var onScheduleClicked: OnScheduleClicked? = null

    init {
        this.onScheduleClicked = onScheduleClicked
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == SCHEDULE) {
            return ViewHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.schedule_item,
                    parent,
                    false
                ), this.onScheduleClicked
            )
        } else {
            return LoaderHolder(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.loader,
                    parent,
                    false
                )
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (loading && position == itemCount - 1) {
            return LOADER
        }

        return SCHEDULE
    }

    override fun getItemCount(): Int = if (loading) schedules.size + 1 else schedules.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == SCHEDULE) {
            (holder as ViewHolder).bind(schedules.elementAt(position))
        } else {
            (holder as LoaderHolder).bind()
        }
    }

    fun loadingStarted(loading: Boolean) {
        this.loading = loading
        if (loading) {
            notifyItemInserted(schedules.size + 1)
        } else {
            notifyItemRemoved(schedules.size)
        }
    }

    inner class LoaderHolder(val view: View) : RecyclerView.ViewHolder(view) {
        init {
            if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
                view.logo.setImageResource(R.drawable.logo_dark)
            val scaleDown = ObjectAnimator.ofPropertyValuesHolder(
                view.logo,
                PropertyValuesHolder.ofFloat("scaleX", 1.2f),
                PropertyValuesHolder.ofFloat("scaleY", 1.2f)
            )
            scaleDown.duration = 500

            scaleDown.repeatCount = ObjectAnimator.INFINITE
            scaleDown.repeatMode = ObjectAnimator.REVERSE

            scaleDown.start()
        }

        fun bind() {

        }
    }

    inner class ViewHolder(val view: View, onScheduleClicked: OnScheduleClicked?) : RecyclerView.ViewHolder(view) {
        private val scheduleAAdapter = ScheduleHoursAdapter()
        private val scheduleBAdapter = ScheduleHoursAdapter()
        private var onScheduleClicked : OnScheduleClicked ?= null

        init {
            view.scheduleA.layoutManager = LinearLayoutManager(context)
            view.scheduleB.layoutManager = LinearLayoutManager(context)
            view.scheduleA.adapter = scheduleAAdapter
            view.scheduleB.adapter = scheduleBAdapter
            this.onScheduleClicked = onScheduleClicked
            view.setOnClickListener {
                schedules[adapterPosition].collapsed = !schedules[adapterPosition].collapsed
                notifyItemChanged(adapterPosition)
            }
        }

        fun bind(schedule: Schedule) {
            view.circle_id.text = schedule.number
            view.lineName.text = schedule.name
            if (schedule.lane != null) {
                //Bus with one direction
                view.groupB.visibility = View.GONE
                view.directionA.text = schedule.lane
                schedule.schedule?.let { scheduleAAdapter.setSchedule(it, schedule.collapsed) }
            } else {
                //Bus with direction A and B
                view.groupB.visibility = View.VISIBLE
                view.directionA.text = schedule.directionA
                view.directionB.text = schedule.directionB
                schedule.scheduleA?.let { scheduleAAdapter.setSchedule(it, schedule.collapsed) }
                schedule.scheduleB?.let { scheduleBAdapter.setSchedule(it, schedule.collapsed) }
            }

            if (schedule.extras != null && !schedule.collapsed) {
                view.extras.visibility = View.VISIBLE
                view.extras.text = schedule.extras
            } else {
                view.extras.visibility = View.GONE
            }

            view.setOnLongClickListener {
                AlertDialog.Builder(context, R.style.AlertDialogStyle)
                    .setTitle(view.lineName.text)
                    .setMessage(R.string.are_you_sure_you_want_to_remove_line)
                    .setPositiveButton(R.string.delete) { dialog, which ->
                        var position = schedules.indexOf(schedule)
                        schedules.remove(schedule)
                        GlobalScope.launch {
                            onScheduleClicked?.onScheduleClicked(schedule, position)
                        }
                        notifyItemRemoved(position)
                    }
                    .setNegativeButton(R.string.cancel, null)
                    .show()
                return@setOnLongClickListener true
            }
        }
    }


    companion object {
        private const val LOADER = 1
        private const val SCHEDULE = 2
    }

    fun updateSchedule(schedules: ArrayList<Schedule>) {
        val difResult = DiffUtil.calculateDiff(ScheduleDiffUtil(this.schedules, schedules))
        this.schedules = schedules
        difResult.dispatchUpdatesTo(this)
    }

    interface OnScheduleClicked {
        suspend fun onScheduleClicked(schedule: Schedule, position: Int)
    }
}