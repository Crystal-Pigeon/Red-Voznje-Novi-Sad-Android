package com.crystalpigeon.busnovisad.model

import com.google.gson.annotations.SerializedName


data class SeasonResponse (
    @SerializedName("datum") val date : String,
    @SerializedName("redv") val season: String
)

data class LaneResponse (
    @SerializedName("id") val id: String,
    @SerializedName("broj") val number: String,
    @SerializedName("linija") val laneName: String
)

data class ScheduleResponse(
    @SerializedName("id") val id: String,
    @SerializedName("broj")  val number: String,
    @SerializedName("naziv")  val name: String,
    @SerializedName("linija") val lane: String?,
    @SerializedName("linijaA") val directionA: String?,
    @SerializedName("linijaB") val directionB: String?,
    @SerializedName("dan") val day: String,
    @SerializedName("raspored") val schedule: LinkedHashMap<String,ArrayList<String>>?,
    @SerializedName("rasporedA") val scheduleA: LinkedHashMap<String, ArrayList<String>>,
    @SerializedName("rasporedB") val scheduleB: LinkedHashMap<String,ArrayList<String>>?,
    @SerializedName("dodaci") val extras: String
)

