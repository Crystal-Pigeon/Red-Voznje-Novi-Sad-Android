package com.crystalpigeon.busnovisad.model.repository

import android.content.SharedPreferences
import com.crystalpigeon.busnovisad.BusNsApp
import com.crystalpigeon.busnovisad.Const
import com.crystalpigeon.busnovisad.model.SeasonResponse
import com.crystalpigeon.busnovisad.model.Service
import com.crystalpigeon.busnovisad.model.dao.SchedulesDao
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
    @Inject
    lateinit var schedulesDao: SchedulesDao
    private var season: List<SeasonResponse>? = null

    init {
        BusNsApp.app.component.inject(this)
    }

    suspend fun shouldUpdate(): Boolean {
        val oldValue = sharedPrefs.getString(Const.DATE, null)
        try{
            season = api.getSeason()
        }
        catch(e:Exception){
            e.printStackTrace()
            return true
        }
        return oldValue == null ||
                oldValue != season?.get(0)?.date ||
                schedulesDao.getNumberOfRows() == 0
    }

    fun seasonUpdated() {
        season?.let {
            prefsEdit.putString(Const.DATE, it[0].date).apply()
        }
    }
}