package com.example.levelapp

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.levelapp.database.DatabaseHandler
import com.example.levelapp.ui.HomeScreen
import com.example.levelapp.ui.SearchScreen

@Composable
fun Navigation(appData: AppDataModel) {
    val navController = rememberNavController()
    if (appData.db.readData().size == 1 || appData.db.getSelected().uuid == "") {
        NavHost(navController = navController, startDestination = Screen.SearchScreen.route) {
            composable(route = Screen.HomeScreen.route) {
                HomeScreen(navController, appData)
            }
            composable(route = Screen.SearchScreen.route) {
                SearchScreen(navController, appData)
            }
        }
    } else {
        NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {
            composable(route = Screen.HomeScreen.route) {
                HomeScreen(navController, appData)
            }
            composable(route = Screen.SearchScreen.route) {
                SearchScreen(navController, appData)
            }
        }
    }
}