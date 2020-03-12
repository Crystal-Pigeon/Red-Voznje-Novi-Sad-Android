package com.crystalpigeon.busnovisad.model

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Service {
    @GET("http://www.gspns.rs/feeds/red-voznje")
    suspend fun getSeason(): Response<List<SeasonResponse>?>

    @GET("all-lanes")
    suspend fun getLanes(@Query("rv") rv: String): Response<List<LaneResponse>>

    @GET("all-buses/{busNumber}")
    suspend fun getBusSchedule(@Path("busNumber") busNumber: String, @Query("rv") rv: String):
            Response<List<ScheduleResponse>>

}