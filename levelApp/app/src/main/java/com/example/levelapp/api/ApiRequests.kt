package com.example.levelapp.api

import com.example.levelapp.api.data.CurrentMeasurement
import com.example.levelapp.api.data.Stations
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiRequests {

    @GET("/webservices/rest-api/v2/stations.json?includeTimeseries=true&includeCurrentMeasurement=true")
    fun getStations(): Call<Stations>

    @GET("/webservices/rest-api/v2/stations/{UUID}/W.json?includeCurrentMeasurement=true")
    fun getMeasurement(@Path("UUID") uuid: String): Call<CurrentMeasurement>

    // TODO: 23.08.2021 Implement
    @GET("/webservices/rest-api/v2/stations.json?fuzzyId={SEARCH}")
    fun getFuzzyStation(@Path("SEARCH") uuid: String): Call<Stations>
}