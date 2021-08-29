package com.example.levelapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import com.example.levelapp.ui.theme.LevelAppTheme

import com.example.levelapp.database.DatabaseHandler
import com.google.accompanist.systemuicontroller.rememberSystemUiController


class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var db = DatabaseHandler(this)
        var appData = AppDataModel(db)
        setContent {
            // Remember a SystemUiController
            val systemUiController = rememberSystemUiController()

            SideEffect {
                // Update all of the system bar colors to be transparent, and use
                // dark icons if we're in light theme
                systemUiController.setStatusBarColor(color =  Color(android.graphics.Color.parseColor("#B4CEFB")),
                    darkIcons = true)
            }
            LevelAppTheme() {
                Navigation(appData)
            }

        }
    }

}
