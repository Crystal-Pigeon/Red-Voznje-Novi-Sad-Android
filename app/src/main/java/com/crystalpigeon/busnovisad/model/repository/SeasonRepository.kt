package com.crystalpigeon.busnovisad.model.repository

import android.content.SharedPreferences
import com.crystalpigeon.busnovisad.BusNsApp
import com.crystalpigeon.busnovisad.Const
import com.crystalpigeon.busnovisad.model.Result
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
    var date: String? = null
    init {
        BusNsApp.app.component.inject(this)
    }

    suspend fun shouldUpdate(): Result<Boolean> {
        val oldValue = sharedPrefs.getString(Const.DATE, null)
        try {
            val seasonResponse = api.getSeason()

            return when (seasonResponse.isSuccessful && seasonResponse.code() in 200..300) {
                true -> {
                    date = seasonResponse.body()?.get(0)?.date
                    val shouldUpdate = oldValue == null ||
                            oldValue != date ||
                            schedulesDao.getNumberOfRows() == 0
                    Result.Success(shouldUpdate)
                }
                else -> Result.Error(java.lang.Exception("Error code ${seasonResponse.code()}"))
            }
        } catch (e: Exception) {
            return Result.Error(e)
        }

    }

    fun seasonUpdated() {
        date?.let {
            prefsEdit.putString(Const.DATE, date).apply()
        }
    }

    fun isEverUpdated(): Boolean {
        return sharedPrefs.getString(Const.DATE, null) != null
    }
}