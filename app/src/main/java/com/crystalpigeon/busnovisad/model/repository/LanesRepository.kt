package com.crystalpigeon.busnovisad.model.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.crystalpigeon.busnovisad.BusNsApp
import com.crystalpigeon.busnovisad.Const
import com.crystalpigeon.busnovisad.model.Service
import com.crystalpigeon.busnovisad.model.dao.FavoriteLanesDao
import com.crystalpigeon.busnovisad.model.dao.LanesDao
import com.crystalpigeon.busnovisad.model.entity.Lane
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LanesRepository {
    @Inject
    lateinit var api: Service
    @Inject
    lateinit var context: Context

    @Inject
    lateinit var lanesDao: LanesDao
    @Inject
    lateinit var favoriteLanesDao: FavoriteLanesDao

    init {
        BusNsApp.app.component.inject(this)
    }

    fun getLanes(type: String): LiveData<List<Lane>> {
        return lanesDao.getLanes(type)
    }

    private suspend fun refreshLanes(type: String) {
        try {
            val lanes = api.getLanes(type)
            lanesDao.deleteForType(type)
            lanes.forEach { lanesDao.insert(Lane(it.id, it.number, it.laneName, type)) }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun getFavorites(): List<Lane> {
        return favoriteLanesDao.getFavLanes()
    }

    suspend fun getNonFavorites(): List<Lane> {
        return lanesDao.getNonFavorites()
    }

    /*
    Fetch urban and suburban lanes and store them in database
     */
    suspend fun cacheAllLanes() {
        val urban = GlobalScope.async { refreshLanes(Const.LANE_URBAN) }
        val suburban = GlobalScope.async { refreshLanes(Const.LANE_SUBURBAN) }
        urban.await()
        suburban.await()
    }
}