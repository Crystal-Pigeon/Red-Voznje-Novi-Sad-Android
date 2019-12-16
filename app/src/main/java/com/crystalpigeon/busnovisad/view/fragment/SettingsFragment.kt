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
import kotlinx.android.synthetic.main.fragment_settings.*

class SettingsFragment : Fragment() {

    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController =
            Navigation.findNavController(activity as MainActivity,
                R.id.nav_host_fragment
            )
        (activity as MainActivity).showBackButton()
        (activity as MainActivity).getBackButton().setOnClickListener {
            navController.popBackStack()
        }
        (activity as MainActivity).getSettingsButton().visibility = View.GONE
        (activity as MainActivity).setActionBarTitle(R.string.settings_screen)

        ivSupport.setOnClickListener {
            navController.navigate(R.id.action_settingsFragment_to_supportFragment)
        }
    }

}
