package com.example.levelapp.api.data

data class StationsItem(
    val agency: String,
    val km: Double,
    val latitude: Double,
    val longitude: Double,
    val longname: String,
    val number: String,
    val shortname: String,
    val timeseries: List<Timesery>,
    val uuid: String,
    val water: Water
)