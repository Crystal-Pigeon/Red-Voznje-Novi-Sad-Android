package com.crystalpigeon.busnovisad.model

import com.squareup.moshi.Json

data class SeasonResponse (
    val date : String,
    val redv: String
)

data class LaneResponse (
    val id: String,
    @field:Json(name = "broj") val number: String,
    @field:Json(name = "linija") val laneName: String
)

data class ScheduleResponse(
    val id: String,
    @field:Json(name = "broj")  val number: String,
    @field:Json(name = "naziv")  val name: String,
    @field:Json(name = "linija") val lane: String?,
    @field:Json(name = "linijaA") val directionA: String?,
    @field:Json(name = "linijaB") val directionB: String?,
    @field:Json(name = "dan") val day: String,
    @field:Json(name = "raspored") val schedule: HashMap<String,ArrayList<String>>?,
    @field:Json(name = "rasporedA") val scheduleA: HashMap<String,ArrayList<String>>?,
    @field:Json(name = "rasporedB") val scheduleB: HashMap<String,ArrayList<String>>?,
    @field:Json(name = "dodaci") val extras: String
)