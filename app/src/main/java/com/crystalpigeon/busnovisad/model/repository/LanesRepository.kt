package com.crystalpigeon.busnovisad.model.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.crystalpigeon.busnovisad.BusNsApp
import com.crystalpigeon.busnovisad.Const
import com.crystalpigeon.busnovisad.model.BusDatabase
import com.crystalpigeon.busnovisad.model.entity.Lane
import com.crystalpigeon.busnovisad.model.dao.LanesDao
import com.crystalpigeon.busnovisad.model.Service
import com.crystalpigeon.busnovisad.model.dao.FavoriteLanesDao
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LanesRepository {
    @Inject
    lateinit var api: Service
    @Inject
    lateinit var context: Context

    private var lanesDao: LanesDao
    private var favoriteLanesDao: FavoriteLanesDao

    init {
        BusNsApp.app.component.inject(this)
        lanesDao = BusDatabase.getDatabase(context).lanesDao()
        favoriteLanesDao = BusDatabase.getDatabase(context).favLanesDao()
    }

    fun getLanes(type: String): LiveData<List<Lane>> {
        return lanesDao.getLanes(type)
    }

    private suspend fun refreshLanes(type: String) {
        try {
            val lanes = api.getLanes(type)
            lanes.forEach { lanesDao.insert(Lane(it.id, it.number, it.laneName, type)) }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun getFavorites(): List<Lane> {
        return favoriteLanesDao.getFavLanes().map { Lane(it.id, null, null, it.type) }
    }

    suspend fun getNonFavorites(): List<Lane> {
        return lanesDao.getLanesExcept(favoriteLanesDao.getFavLanes().map { it.id })
    }

    /*
    Fetch urban and suburban lanes and store them in database
     */
    suspend fun cacheAllLanes() {
        refreshLanes(Const.LANE_URBAN)
        refreshLanes(Const.LANE_SUBURBAN)
    }
}