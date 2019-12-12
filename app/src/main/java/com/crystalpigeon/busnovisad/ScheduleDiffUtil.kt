package com.crystalpigeon.busnovisad

import androidx.recyclerview.widget.DiffUtil
import com.crystalpigeon.busnovisad.model.entity.Schedule

class ScheduleDiffUtil(val oldSchdules: ArrayList<Schedule>, val newSchedules: ArrayList<Schedule>): DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldSchdules[oldItemPosition].id == newSchedules[newItemPosition].id
    }

    override fun getOldListSize() = oldSchdules.size

    override fun getNewListSize() = newSchedules.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldSchdules[oldItemPosition] == newSchedules[newItemPosition]
    }
}