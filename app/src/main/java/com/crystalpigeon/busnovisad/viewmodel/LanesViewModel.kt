package com.crystalpigeon.busnovisad.viewmodel

import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.crystalpigeon.busnovisad.BusNsApp
import com.crystalpigeon.busnovisad.model.dao.FavoriteLanesDao
import com.crystalpigeon.busnovisad.model.entity.FavoriteLane
import com.crystalpigeon.busnovisad.model.entity.Lane
import com.crystalpigeon.busnovisad.model.repository.LanesRepository
import com.google.firebase.analytics.FirebaseAnalytics
import javax.inject.Inject

class LanesViewModel : ViewModel(){

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

    suspend fun onLaneClicked(lane: Lane, firebaseAnalytics: FirebaseAnalytics) {
        if (favLanesDao.getFavLane(lane.id).isEmpty()) {
            val favLane = FavoriteLane(
                lane.id,
                lane.type,
                favLanesDao.getBiggestOrder() ?: 1
            )

            favLanesDao.insertFavLane(favLane)
            val params = Bundle()
            params.putString("line", lane.number)
            firebaseAnalytics.logEvent("bus_favourite", params)
        } else {
            favLanesDao.deleteFavLane(lane.id)
            val params = Bundle()
            params.putString("lane_number", lane.id)
            firebaseAnalytics.logEvent("delete_lane_by_deselecting", params)
        }
    }
}