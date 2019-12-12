package com.crystalpigeon.busnovisad.di

import com.crystalpigeon.busnovisad.viewmodel.LanesViewModel
import com.crystalpigeon.busnovisad.viewmodel.MainViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ViewModelModule {

    @Provides
    fun providesLanesViewModel() : LanesViewModel = LanesViewModel()

    @Singleton
    @Provides
    fun providesMainViewModel() : MainViewModel = MainViewModel()
}