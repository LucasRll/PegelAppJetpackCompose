package com.example.levelapp.api

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import com.example.levelapp.api.data.StationsItem
import com.example.levelapp.database.DatabaseHandler
import com.example.levelapp.database.data.StationDb
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://www.pegelonline.wsv.de/"

class Requests {

    var stations = mutableStateListOf(StationDb())

    suspend fun getStations(db: DatabaseHandler) {

        // TODO: 22.08.2021 Loading animation 

        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiRequests::class.java)

            try {
                stations.removeAt(0)
                val response = api.getStations().awaitResponse()
                if (response.isSuccessful) {
                    val data = response.body()!!
                    for (station: StationsItem in data) {
                        val stationDb = StationDb(
                            station.uuid,
                            station.longname,
                            station.km,
                            station.longitude,
                            station.latitude,
                            station.water.longname,
                            station.timeseries[0].currentMeasurement.timestamp,
                            station.timeseries[0].currentMeasurement.value,
                            station.timeseries[0].currentMeasurement.trend
                        )
                        stations.add(stationDb)
                        db.safeInsertStation(stationDb)
                    }
                }
            } catch (e: Exception) {
                Log.println(Log.ERROR, "Connection failed", "Seems like something went wrong...")
            }

    }

   /* suspend fun getMeasurement(stationDb: StationDb, db: DatabaseHandler) {
        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiRequests::class.java)

        try {
            val response = api.getMeasurement(stationDb.uuid).awaitResponse()
            if (response.isSuccessful) {
                val data = response.body()!!
                stationDb.timestamp = data.timestamp
                stationDb.value = data.value
                stationDb.trend = data.trend
                stationDb.selected = true
                db.insertSelected(stationDb)
            }
        } catch (e: Exception) {
            Log.println(Log.ERROR, "Connection failed", "Seems like something went wrong...")
        }
    }*/

}