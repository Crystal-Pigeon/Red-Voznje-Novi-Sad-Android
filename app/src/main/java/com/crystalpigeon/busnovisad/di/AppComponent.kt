package com.crystalpigeon.busnovisad.di

import com.crystalpigeon.busnovisad.model.repository.LanesRepository
import com.crystalpigeon.busnovisad.model.repository.ScheduleRepository
import com.crystalpigeon.busnovisad.model.repository.SeasonRepository
import com.crystalpigeon.busnovisad.view.MainActivity
import com.crystalpigeon.busnovisad.view.fragment.SettingsFragment
import com.crystalpigeon.busnovisad.viewmodel.LanesViewModel
import com.crystalpigeon.busnovisad.viewmodel.MainViewModel
import com.crystalpigeon.busnovisad.viewmodel.ScheduleViewModel
import com.crystalpigeon.busnovisad.viewmodel.SortViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class, StorageModule::class])
interface AppComponent {
    fun inject(seasonRepository: SeasonRepository)
    fun inject(lanesRepository: LanesRepository)
    fun inject(mainActivity: MainActivity)
    fun inject(lanesViewModel: LanesViewModel)
    fun inject(repository: ScheduleRepository)
    fun inject(mainViewModel: MainViewModel)
    fun inject(settingsFragment: SettingsFragment)
    fun inject(sortViewModel: SortViewModel)
    fun inject(scheduleViewModel: ScheduleViewModel)
}