package com.crystalpigeon.busnovisad

import android.app.Application
import com.crystalpigeon.busnovisad.di.AppComponent
import com.crystalpigeon.busnovisad.di.AppModule
import com.crystalpigeon.busnovisad.di.DaggerAppComponent

class BusNsApp: Application() {

    val component: AppComponent by lazy {
        DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }

    companion object {
        @JvmStatic
        lateinit var app: BusNsApp
    }

    override fun onCreate() {
        super.onCreate()
        app = this
    }
}