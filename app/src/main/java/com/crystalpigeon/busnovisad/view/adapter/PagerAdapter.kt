package com.crystalpigeon.busnovisad.view.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.crystalpigeon.busnovisad.R
import com.crystalpigeon.busnovisad.view.fragment.ScheduleFragment

class PagerAdapter(fm: FragmentManager, context: Context) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private var context: Context? = null
    
    init {
        this.context = context
    }
    
    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> ScheduleFragment.newInstance("R")
            1 -> ScheduleFragment.newInstance("S")
            else -> ScheduleFragment.newInstance("N")
        }
    }

    override fun getCount(): Int = 3

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> context?.getString(R.string.work_day)
            1 -> context?.getString(R.string.saturday)
            2 -> context?.getString(R.string.sunday)
            else -> ""
        }
    }
}