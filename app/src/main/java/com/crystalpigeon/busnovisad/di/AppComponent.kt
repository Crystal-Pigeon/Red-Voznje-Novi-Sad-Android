package com.crystalpigeon.busnovisad.di

import com.crystalpigeon.busnovisad.model.repository.LanesRepository
import com.crystalpigeon.busnovisad.model.repository.SeasonRepository
import com.crystalpigeon.busnovisad.view.MainActivity
import com.crystalpigeon.busnovisad.viewmodel.LanesViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class, StorageModule::class, ViewModelModule::class])
interface AppComponent {
    fun inject(seasonRepository: SeasonRepository)
    fun inject(lanesRepository: LanesRepository)
    fun inject(mainActivity: MainActivity)
    fun inject(lanesViewModel: LanesViewModel)
}