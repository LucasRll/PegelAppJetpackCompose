package com.example.levelapp

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.example.levelapp.api.Requests
import com.example.levelapp.database.DatabaseHandler
import com.example.levelapp.database.data.StationDb
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class AppDataModel(db: DatabaseHandler) {
    val db = db

    val api = Requests()

    var selectedStationDb = mutableStateOf(StationDb())

    init {
        selectedStationDb.value = db.getSelected()
    }

    var stationsDb = mutableStateListOf<StationDb>()

    init {
        GlobalScope.launch {
            for (i in 0 until 10 step 1) {
                stationsDb.add(StationDb())
            }
            api.getStations(db)
            var stationsTemporary = db.readData()
            if (stationsTemporary.isNotEmpty()) {
                stationsDb.clear()
                stationsDb.addAll(stationsTemporary)
            }
        }
    }

    var searchedStations = mutableStateListOf<StationDb>()

    var nearestStations = mutableStateListOf<StationDb>()

}