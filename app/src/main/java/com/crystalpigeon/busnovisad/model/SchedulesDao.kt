package com.crystalpigeon.busnovisad.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SchedulesDao {

    @Query("SELECT * FROM schedule WHERE day LIKE :day")
    fun getSchedulesByDay(day: String): LiveData<List<Schedule>>

    @Query("SELECT * FROM schedule WHERE day LIKE :day and id in (:ids)")
    fun getSchedulesByDayAndLanes(day: String, ids: List<String>): LiveData<List<Schedule>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(schedule: Schedule)

    @Query("DELETE FROM schedule")
    fun deleteAllSchedules()
}