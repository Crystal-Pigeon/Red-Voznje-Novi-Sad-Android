package com.crystalpigeon.busnovisad.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.crystalpigeon.busnovisad.BusNsApp
import com.crystalpigeon.busnovisad.model.repository.LanesRepository
import com.crystalpigeon.busnovisad.model.repository.ScheduleRepository
import com.crystalpigeon.busnovisad.model.repository.SeasonRepository
import javax.inject.Inject

class MainViewModel {
    @Inject
    lateinit var scheduleRepository: ScheduleRepository

    @Inject
    lateinit var seasonRepository: SeasonRepository

    @Inject
    lateinit var lanesRepository: LanesRepository

    private val day = MutableLiveData<String>()

    init {
        BusNsApp.app.component.inject(this)
    }

    fun getFavorites(day: String) {
        this.day.value = day
    }

    val favorites = Transformations.switchMap(day) {
        scheduleRepository.getScheduleFavorites(it)
    }

    suspend fun fetchAllSchedule() {
        if (!seasonRepository.shouldUpdate()) return

        lanesRepository.cacheAllLanes()

        val favorites = lanesRepository.getFavorites()
        scheduleRepository.cacheSchedule(favorites)

        val nonFavorites = lanesRepository.getNonFavorites()
        scheduleRepository.cacheSchedule(nonFavorites)
    }
}