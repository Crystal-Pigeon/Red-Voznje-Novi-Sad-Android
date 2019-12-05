package com.crystalpigeon.busnovisad.di

import com.crystalpigeon.busnovisad.viewmodel.LanesViewModel
import com.crystalpigeon.busnovisad.viewmodel.MainViewModel
import dagger.Module
import dagger.Provides

@Module
class ViewModelModule {

    @Provides
    fun providesLanesViewModel() : LanesViewModel = LanesViewModel()

    @Provides
    fun providesMainViewModel() : MainViewModel = MainViewModel()
}