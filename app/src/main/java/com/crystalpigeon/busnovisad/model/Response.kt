package com.crystalpigeon.busnovisad.model

data class TimetableResponse (
    val date : String,
    val redv: String
)

data class LinesResponse (
    val id: String,
    val broj: String,
    val linija: String
)