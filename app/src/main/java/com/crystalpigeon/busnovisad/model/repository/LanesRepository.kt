package com.crystalpigeon.busnovisad.model.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.crystalpigeon.busnovisad.BusNsApp
import com.crystalpigeon.busnovisad.model.BusDatabase
import com.crystalpigeon.busnovisad.model.Lane
import com.crystalpigeon.busnovisad.model.LanesDao
import com.crystalpigeon.busnovisad.model.Service
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

class LanesRepository {
    @Inject
    lateinit var api: Service
    @Inject
    lateinit var context: Context

    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private var lanesDao: LanesDao
    private var isFresh = false

    init {
        BusNsApp.app.component.inject(this)
        lanesDao = BusDatabase.getDatabase(context).lanesDao()
    }

    fun getLanes(type: String): LiveData<List<Lane>> {
        coroutineScope.launch(Dispatchers.IO) {
            if (!isFresh) {
                try {
                    val lanes = api.getLanes(type)
                    lanes.forEach {
                        lanesDao.insert(Lane(it.id, it.number, it.laneName, type))
                    }
                    isFresh = true
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }

        return lanesDao.getLanes(type)
    }
}