package com.crystalpigeon.busnovisad.model.entity

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.TypeConverters
import retrofit2.Converter
import java.util.*

@Entity(primaryKeys = ["id", "day"])
data class Schedule(
    val id: String,
    val number: String?,
    val name: String?,
    val lane: String?,
    var directionA: String?,
    var directionB: String?,
    val day: String,
    @TypeConverters(Converter::class)
    val schedule: SortedMap<String, ArrayList<String>>?,
    @TypeConverters(Converter::class)
    val scheduleA: SortedMap<String, ArrayList<String>>?,
    @TypeConverters(Converter::class)
    val scheduleB: SortedMap<String, ArrayList<String>>?,
    var extras: String?
) {
    @Ignore
    var collapsed: Boolean = true

    override fun equals(other: Any?): Boolean {
        return if (other is Schedule) {
            this.id == other.id &&
                    this.schedule == other.schedule &&
                    this.scheduleA == other.scheduleA &&
                    this.directionB == other.directionB &&
                    this.day == other.day
        } else false
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}