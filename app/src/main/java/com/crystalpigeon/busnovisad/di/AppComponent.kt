package com.crystalpigeon.busnovisad.di

import com.crystalpigeon.busnovisad.model.repository.LanesRepository
import com.crystalpigeon.busnovisad.model.repository.ScheduleRepository
import com.crystalpigeon.busnovisad.model.repository.SeasonRepository
import com.crystalpigeon.busnovisad.view.MainActivity
import com.crystalpigeon.busnovisad.view.adapter.LaneAdapter
import com.crystalpigeon.busnovisad.view.fragment.MainFragment
import com.crystalpigeon.busnovisad.view.fragment.ScheduleFragment
import com.crystalpigeon.busnovisad.view.fragment.SettingsFragment
import com.crystalpigeon.busnovisad.view.fragment.UrbanSuburbanFragment
import com.crystalpigeon.busnovisad.viewmodel.LanesViewModel
import com.crystalpigeon.busnovisad.viewmodel.MainViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class, StorageModule::class, ViewModelModule::class])
interface AppComponent {
    fun inject(seasonRepository: SeasonRepository)
    fun inject(lanesRepository: LanesRepository)
    fun inject(mainActivity: MainActivity)
    fun inject(lanesViewModel: LanesViewModel)
    fun inject(urbanSuburbanFragment: UrbanSuburbanFragment)
    fun inject(repository: ScheduleRepository)
    fun inject(mainViewModel: MainViewModel)
    fun inject(scheduleFragment: ScheduleFragment)
    fun inject(laneAdapter: LaneAdapter)
    fun inject(mainFragment: MainFragment)
    fun inject(settingsFragment: SettingsFragment)
}