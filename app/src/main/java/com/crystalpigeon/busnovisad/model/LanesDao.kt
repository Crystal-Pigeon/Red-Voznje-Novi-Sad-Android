package com.crystalpigeon.busnovisad.model

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.*
import androidx.room.Query

@Dao
interface LanesDao {

    @Query("SELECT * FROM lane WHERE type LIKE :type")
    fun getLanes(type: String): LiveData<List<Lane>>

    @Insert(onConflict = IGNORE)
    suspend fun insert(lane: Lane)
}