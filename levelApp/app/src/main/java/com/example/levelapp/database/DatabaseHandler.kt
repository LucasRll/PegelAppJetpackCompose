package com.example.levelapp.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.example.levelapp.database.data.StationDb

class DatabaseHandler(context: Context) : SQLiteOpenHelper(context, "PegelDB", null, 1) {


    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE Station (uuid char(36) NOT NULL,longname varchar(100),km DOUBLE,longitude DOUBLE,latitude DOUBLE,water varchar(50), timestamp varchar(100), value DOUBLE, trend INT, selected varchar(10))")
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }


    /**
     * only insert if not already there
     */
    fun safeInsertStation(stationDb: StationDb) {
        val db = this.writableDatabase

        val query = "SELECT * FROM Station WHERE uuid=\'${stationDb.uuid}\'"
        val result = db.rawQuery(query, null)
        if (!result.moveToFirst()) {
            var cv = ContentValues()
            cv.put("uuid", stationDb.uuid)
            cv.put("longname", stationDb.longname)
            cv.put("km", stationDb.km)
            cv.put("longitude", stationDb.longitude)
            cv.put("latitude", stationDb.latitude)
            cv.put("water", stationDb.water)
            cv.put("timestamp", stationDb.timestamp)
            cv.put("value", stationDb.value)
            cv.put("trend", stationDb.trend)
            cv.put("selected", stationDb.selected.toString())

            db.insert("Station", null, cv)
        }
    }


    fun readData(): MutableList<StationDb> {
        var list: MutableList<StationDb> = ArrayList()

        val db = this.writableDatabase
        val query = "SELECT DISTINCT * FROM Station"
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()) {
            do {
                var station = StationDb(
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

        return list
    }


    fun getSelected(): StationDb {
        val db = this.writableDatabase
        var list: MutableList<StationDb> = ArrayList()

        val query = "SELECT * FROM Station WHERE selected=\'true\'"
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()) {
            do {
                var station = StationDb(
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
        //db.close()
        if (list.isNotEmpty()) {
            return list.first()
        }
        return StationDb()
    }

    fun updateSelected(stationDb: StationDb) {
        val db = this.writableDatabase
        val querySelected = "UPDATE Station SET selected=\'false\'"
        db.execSQL(querySelected)
        val query = "UPDATE Station SET selected=\'true\' WHERE uuid=\'${stationDb.uuid}\'"
        db.execSQL(query)
    }


    fun updateMeasurement(stationDb: StationDb): StationDb {
        val db = this.writableDatabase
        val query =
            "UPDATE Station SET timestamp=\'${stationDb.timestamp}\',value='${stationDb.value}',trend='${stationDb.trend}'  WHERE uuid=\'${stationDb.uuid}\'"
        db.execSQL(query)
        return getSelected()

    }

    fun fuzzySearch(searchString: String): MutableList<StationDb> {
        val db = this.writableDatabase
        var list: MutableList<StationDb> = ArrayList()

        val query =
            "SELECT DISTINCT * FROM Station WHERE longname LIKE \'%${searchString.uppercase()}%\' OR water LIKE \'%${searchString.uppercase()}%\'"
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()) {
            do {
                var station = StationDb(
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

        return list
    }


}