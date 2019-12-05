package com.crystalpigeon.busnovisad.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Lane(@PrimaryKey val id: String, val number: String?, val laneName: String?, val type: String)