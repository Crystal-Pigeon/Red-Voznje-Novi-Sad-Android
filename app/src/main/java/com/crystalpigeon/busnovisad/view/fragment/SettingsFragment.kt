package com.crystalpigeon.busnovisad.view.fragment


import android.app.AlertDialog
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.crystalpigeon.busnovisad.BusNsApp
import com.crystalpigeon.busnovisad.Const
import com.crystalpigeon.busnovisad.R
import com.crystalpigeon.busnovisad.view.MainActivity
import kotlinx.android.synthetic.main.fragment_settings.*
import java.util.*
import javax.inject.Inject

class SettingsFragment : Fragment() {

    lateinit var navController: NavController

    @Inject
    lateinit var prefsEdit: SharedPreferences.Editor

    @Inject
    lateinit var sharedPrefs: SharedPreferences

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
            Navigation.findNavController(
                activity as MainActivity,
                R.id.nav_host_fragment
            )
        (activity as MainActivity).hideSortButton()
        (activity as MainActivity).showBackButton()
        (activity as MainActivity).getBackButton().setOnClickListener {
            navController.popBackStack()
        }
        (activity as MainActivity).getSettingsButton().visibility = View.GONE
        (activity as MainActivity).setActionBarTitle(R.string.settings_screen)

        llSupport.setOnClickListener {
            navController.navigate(R.id.action_settingsFragment_to_supportFragment)
        }

        tvLanguage.text =
            when (sharedPrefs.getString(Const.LANGUAGE, null) ?: Locale.getDefault().language) {
                "en" -> getString(R.string.english)
                "ser" -> getString(R.string.serbian)
                else -> Locale.getDefault().displayLanguage
            }

        tvTheme.text = when(sharedPrefs.getString(Const.THEME, null)) {
            "dark" ->  getString(R.string.dark)
            "light" -> getString(R.string.light)
            else -> getString(R.string.default_theme)
        }

        llLanguage.setOnClickListener { createAlertDialogForLanguage() }
        llTheme.setOnClickListener { createAlertDialogForTheme() }
    }

    private fun createAlertDialogForLanguage() {
        val values = arrayOf<CharSequence>(getString(R.string.english), getString(R.string.serbian))
        val builder = AlertDialog.Builder(activity)
        val savedLanguage: String? = sharedPrefs.getString(Const.LANGUAGE, null)
        val selectedLanguage = if (savedLanguage ?: Locale.getDefault().language == "en") 0 else 1

        builder.setTitle(R.string.choose_language)
        builder.setSingleChoiceItems(values, selectedLanguage) { dialog, item ->
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
        val values = arrayOf<CharSequence>(
            getString(R.string.light),
            getString(R.string.dark),
            getString(R.string.default_theme)
        )
        val builder = AlertDialog.Builder(activity)
        val selectedTheme = when {
            sharedPrefs.getString(Const.THEME, null) == "light" -> 0
            sharedPrefs.getString(Const.THEME, null) == "dark" -> 1
            else -> 2
        }

        builder.setTitle(R.string.choose_theme)
        builder.setSingleChoiceItems(values, selectedTheme) { dialog, item ->
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
