package com.crystalpigeon.busnovisad.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.crystalpigeon.busnovisad.BusNsApp
import com.crystalpigeon.busnovisad.R
import com.crystalpigeon.busnovisad.viewmodel.LanesViewModel
import javax.inject.Inject
import kotlinx.android.synthetic.main.toolbar.*

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: LanesViewModel

    init {
        BusNsApp.app.component.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.fetchAllLanes()
    }

    fun setActionBarTitle(id: Int) {
        pageTitle.text = getString(id)
    }
}
