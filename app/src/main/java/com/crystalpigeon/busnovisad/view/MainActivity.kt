package com.crystalpigeon.busnovisad.view

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import com.crystalpigeon.busnovisad.BusNsApp
import com.crystalpigeon.busnovisad.Const
import com.crystalpigeon.busnovisad.R
import com.crystalpigeon.busnovisad.viewmodel.LanesViewModel
import com.crystalpigeon.busnovisad.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: LanesViewModel

    @Inject
    lateinit var mainViewModel: MainViewModel

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    @Inject
    lateinit var prefsEditor: SharedPreferences.Editor

    init {
        BusNsApp.app.component.inject(this)
    }

    private val parentJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + parentJob)

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        val theme = sharedPreferences.getString(Const.THEME, null)
        if (theme == null) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            prefsEditor.putString(Const.THEME, "default")
        } else {
            when (theme) {
                "light" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                "dark" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                "default" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            }
        }
        setContentView(R.layout.activity_main)
        tryFetch()
        mainViewModel.networkError.observe(this, Observer {
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.no_internet_connection))
                .setPositiveButton(getString(R.string.try_again)) { d, _ ->
                    tryFetch()
                    d.dismiss()
                }
                .setNegativeButton(getString(R.string.cancel)) { d, _ -> d.dismiss() }
                .show()
        })

        val res = this.resources
        val dm = res.displayMetrics
        val conf = res.configuration
        val lang = sharedPreferences.getString(Const.LANGUAGE, null)
        if (lang == null) {
            conf.setLocale(Locale("en"))
            prefsEditor.putString(Const.LANGUAGE, "en")
        } else conf.setLocale(Locale(lang))

        res.updateConfiguration(conf, dm)
    }

    private fun tryFetch() {
        coroutineScope.launch(Dispatchers.IO) {
            mainViewModel.fetchAllSchedule()
        }
    }

    fun setActionBarTitle(id: Int) {
        pageTitle.text = getString(id)
    }

    fun getSettingsButton(): ImageView = settings

    fun getBackButton(): ImageView = back_button

    fun hideBackButton() {
        back_button.visibility = View.GONE
    }

    fun showBackButton() {
        back_button.visibility = View.VISIBLE
    }

    fun hideSortButton() {
        sort_favorites.visibility = View.GONE
    }

    fun showSortButton() {
        sort_favorites.visibility = View.VISIBLE
    }
}
