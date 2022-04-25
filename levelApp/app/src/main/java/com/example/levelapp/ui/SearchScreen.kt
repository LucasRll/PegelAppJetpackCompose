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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.levelapp.AppDataModel
import com.example.levelapp.R
import com.example.levelapp.Screen
import com.example.levelapp.api.Requests
import com.example.levelapp.database.DatabaseHandler
import com.example.levelapp.database.data.StationDb
import com.example.levelapp.ui.theme.background
import com.example.levelapp.ui.theme.boxBlue
import com.example.levelapp.util.StringUtil
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.fade
import com.google.accompanist.placeholder.placeholder
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import com.example.levelapp.ui.theme.*
import com.google.accompanist.placeholder.shimmer

@Composable
fun SearchScreen(navController: NavController, appData: AppDataModel) {
    Box(
        modifier = Modifier
            .background(background)
            .fillMaxSize()
    ) {
        Column {
            SearchBox(navController, appData)
            // SearchResults(navController, db)
        }
    }
}


@Composable
fun SearchBox(navController: NavController, appData: AppDataModel) {
    var searchString = rememberSaveable { mutableStateOf("") }
    var searchBoolean = rememberSaveable { mutableStateOf(false) }

    val api = Requests()
    addData(api, appData)
    val stationsDbRemember = remember { appData.stationsDb }
    val stationsDbSearchRemember = remember { appData.searchedStations }

    TextField(
        value = searchString.value,
        onValueChange = {
            /**
             * Wenn Value vorhanden boolean auf true, sonst false und alles wird angezeigt
             */
            searchString.value = it
            if (it != "") {
                searchBoolean.value = true
                updateSearch(appData, it)
            } else {
                searchBoolean.value = false
            }
        },
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

    /**
     * Nicht schön aber selten, je nachdem ob gesucht wird, wird eine andere List angezeigt
     */
    if (!searchBoolean.value) {

        Box(
            modifier = Modifier
                //.padding(start = 25.dp, end = 25.dp)
                .fillMaxWidth()
        ) {
            LazyColumn(
                contentPadding = PaddingValues(start = 25.dp, end = 25.dp, top = 25.dp)
            ) {
                items(stationsDbRemember) {
                    Box(
                        modifier = Modifier
                            .padding(bottom = 5.dp)
                            .clip(RoundedCornerShape(15.dp))
                            .background(boxBlue)
                            .fillMaxWidth()
                            .clickable {
                                onClick(it, appData, navController)
                            }
                            .placeholder(
                                visible = stationsDbRemember[0].uuid == "",
                                color = boxLightBlue,
                                highlight = PlaceholderHighlight.fade(
                                    highlightColor = boxLightBlueShimmer,
                                )
                            )
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(25.dp)
                        ) {
                            Text(
                                text = StringUtil.toLeadingCapitalLetterName(it.longname),
                                style = MaterialTheme.typography.h2,
                                color = Color.White,
                                modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 5.dp)
                            )
                            Text(
                                text = StringUtil.waterAndKilometer(it.water, it.km),
                                style = MaterialTheme.typography.body1,
                                color = Color.White,
                            )

                        }
                    }
                }
            }
        }
    } else {
        Box(
            modifier = Modifier
                //.padding(start = 25.dp, end = 25.dp)
                .fillMaxWidth()
        ) {
            LazyColumn(
                contentPadding = PaddingValues(start = 25.dp, end = 25.dp, top = 25.dp)
            ) {
                items(stationsDbSearchRemember) {
                    Box(
                        modifier = Modifier
                            .padding(bottom = 5.dp)
                            .clip(RoundedCornerShape(15.dp))
                            .background(boxBlue)
                            .fillMaxWidth()
                            .clickable {
                                onClick(it, appData, navController)
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
}

/*@Composable
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
}*/

/**
 * Adds all Stations to the Search Screen
 */
fun addData(api: Requests, appData: AppDataModel) {
    GlobalScope.launch {
        api.getStations(appData.db)
        appData.stationsDb.addAll(appData.db.readData())
    }
}

fun updateSelected(stationDb: StationDb, appData: AppDataModel) {
    GlobalScope.launch {
        appData.db.updateMeasurement(appData.api.getMeasurement(stationDb))
        appData.setSelectedStation(stationDb)
        getNearest(appData)
    }
}

fun updateSearch(appData: AppDataModel, search: String) {
    GlobalScope.launch {
        val result = appData.db.fuzzySearch(search)
        if (result.isNotEmpty()) {
            appData.searchedStations.clear()
            appData.searchedStations.addAll(result)
        }
    }
}

fun updateMeasurement(api: Requests, db: DatabaseHandler, stationDb: StationDb) {
    GlobalScope.launch {
        api.getMeasurement(stationDb)
    }
}

fun onClick(
    stationDb: StationDb,
    appData: AppDataModel,
    navController: NavController
) {
    updateSelected(stationDb, appData)
    navController.navigate(Screen.HomeScreen.route)
}

