package com.crystalpigeon.busnovisad.model.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.crystalpigeon.busnovisad.BusNsApp
import com.crystalpigeon.busnovisad.model.BusDatabase
import com.crystalpigeon.busnovisad.model.Service
import com.crystalpigeon.busnovisad.model.dao.FavoriteLanesDao
import com.crystalpigeon.busnovisad.model.dao.SchedulesDao
import com.crystalpigeon.busnovisad.model.entity.Lane
import com.crystalpigeon.busnovisad.model.entity.Schedule
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ScheduleRepository {
    @Inject
    lateinit var api: Service
    @Inject
    lateinit var context: Context

    private val schedulesDao: SchedulesDao

    private val favoriteLanesDao: FavoriteLanesDao

    init {
        BusNsApp.app.component.inject(this)
        schedulesDao = BusDatabase.getDatabase(context).schedulesDao()
        favoriteLanesDao = BusDatabase.getDatabase(context).favLanesDao()
    }

    fun getScheduleFavorites(day: String): LiveData<List<Schedule>> {
        return Transformations.switchMap(favoriteLanesDao.getFavLanesIds()) {
            return@switchMap schedulesDao.getSchedulesByDayAndLanes(day, it)
        }
    }

    private suspend fun fetchScheduleForBus(id: String, type: String) {
        try {
            val schedules = api.getBusSchedule(id, type)
            schedules.forEach { s ->
                val schedule = Schedule(
                    s.id,
                    s.number,
                    s.name,
                    s.lane,
                    s.directionA,
                    s.directionB,
                    s.day,
                    s.schedule,
                    s.scheduleA,
                    s.scheduleB,
                    s.extras
                )

                schedulesDao.insert(schedule)
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun cacheSchedule(buses: List<Lane>) {
        buses.forEach { fetchScheduleForBus(it.id, it.type) }
    }
}