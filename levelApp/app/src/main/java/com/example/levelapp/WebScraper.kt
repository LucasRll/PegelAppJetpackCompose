package com.example.levelapp

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.net.URL


class WebScraper {

    companion object {
        suspend fun currentMeasurementMagdeburgStrombruecke(): CurrentMeasurement {
            return retrieveCurrentMeasurement("https://www.pegelonline.wsv.de/webservices/rest-api/v2/stations/MAGDEBURG-STROMBRÜCKE/W/currentmeasurement.json")
        }


        suspend fun retrieveCurrentMeasurement(url: String): CurrentMeasurement {
            val apiResponse =
                URL(url).readText()
            val json = JSONObject(apiResponse)
            return CurrentMeasurement(
                json.getString("timestamp"),
                json.getDouble("value"),
                json.getInt("trend"),
                json.getString("stateMnwMhw"),
                json.getString("stateNswHsw")
            )
        }
    }

}
