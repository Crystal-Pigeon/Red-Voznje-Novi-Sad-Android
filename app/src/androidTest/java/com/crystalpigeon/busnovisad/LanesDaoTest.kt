package com.crystalpigeon.busnovisad

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.crystalpigeon.busnovisad.model.BusDatabase
import com.crystalpigeon.busnovisad.model.Lane
import com.crystalpigeon.busnovisad.model.LanesDao
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.lang.Exception

@RunWith(AndroidJUnit4::class)
class LanesDaoTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var lanesDao: LanesDao
    private lateinit var db: BusDatabase

    @Before
    fun createDb() {
        val context: Context = ApplicationProvider.getApplicationContext()
        db = Room.inMemoryDatabaseBuilder(context, BusDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        lanesDao = db.lanesDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertLaneAndGetLanes() = runBlocking {
        val lane = Lane("1.", "1", "Klisa - Centar", "rvg")
        lanesDao.insert(lane)

        val allLanes = lanesDao.getLanes("rvg").waitForValue()
        assertEquals(allLanes[0].id, lane.id)
        assertEquals(allLanes[0].number, lane.number)
        assertEquals(allLanes[0].laneName, lane.laneName)
        assertEquals(allLanes[0].type, lane.type)
    }

    @Test
    @Throws(Exception::class)
    fun getOnlyLanesByRvg() = runBlocking {
        val type = "rvg"
        val lane = Lane("1.", "1", "Klisa - Centar - Strand", type)
        lanesDao.insert(lane)

        val lane2 = Lane("4.", "4", "Zeleznicka - Liman IV", type)
        lanesDao.insert(lane2)

        val allLanes = lanesDao.getLanes(type).waitForValue()
        assertEquals(allLanes.size, 2)
        assertEquals(allLanes[0].type, type)
        assertEquals(allLanes[1].type, type)
    }

    @Test
    @Throws(Exception::class)
    fun getOnlyLanesByRvp() = runBlocking {
        val type = "rvp"
        val wrongType = "rvg"
        val lane = Lane("32.", "32", "Temerin - Novi Sad", type)
        lanesDao.insert(lane)

        val lane2 = Lane("33*", "33", "Gospodjinci - Novi Sad", type)
        lanesDao.insert(lane2)

        val lane3 = Lane("4.", "4", "Zeleznicka - Liman IV", wrongType)
        lanesDao.insert(lane3)

        val allLanes = lanesDao.getLanes(type).waitForValue()
        assertEquals(2, allLanes.size)
        assertEquals(allLanes[0].type, type)
        assertEquals(allLanes[1].type, type)
    }
}