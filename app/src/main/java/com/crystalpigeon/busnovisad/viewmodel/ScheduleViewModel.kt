package com.crystalpigeon.busnovisad.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.crystalpigeon.busnovisad.BusNsApp
import com.crystalpigeon.busnovisad.model.entity.Schedule
import com.crystalpigeon.busnovisad.model.repository.ScheduleRepository
import javax.inject.Inject

class ScheduleViewModel : ViewModel() {
    @Inject
    lateinit var scheduleRepository: ScheduleRepository

    init {
        BusNsApp.app.component.inject(this)
    }

    private val day = MutableLiveData<String>()

    val schedule: LiveData<List<Schedule>> = Transformations.switchMap(day) { day ->
        scheduleRepository.getScheduleFavorites(day)
    }

    fun getFavorites(day: String) {
        this.day.value = day
    }
}