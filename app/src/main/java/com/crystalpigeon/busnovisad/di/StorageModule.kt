package com.crystalpigeon.busnovisad.di

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
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
    fun getEditor(preferences: SharedPreferences) : SharedPreferences.Editor {
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
}