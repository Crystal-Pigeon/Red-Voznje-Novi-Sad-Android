package com.crystalpigeon.busnovisad.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.crystalpigeon.busnovisad.R
import kotlinx.android.synthetic.main.toolbar.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun setActionBarTitle(id: Int) {
        pageTitle.text = getString(id)
    }
}
