package com.example.levelapp.api

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import com.example.levelapp.api.data.StationsItem
import com.example.levelapp.database.data.Station
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://www.pegelonline.wsv.de/"

class Requests {

    var stations = mutableStateListOf(Station())

    suspend fun getStations(){

        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiRequests::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            try {
                stations.removeAt(0)
                val response = api.getStations().awaitResponse()
                if (response.isSuccessful) {
                    val data = response.body()!!
                    for (station: StationsItem in data) {
                        val stationDb = Station(
                            station.uuid,
                            station.longname,
                            station.km,
                            station.longitude,
                            station.latitude,
                            station.water.longname,
                            "",
                            0.0,
                            -999
                        )
                        stations.add(stationDb)
                    }
                }
            } catch (e: Exception) {
                Log.println(Log.ERROR, "Connection failed", "Seems like something went wrong...")
            }
        }

    }

}