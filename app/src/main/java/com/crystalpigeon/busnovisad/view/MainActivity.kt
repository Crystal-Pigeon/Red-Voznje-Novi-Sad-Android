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
import com.google.android.material.snackbar.Snackbar
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
        setContentView(R.layout.activity_main)
        configureTheme()

        if (savedInstanceState == null) tryFetch()

        mainViewModel.importantError.observe(this, Observer { message ->
            message.getContentIfNotHandled()?.let {
                AlertDialog.Builder(this)
                    .setTitle(it)
                    .setPositiveButton(getString(R.string.try_again)) { d, _ ->
                        tryFetch()
                        d.dismiss()
                    }
                    .setOnDismissListener { tryFetch() }
                    .show()
            }
        })

        mainViewModel.nonImportantError.observe(this, Observer { message ->
            message.getContentIfNotHandled()?.let {
                val snackbar = Snackbar.make(
                    findViewById(android.R.id.content),
                    it,
                    Snackbar.LENGTH_LONG
                )

                snackbar.setAction(getString(R.string.try_again)) { tryFetch() }
                snackbar.show()
            }
        })

        mainViewModel.info.observe(this, Observer { message ->
            message.getContentIfNotHandled()?.let {
                Snackbar.make(
                    findViewById(android.R.id.content),
                    it,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        })

        setLanguage()
    }

    private fun configureTheme() {
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
    }

    private fun setLanguage() {
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
