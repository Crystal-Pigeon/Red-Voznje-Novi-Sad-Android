package com.crystalpigeon.busnovisad.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.crystalpigeon.busnovisad.R
import com.crystalpigeon.busnovisad.view.MainActivity
import com.crystalpigeon.busnovisad.view.adapter.PagerAdapter
import com.crystalpigeon.busnovisad.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.toolbar.*

class MainFragment : Fragment() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).setActionBarTitle(R.string.app_name)
        val adapter = PagerAdapter(childFragmentManager, context!!)
        viewpager.adapter = adapter
        viewpager.offscreenPageLimit = 2
        tablayout.setupWithViewPager(viewpager)
        val position = viewModel.getTabPositionByDate()
        viewpager.currentItem = position
        fabAddLines.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_addLinesFragment)
        }

        (activity as MainActivity).sort_favorites.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_sortFavoritesFragment)
        }
    }


    override fun onResume() {
        super.onResume()
        (activity as MainActivity).showSortButton()
    }
}
