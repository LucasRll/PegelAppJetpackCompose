package com.example.levelapp.api.data

data class CurrentMeasurements(
    val stateMnwMhw: String,
    val stateNswHsw: String,
    val timestamp: String,
    val trend: Int,
    val value: Double
)