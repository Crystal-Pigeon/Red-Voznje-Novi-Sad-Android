package com.crystalpigeon.busnovisad.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Lane(@PrimaryKey @ColumnInfo(name = "id") val id: String, val number: String?, val laneName: String?, val type: String)