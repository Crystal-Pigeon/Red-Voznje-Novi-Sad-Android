package com.crystalpigeon.busnovisad.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SchedulesDao {

    @Query("SELECT * FROM schedule WHERE day LIKE :day")
    fun getSchedules(day: String): LiveData<List<Schedule>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(schedule: Schedule)
}