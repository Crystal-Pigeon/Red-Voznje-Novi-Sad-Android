package com.crystalpigeon.busnovisad.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.crystalpigeon.busnovisad.R
import com.crystalpigeon.busnovisad.view.MainActivity
import com.crystalpigeon.busnovisad.view.adapter.UrbanSuburbanPagerAdapter
import kotlinx.android.synthetic.main.fragment_add_lines.*

class AddLinesFragment : Fragment() {

    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_lines, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).setActionBarTitle(R.string.add)
        (activity as MainActivity).showBackButton()
        navController =
            Navigation.findNavController(activity as MainActivity, R.id.nav_host_fragment)
        viewpager_add_lines.adapter = UrbanSuburbanPagerAdapter(childFragmentManager,context!!)
        tablayout.setupWithViewPager(viewpager_add_lines)

        (activity as MainActivity).getBackButton().setOnClickListener {
            navController.popBackStack()
        }
    }
}