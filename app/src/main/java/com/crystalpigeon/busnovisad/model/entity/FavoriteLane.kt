package com.crystalpigeon.busnovisad.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class FavoriteLane(@PrimaryKey val id: String, val type: String, val sort: Int)