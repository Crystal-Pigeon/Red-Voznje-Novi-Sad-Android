package com.crystalpigeon.busnovisad.viewmodel

import com.crystalpigeon.busnovisad.BusNsApp
import com.crystalpigeon.busnovisad.model.repository.LanesRepository
import javax.inject.Inject

class LanesViewModel {

    @Inject
    lateinit var lanesRepository: LanesRepository

    init {
        BusNsApp.app.component.inject(this)
    }

    fun getLanes(type: String) = lanesRepository.getLanes(type)
}