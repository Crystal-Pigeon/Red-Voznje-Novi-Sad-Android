package com.crystalpigeon.busnovisad.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.crystalpigeon.busnovisad.R
import com.crystalpigeon.busnovisad.view.MainActivity

class ScheduleFragment : Fragment() {

    companion object {
        fun newInstance(): ScheduleFragment {
            return ScheduleFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as MainActivity).hideBackButton()
        return inflater.inflate(R.layout.fragment_schedule, container, false)
    }
}
