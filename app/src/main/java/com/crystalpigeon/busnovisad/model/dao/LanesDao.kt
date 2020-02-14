package com.crystalpigeon.busnovisad.model.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.crystalpigeon.busnovisad.model.entity.Lane

@Dao
interface LanesDao {

    @Query("SELECT * FROM lane WHERE type LIKE :type")
    fun getLanes(type: String): LiveData<List<Lane>>

    @Insert(onConflict = REPLACE)
    suspend fun insert(lane: Lane)

    @Query("SELECT * FROM lane WHERE id not in (SELECT f.id FROM favoriteLane f)")
    suspend fun getNonFavorites(): List<Lane>

    @Query("DELETE FROM lane WHERE type = :type")
    suspend fun deleteForType(type: String)
}