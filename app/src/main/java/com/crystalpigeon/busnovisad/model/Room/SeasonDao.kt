package com.crystalpigeon.busnovisad.model.Room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface SeasonDao {

    @get:Query("Select * from season")
    val getSeason: Season

    @Insert
    fun saveSeason(season: Season)
}