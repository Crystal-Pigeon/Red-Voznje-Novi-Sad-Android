package com.crystalpigeon.busnovisad.model.repository

import android.content.SharedPreferences
import com.crystalpigeon.busnovisad.BusNsApp
import com.crystalpigeon.busnovisad.Const
import com.crystalpigeon.busnovisad.model.Service
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SeasonRepository {
    @Inject
    lateinit var api: Service
    @Inject
    lateinit var sharedPrefs: SharedPreferences
    @Inject
    lateinit var prefsEdit: SharedPreferences.Editor

    init {
        BusNsApp.app.component.inject(this)
    }

    suspend fun shouldUpdate(): Boolean {
        val oldValue = sharedPrefs.getString(Const.DATE, null)
        val season = api.getSeason()

        if (oldValue == null || oldValue != season[0].date) {
            prefsEdit.putString(Const.DATE, season[0].date).apply()
            return true
        } else {
            return false
        }
    }
}