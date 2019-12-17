package com.crystalpigeon.busnovisad.view.fragment


import android.app.AlertDialog
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
import kotlinx.android.synthetic.main.fragment_settings.*
import android.content.SharedPreferences
import com.crystalpigeon.busnovisad.BusNsApp
import com.crystalpigeon.busnovisad.Const
import javax.inject.Inject

class SettingsFragment : Fragment() {

    lateinit var navController: NavController

    @Inject
    lateinit var prefsEdit: SharedPreferences.Editor

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    init {
        BusNsApp.app.component.inject(this)
    }

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

        llSupport.setOnClickListener {
            navController.navigate(R.id.action_settingsFragment_to_supportFragment)
        }
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)
            ivSupport.setImageResource(R.drawable.forward_white)

        if (sharedPreferences.getString(Const.LANGUAGE, null) == "en")
            tvLanguage.text = getString(R.string.english)
        else if (sharedPreferences.getString(Const.LANGUAGE, null) == "ser")
            tvLanguage.text = getString(R.string.serbian)

        when {
            sharedPreferences.getString(Const.THEME, null)=="dark" -> tvTheme.text = getString(R.string.dark)
            sharedPreferences.getString(Const.THEME, null)=="light" -> tvTheme.text = getString(R.string.light)
            sharedPreferences.getString(Const.THEME, null)=="default" -> tvTheme.text = getString(R.string.default_theme)
        }
        llLanguage.setOnClickListener{ createAlertDialogForLanguage() }
        llTheme.setOnClickListener{ createAlertDialogForTheme() }
    }

    private fun createAlertDialogForLanguage() {
        val values = arrayOf<CharSequence>(getString(R.string.english), getString(R.string.serbian))
        val builder = AlertDialog.Builder(activity)

        builder.setTitle(R.string.choose_theme)
        builder.setSingleChoiceItems(values, -1) { dialog, item ->
            when (item) {
                0 -> {
                    prefsEdit.putString(Const.LANGUAGE, "en").apply()
                    activity?.recreate()
                }
                1 -> {
                    prefsEdit.putString(Const.LANGUAGE, "ser").apply()
                    activity?.recreate()
                }
            }
            dialog.dismiss()
        }
        val alertDialog = builder.create()
        alertDialog.show()
    }

    private fun createAlertDialogForTheme() {
        val values = arrayOf<CharSequence>(getString(R.string.light), getString(R.string.dark), getString(R.string.default_theme))

        val builder = AlertDialog.Builder(activity)

        builder.setTitle(R.string.choose_theme)
        builder.setSingleChoiceItems(values, -1) { dialog, item ->
            when (item) {
                0 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    activity?.recreate()
                    prefsEdit.putString(Const.THEME, "light").apply()
                }
                1 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    activity?.recreate()
                    prefsEdit.putString(Const.THEME, "dark").apply()
                }
                2 -> {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                    activity?.recreate()
                    prefsEdit.putString(Const.THEME, "default").apply()
                }
            }
            dialog.dismiss()
        }
        val alertDialog = builder.create()
        alertDialog.show()
    }
}
