package com.crystalpigeon.busnovisad.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.crystalpigeon.busnovisad.BusNsApp

import com.crystalpigeon.busnovisad.R
import com.crystalpigeon.busnovisad.view.MainActivity
import com.crystalpigeon.busnovisad.view.adapter.PagerAdapter
import com.crystalpigeon.busnovisad.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_main.tablayout
import javax.inject.Inject

class MainFragment : Fragment() {
    @Inject
    lateinit var viewModel: MainViewModel

    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        BusNsApp.app.component.inject(this)
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController =
            Navigation.findNavController(activity as MainActivity, R.id.nav_host_fragment)
        (activity as MainActivity).setActionBarTitle(R.string.app_name)
        val adapter = PagerAdapter(childFragmentManager, context!!)
        viewpager.adapter = adapter
        tablayout.setupWithViewPager(viewpager)
        val position = viewModel.getTabPositionByDate()
        viewpager.currentItem = position
        fabAddLines.setOnClickListener {
            navController.navigate(R.id.action_mainFragment_to_addLinesFragment)
        }
    }
}
