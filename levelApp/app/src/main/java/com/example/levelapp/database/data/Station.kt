package com.example.levelapp.database.data

class Station {

    var uuid: String = ""
    var longname: String = ""
    var km: Double = 0.0
    var longitude: Double = 0.0
    var latitude: Double = 0.0
    var water: String = ""

    constructor(uuid: String, longname: String, km: Double, longitude: Double, latitude: Double, water: String) {
        this.uuid = uuid
        this.longname = longname
        this.km = km
        this.longitude = longitude
        this.latitude = latitude
        this.water = water
    }
}