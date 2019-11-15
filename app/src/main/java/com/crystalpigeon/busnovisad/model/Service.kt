package com.crystalpigeon.busnovisad.model

import android.database.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Service {
    @GET("http://www.gspns.rs/feeds/red-voznje")
    fun getTimetable(): Observable<Response<List<TimetableResponse>>>

    @GET("all-lanes")
    fun getCityLines(@Query("rv") rv: String) : Observable<Response<List<LinesResponse>>>

    @GET("all-lanes")
    fun getSuburbanLines(@Query("rv") rv: String) : Observable<Response<List<LinesResponse>>>

    @GET("all-buses/{busNumber}")
    fun getLineByNumber(@Path("busNumber") busNumber: Int, @Query("rv") rv: String)

}