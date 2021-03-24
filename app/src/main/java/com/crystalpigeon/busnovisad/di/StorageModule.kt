package com.crystalpigeon.busnovisad.di

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import androidx.room.Room
import com.crystalpigeon.busnovisad.model.BusDatabase
import com.crystalpigeon.busnovisad.model.dao.FavoriteLanesDao
import com.crystalpigeon.busnovisad.model.dao.LanesDao
import com.crystalpigeon.busnovisad.model.dao.SchedulesDao
import com.crystalpigeon.busnovisad.model.repository.LanesRepository
import com.crystalpigeon.busnovisad.model.repository.ScheduleRepository
import com.crystalpigeon.busnovisad.model.repository.SeasonRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class StorageModule {
    @Singleton
    @Provides
    fun getSharedPrefs(context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }

    @Singleton
    @Provides
    fun getEditor(preferences: SharedPreferences): SharedPreferences.Editor {
        return preferences.edit()
    }

    @Singleton
    @Provides
    fun provideLanesRepository(): LanesRepository = LanesRepository()

    @Singleton
    @Provides
    fun provideScheduleRepository(): ScheduleRepository = ScheduleRepository()

    @Singleton
    @Provides
    fun provideSeasonRepository(): SeasonRepository = SeasonRepository()

    @Singleton
    @Provides
    fun provideRoomDatabase(context: Context): BusDatabase {
        return Room.databaseBuilder(
            context,
            BusDatabase::class.java,
            "bus_database"
        ).build()
    }

    @Singleton
    @Provides
    fun providesFavoriteLane(db: BusDatabase): FavoriteLanesDao {
        return db.favLanesDao()
    }

    @Singleton
    @Provides
    fun providesLanesDao(db: BusDatabase): LanesDao {
        return db.lanesDao()
    }

    @Singleton
    @Provides
    fun provideSchedulesDao(db: BusDatabase): SchedulesDao {
        return db.schedulesDao()
    }
}