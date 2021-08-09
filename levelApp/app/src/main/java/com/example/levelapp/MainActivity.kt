package com.example.levelapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.Navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.example.levelapp.ui.theme.LevelAppTheme
import com.android.volley.VolleyError

import com.android.volley.Response

import org.json.JSONException

import org.json.JSONObject

import org.json.JSONArray

import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.levelapp.ui.HomeScreen
import com.example.levelapp.ui.SearchScreen
import java.net.URL
import java.util.concurrent.atomic.AtomicReference


class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LevelAppTheme() {
                Navigation()
            }

        }
    }

}
