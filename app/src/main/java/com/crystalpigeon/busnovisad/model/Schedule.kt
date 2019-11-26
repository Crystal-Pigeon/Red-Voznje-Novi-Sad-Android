package com.crystalpigeon.busnovisad.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Schedule(
    @PrimaryKey @ColumnInfo(name = "id") val id: String, val number: String?,
    val name: String?,
    val lane: String?,
    val directionA: String?,
    val directionB: String?,
    val day: String,
    val schedule: HashMap<String, ArrayList<String>>?,
    val scheduleA: HashMap<String, ArrayList<String>>?,
    val scheduleB: HashMap<String, ArrayList<String>>?,
    val extras: String
)