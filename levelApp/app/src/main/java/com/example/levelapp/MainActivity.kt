package com.example.levelapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.levelapp.ui.theme.LevelAppTheme

import com.example.levelapp.database.DatabaseHandler


class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var db = DatabaseHandler(this)
        setContent {
            LevelAppTheme() {
                Navigation(db)
            }

        }
    }

}
