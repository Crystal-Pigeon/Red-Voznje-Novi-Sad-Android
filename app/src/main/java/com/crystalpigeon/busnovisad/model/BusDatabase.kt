package com.crystalpigeon.busnovisad.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.crystalpigeon.busnovisad.model.dao.FavoriteLanesDao
import com.crystalpigeon.busnovisad.model.dao.LanesDao
import com.crystalpigeon.busnovisad.model.dao.SchedulesDao
import com.crystalpigeon.busnovisad.model.entity.FavoriteLane
import com.crystalpigeon.busnovisad.model.entity.Lane
import com.crystalpigeon.busnovisad.model.entity.Schedule

@Database(entities = [Lane::class, Schedule::class, FavoriteLane::class], version = 13, exportSchema = false)
@TypeConverters(Converters::class)
abstract class BusDatabase : RoomDatabase() {

    abstract fun lanesDao(): LanesDao
    abstract fun favLanesDao(): FavoriteLanesDao
    abstract fun schedulesDao(): SchedulesDao
}