package com.crystalpigeon.busnovisad.model.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.crystalpigeon.busnovisad.BusNsApp
import com.crystalpigeon.busnovisad.model.Result
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

    private suspend fun refreshScheduleForBus(id: String, type: String) {
        val schedulesResponse = api.getBusSchedule(id, type)
        if (schedulesResponse.isSuccessful) {
            schedulesResponse.body()?.forEach { s ->
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
        }else{
            throw java.lang.Exception("${schedulesResponse.code()}")
        }

    }

    suspend fun cacheSchedule(buses: List<Lane>): Result<Boolean> {
        val deferredList = arrayListOf<Deferred<Unit>>()
        buses.forEach {
            deferredList.add(GlobalScope.async { refreshScheduleForBus(it.id, it.type) })
        }
        try {
            deferredList.forEach { it.await() }
        } catch (e: Exception) {
            return Result.Error(e)
        }
        return Result.Success(true)
    }


}