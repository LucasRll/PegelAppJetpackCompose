package com.example.levelapp

sealed class Screen(val route: String) {
    object HomeScreen: Screen("HomeScreen")
    object SearchScreen: Screen("SearchScreen")
}
