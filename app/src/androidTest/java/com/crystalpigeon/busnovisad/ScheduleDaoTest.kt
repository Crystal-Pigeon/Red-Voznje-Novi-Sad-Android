package com.crystalpigeon.busnovisad

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.crystalpigeon.busnovisad.model.BusDatabase
import com.crystalpigeon.busnovisad.model.dao.SchedulesDao
import com.crystalpigeon.busnovisad.model.entity.Schedule
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class ScheduleDaoTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var schedulesDao: SchedulesDao
    private lateinit var database: BusDatabase

    @Before
    fun createDatabase() {
        val context: Context = ApplicationProvider.getApplicationContext()
        database = Room.inMemoryDatabaseBuilder(context, BusDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        schedulesDao = database.schedulesDao()
    }

    @After @Throws(IOException::class)
    fun closeDatabase() {
        database.close()
    }

    @Test @Throws(IOException::class)
    fun insertScheduleAndGetScheduleByDay() = runBlocking {
        val map: LinkedHashMap<String, ArrayList<String>> = linkedMapOf()
        map["1"] = arrayListOf("10", "20")
        val schedule = Schedule(
            "1.",
            "1",
            "Klisa - Centar",
            null,
            "raspored A",
            "raspored B",
            "R",
            map,
            map,
            map,
            "dodaci"
        )

        schedulesDao.insert(schedule)

        val allSchedulesByDay = schedulesDao.getSchedulesByDay("R").waitForValue()
        assertEquals(allSchedulesByDay[0].id, schedule.id)
        assertEquals(allSchedulesByDay[0].number, schedule.number)
        assertEquals(allSchedulesByDay[0].name, schedule.name)
        assertEquals(allSchedulesByDay[0].lane, schedule.lane)
        assertEquals(allSchedulesByDay[0].directionA, schedule.directionA)
        assertEquals(allSchedulesByDay[0].directionB, schedule.directionB)
        assertEquals(allSchedulesByDay[0].day, schedule.day)
        assertEquals(allSchedulesByDay[0].schedule, schedule.schedule)
        assertEquals(allSchedulesByDay[0].scheduleA, schedule.scheduleA)
        assertEquals(allSchedulesByDay[0].scheduleB, schedule.scheduleB)
        assertEquals(allSchedulesByDay[0].extras, schedule.extras)
    }

    @Test @Throws(IOException::class)
    fun insertScheduleAndGetScheduleByDayAndLanes() = runBlocking {
        val map: LinkedHashMap<String, ArrayList<String>> = linkedMapOf("1" to arrayListOf("jedan", "dva"), "2" to arrayListOf("jedan", "dva"), "3" to arrayListOf("jedan", "dva"))
        val schedule1 = Schedule(
            "1*", "1", "Klisa - Centar", "Klisa - Centar",
            "raspored A", "raspored B", "R", map, map, map, "dodaci"
        )
        val schedule1Duplicate = Schedule(
            "1*", "1", "Klisa - Centar", "Klisa - Centar",
            "raspored A", "raspored B", "R", map, map, map, "dodaci"
        )

        val schedule1N = Schedule(
            "1*", "1", "Klisa - Centar", "Klisa - Centar",
            "raspored A", "raspored B", "N", map, map, map, "dodaci"
        )


        val schedule2 = Schedule(
            "2.", "1", "Klisa - Centar", "Klisa - Centar",
            "raspored A", "raspored B", "R", map, map, map, "dodaci"
        )

        val schedule3 = Schedule(
            "14.", "1", "Klisa - Centar", "Klisa - Centar",
            "raspored A", "raspored B", "R", map, map, map, "dodaci"
        )

        schedulesDao.insert(schedule1)
        schedulesDao.insert(schedule1Duplicate)
        schedulesDao.insert(schedule1N)
        schedulesDao.insert(schedule2)
        schedulesDao.insert(schedule3)

        val allSchedulesByDayAndLanes = schedulesDao.getSchedulesByDayAndLanes("R", arrayListOf("1*", "2.", "14.")).waitForValue()
        assertEquals(3, allSchedulesByDayAndLanes.size)
    }

    @Test @Throws(IOException::class)
    fun deleteAllSchedules() = runBlocking {
        val map: LinkedHashMap<String, ArrayList<String>> = linkedMapOf("1" to arrayListOf("jedan", "dva"), "2" to arrayListOf("jedan", "dva"), "3" to arrayListOf("jedan", "dva"))
        val schedule = Schedule(
            "1.", "1", "Klisa - Centar", "Klisa - Centar",
            "raspored A", "raspored B", "N", map, map, map, "dodaci"
        )

        schedulesDao.insert(schedule)

        schedulesDao.deleteAllSchedules()

        val get = schedulesDao.getSchedulesByDay("N")
        assertEquals(null, get.value?.size)
    }
}