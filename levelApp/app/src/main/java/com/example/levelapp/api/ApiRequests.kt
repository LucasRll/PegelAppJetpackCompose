package com.example.levelapp.api

import com.example.levelapp.api.data.Stations
import retrofit2.Call
import retrofit2.http.GET

interface ApiRequests {

    @GET("/webservices/rest-api/v2/stations.json")
    fun getStations(): Call<Stations>
}