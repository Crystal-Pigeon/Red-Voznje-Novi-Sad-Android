package com.crystalpigeon.busnovisad.model.Room

import android.content.Context
import androidx.room.Room

class DatabaseClient(mContext: Context) {
    val database: SeasonDatabase

    init {
        database = Room.databaseBuilder(mContext, SeasonDatabase::class.java, "season_database")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries().build()
    }

    companion object {

        private var mInstance: DatabaseClient? = null

        @Synchronized
        fun getInstance(mContext: Context): DatabaseClient {
            if (mInstance == null) {
                mInstance = DatabaseClient(mContext)
            }
            return mInstance as DatabaseClient
        }
    }
}