package com.example.levelapp.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.levelapp.database.data.Measurement
import com.example.levelapp.database.data.Station

class DatabaseHandler(context: Context): SQLiteOpenHelper(context, "PegelDB", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE Station (uuid varchar(50) PRIMARY KEY,longname varchar(100),km DOUBLE,longitude DOUBLE,latitude DOUBLE,water varchar(50))")
        db?.execSQL("CREATE TABLE Measurement (uuid varchar(50) PRIMARY KEY, timestamp varchar(100), value DOUBLE, trend INT)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    fun insertData(station: Station) {
        val db = this.writableDatabase
        var cv = ContentValues()
        cv.put("uuid", station.uuid)
        cv.put("longname", station.longname)
        cv.put("km", station.km)
        cv.put("longitude", station.longitude)
        cv.put("latitude", station.latitude)
        cv.put("water", station.water)

        db.insert("Station", null, cv)
    }

    fun insertData(measurement: Measurement) {
        val db = this.writableDatabase
        var cv = ContentValues()
        cv.put("uuid", measurement.uuid)
        cv.put("timestamp", measurement.timestamp)
        cv.put("value", measurement.value)
        cv.put("trend", measurement.trend)

        db.insert("Measurement", null, cv)
    }

}