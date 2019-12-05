package com.crystalpigeon.busnovisad.model.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.crystalpigeon.busnovisad.model.entity.FavoriteLane

@Dao
interface FavoriteLanesDao {

    @Query("SELECT * FROM favoriteLane WHERE id = :id")
    suspend fun getFavLane(id: String): List<FavoriteLane>

    @Query("SELECT * FROM favoriteLane")
    suspend fun getFavLanes(): List<FavoriteLane>

    @Query("SELECT id FROM favoriteLane")
    fun getFavLanesIds(): LiveData<List<String>>

    @Query("DELETE FROM favoriteLane WHERE id = :id")
    suspend fun deleteFavLane(id: String)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavLane(favLane: FavoriteLane)
}