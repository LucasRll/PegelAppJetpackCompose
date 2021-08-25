package com.example.levelapp.api

import android.provider.ContactsContract
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


    suspend fun getStations(db: DatabaseHandler) {


        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiRequests::class.java)

        try {
            val response = api.getStations().awaitResponse()
            if (response.isSuccessful) {
                val data = response.body()!!
                for (station: StationsItem in data) {
                    val neededTimeSeries = 0
                    for (element in station.timeseries) {
                        if (element.shortname == "W") {
                            neededTimeSeries == station.timeseries.indexOf(element)
                        }
                    }
                    val stationDb = StationDb(
                        station.uuid,
                        station.longname,
                        station.km,
                        station.longitude,
                        station.latitude,
                        station.water.longname,
                        station.timeseries[neededTimeSeries].currentMeasurement.timestamp,
                        station.timeseries[neededTimeSeries].currentMeasurement.value,
                        station.timeseries[neededTimeSeries].currentMeasurement.trend
                    )
                    db.safeInsertStation(stationDb)
                }
            }
        } catch (e: Exception) {
            Log.println(Log.ERROR, "Connection failed", e.toString())
        }

    }

    suspend fun getMeasurement(stationDb: StationDb): StationDb {
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
            }
        } catch (e: Exception) {
            Log.println(Log.ERROR, "Connection failed", e.toString())
        }

        return stationDb
    }

    suspend fun getNearestStations(
        stationDb: StationDb,
        db: DatabaseHandler
    ): MutableList<StationDb> {
        db.nearestStations.clear()
        var list: MutableList<StationDb> = ArrayList()
        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiRequests::class.java)

        try {
            val response = api.getNearestStations(stationDb.water, stationDb.km).awaitResponse()
            if (response.isSuccessful) {
                val data = response.body()!!
               // Log.println(Log.ERROR, "data", data.toString())
                for (station: StationsItem in data) {
                    val neededTimeSeries = 0
                    for (element in station.timeseries) {
                        if (element.shortname == "W") {
                            neededTimeSeries == station.timeseries.indexOf(element)
                        }
                    }
                    val stationDb = StationDb(
                        station.uuid,
                        station.longname,
                        station.km,
                        station.longitude,
                        station.latitude,
                        station.water.longname,
                        station.timeseries[neededTimeSeries].currentMeasurement.timestamp,
                        station.timeseries[neededTimeSeries].currentMeasurement.value,
                        station.timeseries[neededTimeSeries].currentMeasurement.trend
                    )
                    list.add(stationDb)

                }
            }
        } catch (e: Exception) {
            Log.println(Log.ERROR, "Connection failed", e.toString())
        }
        db.nearestStations.addAll(list)
        return list
    }

}