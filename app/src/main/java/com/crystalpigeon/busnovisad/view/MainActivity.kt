package com.crystalpigeon.busnovisad.view

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.crystalpigeon.busnovisad.BusNsApp
import com.crystalpigeon.busnovisad.R
import com.crystalpigeon.busnovisad.viewmodel.LanesViewModel
import com.crystalpigeon.busnovisad.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: LanesViewModel

    @Inject
    lateinit var mainViewModel: MainViewModel

    init {
        BusNsApp.app.component.inject(this)
    }

    private val parentJob = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.IO + parentJob)

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tryFetch()
        mainViewModel.networkError.observe(this, Observer {
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.no_internet_connection))
                .setPositiveButton(getString(R.string.try_again)) { d, _ ->
                    tryFetch()
                    d.dismiss()
                }
                .setNegativeButton(getString(R.string.cancel)){d, _ -> d.dismiss() }
                .show()
        })
    }

    private fun tryFetch(){
        coroutineScope.launch(Dispatchers.IO) {
            mainViewModel.fetchAllSchedule()
        }
    }

    fun setActionBarTitle(id: Int) {
        pageTitle.text = getString(id)
    }

    fun getBackButton(): ImageView = back_button

    fun hideBackButton() { back_button.visibility = View.GONE }

    fun showBackButton() { back_button.visibility = View.VISIBLE }
}
