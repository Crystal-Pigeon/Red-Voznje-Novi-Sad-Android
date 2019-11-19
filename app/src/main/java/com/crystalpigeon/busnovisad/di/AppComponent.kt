package com.crystalpigeon.busnovisad.di

import com.crystalpigeon.busnovisad.model.SeasonRepository
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class, StorageModule::class])
interface AppComponent {
    fun inject(seasonRepository: SeasonRepository)
}