package com.crystalpigeon.busnovisad.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavouriteLanesDao {

    @Query("SELECT * FROM favouritelane WHERE id = :id")
    suspend fun getFavLane(id: String): List<FavouriteLane>

    @Query("DELETE FROM favouritelane WHERE id = :id")
    suspend fun deleteFavLane(id: String)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFavLane(favLane: FavouriteLane)
}