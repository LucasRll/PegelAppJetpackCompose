package com.example.levelapp

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.levelapp.ui.*

@Composable
fun Navigation(appData: AppDataModel) {
    val navController = rememberNavController()
    /*if (appData.db.readData().size == 1 || appData.db.getSelected().uuid == "") {
        NavHost(navController = navController, startDestination = Screen.SearchScreen.route) {
            composable(route = Screen.HomeScreen.route) {
                HomeScreen(navController, appData)
            }
            composable(route = Screen.SearchScreen.route) {
                SearchScreen(navController, appData)
            }
            composable(route = Screen.InfoScreen.route) {
                InfoScreen(navController = navController)
            }
            composable(route = Screen.LizenzScreen.route) {
                LizenzScreen(navController = navController)
            }
            composable(route = Screen.LizenzOpenSourceScreen.route) {
                LizenzOpenSourceScreen(navController = navController)
            }
            composable(route = Screen.QuellenScreen.route) {
                QuellenScreen(navController = navController)
            }
            composable(route = Screen.DatenschutzScreen.route) {
                DatenschutzScreen(navController = navController)
            }
            composable(route = Screen.SettingsScreen.route) {
                SettingsScreen(navController, appData)
            }
        }
    } else {*/
        NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {
            composable(route = Screen.HomeScreen.route) {
                HomeScreen(navController, appData)
            }
            composable(route = Screen.SearchScreen.route) {
                SearchScreen(navController, appData)
            }
            composable(route = Screen.InfoScreen.route) {
                InfoScreen(navController = navController)
            }
            composable(route = Screen.LizenzScreen.route) {
                LizenzScreen(navController = navController)
            }
            composable(route = Screen.LizenzOpenSourceScreen.route) {
                LizenzOpenSourceScreen(navController = navController)
            }
            composable(route = Screen.QuellenScreen.route) {
                QuellenScreen(navController = navController)
            }
            composable(route = Screen.DatenschutzScreen.route) {
                DatenschutzScreen(navController = navController)
            }
            composable(route = Screen.SettingsScreen.route) {
                SettingsScreen(navController, appData)
            }
        }
//    }
}