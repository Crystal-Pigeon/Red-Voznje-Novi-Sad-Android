package com.crystalpigeon.busnovisad.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class FavouriteLane (@PrimaryKey val id: String, val type: String?)