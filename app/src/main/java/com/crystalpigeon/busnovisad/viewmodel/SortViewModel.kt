package com.crystalpigeon.busnovisad.viewmodel

import androidx.lifecycle.ViewModel
import com.crystalpigeon.busnovisad.BusNsApp
import com.crystalpigeon.busnovisad.model.dao.FavoriteLanesDao
import com.crystalpigeon.busnovisad.model.entity.Lane
import javax.inject.Inject

class SortViewModel : ViewModel() {

    @Inject
    lateinit var favoriteLanesDao: FavoriteLanesDao

    init {
        BusNsApp.app.component.inject(this)
    }

    suspend fun getAllFavorites(): List<Lane> {
        return favoriteLanesDao.getFavLanes()
    }

    fun updateOrder(favorites: List<Lane>) {
        var order = 1
        for (favorite in favorites) {
            favoriteLanesDao.updateOrder(favorite.id, order)
            order++
        }
    }
}