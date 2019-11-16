package com.crystalpigeon.busnovisad.model.Room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Season::class], exportSchema = false, version = 1)
abstract class SeasonDatabase: RoomDatabase(){

    abstract fun seasonDao(): SeasonDao
}