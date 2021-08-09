package com.example.levelapp.database.data

class Measurement {

    var uuid: String = ""
    var timestamp: String = ""
    var value: Double = 0.0
    var trend: Int = 0

    constructor(uuid: String, timestamp: String, value: Double, trend: Int) {
        this.uuid = uuid
        this.timestamp = timestamp
        this.value = value
        this.trend = trend
    }
}