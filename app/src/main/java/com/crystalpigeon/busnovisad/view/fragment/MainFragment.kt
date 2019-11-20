package com.crystalpigeon.busnovisad.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.crystalpigeon.busnovisad.BusNsApp
import com.crystalpigeon.busnovisad.Const

import com.crystalpigeon.busnovisad.R
import com.crystalpigeon.busnovisad.view.MainActivity
import com.crystalpigeon.busnovisad.view.adapter.PagerAdapter
import com.crystalpigeon.busnovisad.viewmodel.LanesViewModel
import kotlinx.android.synthetic.main.fragment_main.*
import javax.inject.Inject

class MainFragment : Fragment() {
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController =
            Navigation.findNavController(activity as MainActivity, R.id.nav_host_fragment)
        viewpager.adapter = PagerAdapter(childFragmentManager, context!!)
        tablayout.setupWithViewPager(viewpager)

        fabAddLines.setOnClickListener {
            navController.navigate(R.id.action_mainFragment_to_addLinesFragment)
        }
    }
}
