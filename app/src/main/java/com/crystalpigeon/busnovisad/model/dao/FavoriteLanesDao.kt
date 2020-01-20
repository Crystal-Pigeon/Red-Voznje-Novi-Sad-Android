package com.crystalpigeon.busnovisad.model.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.crystalpigeon.busnovisad.model.entity.FavoriteLane
import com.crystalpigeon.busnovisad.model.entity.Lane
import com.crystalpigeon.busnovisad.model.entity.Schedule

@Dao
interface FavoriteLanesDao {

    @Query("SELECT * FROM favoriteLane WHERE id = :id")
    suspend fun getFavLane(id: String): List<FavoriteLane>

    @Query("SELECT l.* FROM favoriteLane f inner join lane l on f.id = l.id ORDER BY sort ASC")
    suspend fun getFavLanes(): List<Lane>

    @Query("DELETE FROM favoriteLane WHERE id = :id")
    suspend fun deleteFavLane(id: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavLane(favLane: FavoriteLane)

    @Query("SELECT MAX(sort) + 1 FROM favoriteLane")
    suspend fun getBiggestOrder(): Int?

    @Query("UPDATE favoriteLane SET sort = :sort WHERE id = :id")
    fun updateOrder(id: String, sort: Int)

    @Query("SELECT s.* FROM favoriteLane f INNER JOIN schedule s ON f.id = s.id WHERE s.day = :day ORDER BY sort ASC")
    fun getFavoritesByDay(day: String): LiveData<List<Schedule>>
}