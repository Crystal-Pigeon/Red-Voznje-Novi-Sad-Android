package com.crystalpigeon.busnovisad.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.crystalpigeon.busnovisad.R
import com.crystalpigeon.busnovisad.view.MainActivity
import com.crystalpigeon.busnovisad.view.adapter.UrbanSuburbanPagerAdapter
import kotlinx.android.synthetic.main.fragment_add_lines.*

class AddLinesFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_lines, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).setActionBarTitle(R.string.add_lines)
        (activity as MainActivity).showBackButton()
        viewpager_add_lines.adapter = UrbanSuburbanPagerAdapter(childFragmentManager,context!!)
        tablayout.setupWithViewPager(viewpager_add_lines)
        (activity as MainActivity).hideSortButton()
        (activity as MainActivity).getBackButton().setOnClickListener {
            findNavController().navigateUp()
        }

        (activity as MainActivity).getSettingsButton().visibility = View.VISIBLE
        (activity as MainActivity).getSettingsButton().setOnClickListener {
            findNavController().navigate(R.id.action_addLinesFragment_to_settingsFragment)
        }
    }
}