package com.example.levelapp.api

import com.example.levelapp.api.data.CurrentMeasurement
import com.example.levelapp.api.data.Stations
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiRequests {

    @GET("/webservices/rest-api/v2/stations.json?includeTimeseries=true&includeCurrentMeasurement=true")
    fun getStations(): Call<Stations>

    @GET("/webservices/rest-api/v2/stations/{UUID}/W/currentmeasurement.json")
    fun getMeasurement(@Path("UUID") uuid: String): Call<CurrentMeasurement>

    @GET("/webservices/rest-api/v2/stations.json?includeTimeseries=true&includeCurrentMeasurement=true&radius=50")
    fun getNearestStations(@Query("waters") water: String, @Query("km") km: Double): Call<Stations>
}