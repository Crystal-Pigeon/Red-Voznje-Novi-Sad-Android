package com.crystalpigeon.busnovisad.view

import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import com.crystalpigeon.busnovisad.BusNsApp
import com.crystalpigeon.busnovisad.Const
import com.crystalpigeon.busnovisad.R
import com.crystalpigeon.busnovisad.viewmodel.MainViewModel
import com.crystalpigeon.busnovisad.viewmodel.MainViewModel.Message
import com.crystalpigeon.busnovisad.viewmodel.MainViewModel.Message.*
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var sharedPreferences: SharedPreferences

    @Inject
    lateinit var prefsEditor: SharedPreferences.Editor
    private val mainViewModel: MainViewModel by viewModels()
    private val parentJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + parentJob)
    private fun toLocalMessage(message: Message): String{
        return when(message){
            ERROR_CHECKING_FOR_UPDATE -> getString(R.string.error_checking_for_update)
            ERROR_FETCHING_DATA -> getString(R.string.error_fetching_scedule)
            NO_INTERNET -> getString(R.string.no_internet_connection)
            UP_TO_DATE -> getString(R.string.everything_is_up_to_date)
        }
    }

    init {
        BusNsApp.app.component.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        configureTheme()
        if (savedInstanceState == null) tryFetch()

        mainViewModel.importantError.observe(this, Observer { message ->
            message.getContentIfNotHandled()?.let {
                AlertDialog.Builder(this)
                    .setTitle(toLocalMessage(message = it))
                    .setPositiveButton(getString(R.string.try_again)) { d, _ ->
                        tryFetch()
                        d.dismiss()
                    }
                    .setOnDismissListener { tryFetch() }
                    .show()
            }
        })

        mainViewModel.nonImportantError.observe(this, Observer { message ->
            val cLayout:View? = findViewById(R.id.clayout)
            message.getContentIfNotHandled()?.let {
                val snackbar = Snackbar.make(
                    cLayout?:findViewById(R.id.root),
                    toLocalMessage(it),
                    Snackbar.LENGTH_LONG
                )

                snackbar.setAction(getString(R.string.try_again)) { tryFetch() }
                snackbar.show()
            }
        })

        mainViewModel.info.observe(this, Observer { message ->
            val cLayout:View? = findViewById(R.id.clayout)
            message.getContentIfNotHandled()?.let {
                Snackbar.make(
                    cLayout?:findViewById(R.id.root),
                    toLocalMessage(it),
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

    @Suppress("DEPRECATION")
    private fun setLanguage() {
        val res = this.resources
        val dm = res.displayMetrics
        val conf = res.configuration
        val lang = sharedPreferences.getString(Const.LANGUAGE, null)
        if (lang == null) {
            conf.setLocale(Locale(Locale.getDefault().language))
            prefsEditor.putString(Const.LANGUAGE, Locale.getDefault().language)
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
