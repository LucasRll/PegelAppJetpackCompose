package com.example.levelapp.api.data

data class Timesery(
    val currentMeasurement: CurrentMeasurement,
    val equidistance: Int,
    val gaugeZero: GaugeZero,
    val longname: String,
    val shortname: String,
    val unit: String
)