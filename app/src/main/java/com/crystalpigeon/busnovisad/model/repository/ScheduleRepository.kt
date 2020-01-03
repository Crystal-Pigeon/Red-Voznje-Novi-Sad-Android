package com.crystalpigeon.busnovisad.model.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.crystalpigeon.busnovisad.BusNsApp
import com.crystalpigeon.busnovisad.model.Service
import com.crystalpigeon.busnovisad.model.dao.FavoriteLanesDao
import com.crystalpigeon.busnovisad.model.dao.SchedulesDao
import com.crystalpigeon.busnovisad.model.entity.Lane
import com.crystalpigeon.busnovisad.model.entity.Schedule
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ScheduleRepository {
    @Inject
    lateinit var api: Service
    @Inject
    lateinit var context: Context

    @Inject
    lateinit var schedulesDao: SchedulesDao

    @Inject
    lateinit var favoriteLanesDao: FavoriteLanesDao

    init {
        BusNsApp.app.component.inject(this)
    }

    fun getScheduleFavorites(day: String): LiveData<List<Schedule>> {
        return favoriteLanesDao.getFavoritesByDay(day)
    }

    suspend fun deleteSchedule(id: String) {
        return favoriteLanesDao.deleteFavLane(id)
    }

    private suspend fun refreshScheduleForBus(id: String, type: String): Boolean {
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
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }

    fun cacheSchedule(buses: List<Lane>): ArrayList<Deferred<Boolean>> {
        val deferredList = arrayListOf<Deferred<Boolean>>()
        buses.forEach {
            deferredList.add(GlobalScope.async { refreshScheduleForBus(it.id, it.type) })
        }
        return deferredList
    }
}