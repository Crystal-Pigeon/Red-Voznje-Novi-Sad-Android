package com.crystalpigeon.busnovisad.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.crystalpigeon.busnovisad.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
