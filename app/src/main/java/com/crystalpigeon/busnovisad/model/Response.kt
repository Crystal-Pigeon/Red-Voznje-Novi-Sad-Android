package com.crystalpigeon.busnovisad.model

data class TimetableResponse (
    val date : String,
    val redv: String
)

data class UrbanSuburbanLinesResponse (
    val id: String,
    val broj: String,
    val linija: String
)

data class LineResponse(
    val id: String,
    val broj: String,
    val naziv: String,
    val linija: String?,
    val linijaA: String?,
    val linijaB: String?,
    val dan: String,
    val raspored: HashMap<String,ArrayList<String>>?,
    val rasporedA: HashMap<String,ArrayList<String>>?,
    val rasporedB: HashMap<String,ArrayList<String>>?,
    val dodaci: String
)