package com.example.levelapp.database.data


class Station {

    var uuid: String = ""
    var longname: String = ""
    var km: Double = 0.0
    var longitude: Double = 0.0
    var latitude: Double = 0.0
    var water: String = ""
    var timestamp: String = ""
    var value: Double = 0.0
    var trend: Int = 0
    var selected: Boolean = false

    constructor()

    constructor(uuid: String, longname: String, km: Double, longitude: Double, latitude: Double, water: String, timestamp: String, value: Double, trend: Int, selected: Boolean) {
        this.uuid = uuid
        this.longname = longname
        this.km = km
        this.longitude = longitude
        this.latitude = latitude
        this.water = water
        this.timestamp = timestamp
        this.value = value
        this.trend = trend
        this.selected = selected
    }

    constructor(uuid: String, longname: String, km: Double, longitude: Double, latitude: Double, water: String, timestamp: String, value: Double, trend: Int) {
        this.uuid = uuid
        this.longname = longname
        this.km = km
        this.longitude = longitude
        this.latitude = latitude
        this.water = water
        this.timestamp = timestamp
        this.value = value
        this.trend = trend
        this.selected = false
    }

}