package com.crystalpigeon.busnovisad.di

import com.crystalpigeon.busnovisad.viewmodel.LanesViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
class ViewModelModule {

    @Provides
    fun providesLanesViewModel() : LanesViewModel = LanesViewModel()
}