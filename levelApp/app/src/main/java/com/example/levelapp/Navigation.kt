package com.example.levelapp

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.levelapp.database.DatabaseHandler
import com.example.levelapp.ui.HomeScreen
import com.example.levelapp.ui.SearchScreen

@Composable
fun Navigation(db: DatabaseHandler) {
    val navController = rememberNavController()
//    if (db.readData().size == 1 || db.getSelected().uuid == "") {
        NavHost(navController = navController, startDestination = Screen.SearchScreen.route) {
            composable(route = Screen.HomeScreen.route) {
                HomeScreen(navController, db)
            }
            composable(route = Screen.SearchScreen.route) {
                SearchScreen(navController, db)
            }
        }
   /* } else {
        NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {
            composable(route = Screen.HomeScreen.route) {
                HomeScreen(navController, db)
            }
            composable(route = Screen.SearchScreen.route) {
                SearchScreen(navController, db)
            }
        }
    }*/
}