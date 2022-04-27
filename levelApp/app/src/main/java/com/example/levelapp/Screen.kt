package com.example.levelapp

sealed class Screen(val route: String) {
    object HomeScreen: Screen("HomeScreen")
    object SearchScreen: Screen("SearchScreen")
    object InfoScreen: Screen("InfoScreen")
    object LizenzScreen: Screen("LizenzScreen")
    object LizenzOpenSourceScreen: Screen("LizenzOpenSourceScreen")
    object QuellenScreen: Screen("QuellenScreen")
    object DatenschutzScreen: Screen("DatenschutzScreen")
}
