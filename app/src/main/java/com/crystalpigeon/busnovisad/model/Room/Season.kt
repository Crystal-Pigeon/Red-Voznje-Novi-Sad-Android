package com.crystalpigeon.busnovisad.model.Room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "season")
class Season {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    @ColumnInfo(name = "datum")
    var date: String = ""
    @ColumnInfo(name = "redv")
    var season: String = ""

    constructor()

    constructor(id: Int, date: String, season: String) {
        this.id = id
        this.date = date
        this.season = season
    }
}