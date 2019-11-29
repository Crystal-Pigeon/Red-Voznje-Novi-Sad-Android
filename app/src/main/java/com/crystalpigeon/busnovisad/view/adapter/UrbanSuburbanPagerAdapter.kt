package com.crystalpigeon.busnovisad.view.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.crystalpigeon.busnovisad.Const
import com.crystalpigeon.busnovisad.R
import com.crystalpigeon.busnovisad.view.fragment.UrbanSuburbanFragment

class UrbanSuburbanPagerAdapter(fm: FragmentManager, context: Context) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private var context: Context? = null

    init {
        this.context = context
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> UrbanSuburbanFragment.newInstance(Const.LANE_URBAN)
            1 -> UrbanSuburbanFragment.newInstance(Const.LANE_SUBURBAN)
            else -> UrbanSuburbanFragment.newInstance("")
        }
    }

    override fun getCount(): Int = 2

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> context?.getString(R.string.urban)
            1 -> context?.getString(R.string.suburban)
            else -> ""
        }
    }
}