package com.crystalpigeon.busnovisad.viewmodel

import com.crystalpigeon.busnovisad.BusNsApp
import com.crystalpigeon.busnovisad.model.dao.FavoriteLanesDao
import com.crystalpigeon.busnovisad.model.entity.FavoriteLane
import com.crystalpigeon.busnovisad.model.entity.Lane
import com.crystalpigeon.busnovisad.model.repository.LanesRepository
import javax.inject.Inject

class LanesViewModel {

    @Inject
    lateinit var lanesRepository: LanesRepository
    @Inject
    lateinit var favLanesDao: FavoriteLanesDao

    init {
        BusNsApp.app.component.inject(this)
    }

    fun getLanes(type: String) = lanesRepository.getLanes(type)

    suspend fun isFavorite(lane: Lane): Boolean {
        return favLanesDao.getFavLane(lane.id).isNotEmpty()
    }

    suspend fun onLaneClicked(lane: Lane) {
        if (favLanesDao.getFavLane(lane.id).isEmpty()) {
            val favLane = FavoriteLane(
                lane.id,
                lane.type,
                favLanesDao.getBiggestOrder() ?: 1
            )

            favLanesDao.insertFavLane(favLane)
        } else {
            favLanesDao.deleteFavLane(lane.id)
        }
    }
}