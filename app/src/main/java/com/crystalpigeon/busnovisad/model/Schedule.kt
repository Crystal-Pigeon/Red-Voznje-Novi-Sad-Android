package com.crystalpigeon.busnovisad.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import retrofit2.Converter
import java.util.ArrayList

@Entity
class Schedule(
    @PrimaryKey @ColumnInfo(name = "id") val id: String, val number: String?,
    val name: String?,
    val lane: String?,
    val directionA: String?,
    val directionB: String?,
    val day: String,
    @TypeConverters(Converter::class)
    val schedule: HashMap<String, ArrayList<String>>?,
    @TypeConverters(Converter::class)
    val scheduleA: HashMap<String, ArrayList<String>>?,
    @TypeConverters(Converter::class)
    val scheduleB: HashMap<String, ArrayList<String>>?,
    val extras: String?
)