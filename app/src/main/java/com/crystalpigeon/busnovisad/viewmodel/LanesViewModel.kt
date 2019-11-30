package com.crystalpigeon.busnovisad.viewmodel

import com.crystalpigeon.busnovisad.BusNsApp
import com.crystalpigeon.busnovisad.Const.LANE_SUBURBAN
import com.crystalpigeon.busnovisad.Const.LANE_URBAN
import com.crystalpigeon.busnovisad.model.repository.LanesRepository
import javax.inject.Inject

class LanesViewModel {

    @Inject
    lateinit var lanesRepository: LanesRepository

    init {
        BusNsApp.app.component.inject(this)
    }

    fun fetchAllLanes(){
        lanesRepository.getLanes(LANE_URBAN)
        lanesRepository.getLanes(LANE_SUBURBAN)
    }

    fun getLanes(type: String) = lanesRepository.getLanes(type)
}