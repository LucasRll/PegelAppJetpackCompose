package com.example.levelapp.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.levelapp.database.data.Station

class DatabaseHandler(context: Context): SQLiteOpenHelper(context, "PegelDB", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE Station (uuid char(36) NOT NULL,longname varchar(100),km DOUBLE,longitude DOUBLE,latitude DOUBLE,water varchar(50), timestamp varchar(100), value DOUBLE, trend INT, selected varchar(10))")
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    fun insertStation(station: Station) {
        val db = this.writableDatabase
        var cv = ContentValues()
        cv.put("uuid", station.uuid)
        cv.put("longname", station.longname)
        cv.put("km", station.km)
        cv.put("longitude", station.longitude)
        cv.put("latitude", station.latitude)
        cv.put("water", station.water)
        cv.put("timestamp", station.timestamp)
        cv.put("value", station.value)
        cv.put("trend", station.trend)
        cv.put("selected", station.selected.toString())

        db.insert("Station", null, cv)
    }

    fun readData(): MutableList<Station> {
        var list: MutableList<Station> = ArrayList()

        val db = this.readableDatabase
//        val deleteQuery = "DELETE FROM Station"
//        db.rawQuery(deleteQuery, null)
        val query = "SELECT * FROM Station"
        val result = db.rawQuery(query, null)
        if(result.moveToFirst()) {
            do {
                var station = Station(
                    uuid = result.getString(0),
                    longname = result.getString(1),
                    km = result.getString(2).toDouble(),
                    longitude = result.getString(3).toDouble(),
                    latitude = result.getString(4).toDouble(),
                    water = result.getString(5),
                    timestamp = result.getString(6),
                    value = result.getString(7).toDouble(),
                    trend = result.getString(8).toInt(),
                    selected = result.getString(9).toBoolean()
                )
                list.add(station)
            } while (result.moveToNext())
        }

        result.close()
        db.close()

        return list
    }

    fun updateSelected(uuid: String) {
        val db = this.writableDatabase
        val query = "UPDATE Station SET selected=\'true\' WHERE uuid =\'$uuid\'"
        val values = ContentValues()
        values.put("selected", "true")
        db.update("Station", values, "uuid =\'$uuid\'", arrayOf())
    }


}