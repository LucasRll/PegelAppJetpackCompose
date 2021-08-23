package com.example.levelapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.levelapp.R
import com.example.levelapp.Screen
import com.example.levelapp.api.Requests
import com.example.levelapp.database.DatabaseHandler
import com.example.levelapp.database.data.StationDb
import com.example.levelapp.ui.theme.background
import com.example.levelapp.ui.theme.boxBlue
import com.example.levelapp.util.StringUtil
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


@Composable
fun SearchScreen(navController: NavController, db: DatabaseHandler) {
    Box(
        modifier = Modifier
            .background(background)
            .fillMaxSize()
    ) {
        Column {
            SearchBox()
            SearchResults(navController, db)
        }
    }
}


@Composable
fun SearchBox() {
    var search = rememberSaveable { mutableStateOf("") }

    TextField(
        value = search.value,
        onValueChange = { search.value = it },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.White
        ),
        modifier = Modifier
            .padding(start = 25.dp, top = 25.dp, end = 25.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(15.dp)),
        textStyle = MaterialTheme.typography.body1,
        maxLines = 1,
        trailingIcon = {
            Icon(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = "search",
                Modifier
                    .clip(RoundedCornerShape(30.dp))
                    .background(Color.White)
                    .padding(15.dp)
                    .size(20.dp)
            )
        }
    )
}

@Composable
fun SearchResults(navController: NavController, db: DatabaseHandler) {

    val api = Requests()
    addData(api, db)
    val stationsRemember = remember { api.stations }
    Box(
        modifier = Modifier
            //.padding(start = 25.dp, end = 25.dp)
            .fillMaxWidth()
    ) {
        LazyColumn(
            contentPadding = PaddingValues(start = 25.dp, end = 25.dp, top = 25.dp)
        ) {
            items(stationsRemember) {
                Box(
                    modifier = Modifier
                        .padding(bottom = 5.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .background(boxBlue)
                        .fillMaxWidth()
                        .clickable {
                            updateSelected(it, db)
                            if (db.getSelected().uuid == "") {
                                navController.navigate(Screen.HomeScreen.route)
                            } else {
                                navController.popBackStack()
                            }
                        }
                ) {
                    Column(
                        modifier = Modifier
                            .padding(25.dp)
                    ) {
                        Text(
                            text = StringUtil.toLeadingCapitalLetterName(it.longname),// todo
                            style = MaterialTheme.typography.h2,
                            color = Color.White,
                            modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 5.dp)
                        )
                        Text(
                            text = StringUtil.waterAndKilometer(it.water, it.km),// todo
                            style = MaterialTheme.typography.body1,
                            color = Color.White,
                        )

                    }
                }
            }
        }
    }
}

/**
 * Adds all Stations to the Search Screen
 */
fun addData(api: Requests, db: DatabaseHandler) {
    GlobalScope.launch {
        api.getStations(db)
    }
}

fun updateSelected(stationDb: StationDb, db: DatabaseHandler) {
    GlobalScope.launch {
        db.updateSelected(stationDb)
    }
}

