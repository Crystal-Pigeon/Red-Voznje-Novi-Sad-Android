package com.crystalpigeon.busnovisad.view.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.crystalpigeon.busnovisad.R
import com.crystalpigeon.busnovisad.view.MainActivity
import kotlinx.android.synthetic.main.fragment_support.*

class SupportFragment : Fragment() {

    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_support, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController =
            Navigation.findNavController(activity as MainActivity,
                R.id.nav_host_fragment
            )
        (activity as MainActivity).setActionBarTitle(R.string.support)
        (activity as MainActivity).showBackButton()
        (activity as MainActivity).getBackButton().setOnClickListener {
            navController.popBackStack()
        }

        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
            ivEmail.setImageResource(R.drawable.email)
    }
}
