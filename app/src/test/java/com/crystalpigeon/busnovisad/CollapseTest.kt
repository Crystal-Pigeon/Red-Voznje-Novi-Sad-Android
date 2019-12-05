package com.crystalpigeon.busnovisad

import com.crystalpigeon.busnovisad.view.adapter.ScheduleHoursAdapter
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class CollapseTest{
    private lateinit var adapter: ScheduleHoursAdapter

    @Before
    fun setAdapter(){
        adapter = ScheduleHoursAdapter()
    }

    @Test
    fun hasBeforeMiddleNext() {
        val hoursExpanded = ArrayList<String>()
        hoursExpanded.add("08")
        hoursExpanded.add("09")
        hoursExpanded.add("10")
        hoursExpanded.add("11")
        hoursExpanded.add("12")

        val collapsed = adapter.getCollapsedList(hoursExpanded, 10)
        Assert.assertEquals(3, collapsed.size)
        Assert.assertEquals("09", collapsed[0])
        Assert.assertEquals("10", collapsed[1])
        Assert.assertEquals("11", collapsed[2])
    }

    @Test
    fun hasMiddleNextNext() {
        val hoursExpanded = ArrayList<String>()
        hoursExpanded.add("04")
        hoursExpanded.add("05")
        hoursExpanded.add("06")
        val collapsed = adapter.getCollapsedList(hoursExpanded, 4)
        Assert.assertEquals(collapsed[0], "04")
        Assert.assertEquals(collapsed[1], "05")
        Assert.assertEquals(collapsed[2], "06")
    }

    @Test
    fun noCurrentHasAfter() {
        val hoursExpanded = ArrayList<String>()
        hoursExpanded.add("04")
        hoursExpanded.add("05")
        hoursExpanded.add("06")
        val collapsed = adapter.getCollapsedList(hoursExpanded, 2)
        Assert.assertEquals(collapsed[0], "04")
        Assert.assertEquals(collapsed[1], "05")
        Assert.assertEquals(collapsed[2], "06")
    }

    @Test
    fun noCurrentNoAfter() {
        val hoursExpanded = ArrayList<String>()
        hoursExpanded.add("04")
        hoursExpanded.add("05")
        hoursExpanded.add("06")
        hoursExpanded.add("07")
        hoursExpanded.add("08")
        val collapsed = adapter.getCollapsedList(hoursExpanded, 10)
        Assert.assertEquals(collapsed[0], "04")
        Assert.assertEquals(collapsed[1], "05")
        Assert.assertEquals(collapsed[2], "06")
    }

    @Test
    fun noCurrentHourListSizeOne() {
        val hoursExpanded = ArrayList<String>()
        hoursExpanded.add("04")
        val collapsed = adapter.getCollapsedList(hoursExpanded, 10)
        Assert.assertEquals(1, collapsed.size)
        Assert.assertEquals("04", collapsed[0])
    }

    @Test
    fun noCurrentHourListSizeTwo() {
        val hoursExpanded = ArrayList<String>()
        hoursExpanded.add("04")
        hoursExpanded.add("22")
        val collapsed = adapter.getCollapsedList(hoursExpanded, 10)
        Assert.assertEquals(2, collapsed.size)
        Assert.assertEquals("22", collapsed[0])
        Assert.assertEquals("04", collapsed[1])
    }
}