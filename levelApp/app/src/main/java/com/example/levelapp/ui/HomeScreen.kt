package com.example.levelapp.ui

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.levelapp.R
import com.example.levelapp.Screen
import com.example.levelapp.api.Requests
import com.example.levelapp.database.DatabaseHandler
import com.example.levelapp.database.data.StationDb
import com.example.levelapp.ui.theme.*
import com.example.levelapp.util.StringUtil
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(navController: NavController, db: DatabaseHandler) {
    Box(
        modifier = Modifier
            .background(background)
            .fillMaxSize()
            .verticalScroll(state = ScrollState(0), enabled = true)
    ) {
        Column {
            headerSection(navController)
            mainView(db)
            nearestStations(db)
            rewind()
        }
    }


}

@Composable
fun headerSection(navController: NavController) {

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom,
        modifier = Modifier
            .fillMaxWidth()
            .padding(25.dp)
    ) {
        Text(
            text = "Wasserstand",
            style = MaterialTheme.typography.h1,
            color = textDark
        )

        Icon(
            painter = painterResource(id = R.drawable.ic_search),
            contentDescription = "search",
            Modifier
                .clip(RoundedCornerShape(30.dp))
                .clickable {
                    navController.navigate(Screen.SearchScreen.route)
                }
                .background(Color.White)
                .padding(15.dp)
                .size(20.dp)

        )

    }
}

fun updateSelectedStation(db: DatabaseHandler): StationDb {
    var station = db.getSelected()
    while (station.longname == "") {
        station = db.getSelected()
    }
    return station
}

/**
 * Adds all Stations to the Search Screen
 */
fun getSelected(db: DatabaseHandler) {
    GlobalScope.launch {
        db.getSelected()
    }
}

@Composable
fun mainView(db: DatabaseHandler) {
    getSelected(db)
    val stationRemember = remember { db.selectedStationDb }
    Box(
        modifier = Modifier
            .padding(start = 25.dp, end = 25.dp, bottom = 25.dp)
            .clip(RoundedCornerShape(15.dp))
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        boxDarkBlue,
                        boxLightBlue
                    )
                )
            )
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .padding(25.dp)
        ) {
            Text(
                text = StringUtil.changeDateFormat(stationRemember.value.timestamp),// todo
                style = MaterialTheme.typography.body1,
                color = Color.White,
                modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 5.dp)
            )
            Text(
                text = StringUtil.toLeadingCapitalLetterName(stationRemember.value.longname),
                style = MaterialTheme.typography.h2,
                color = Color.White,
                modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 5.dp)
            )
            Text(
                text = StringUtil.waterAndKilometer(stationRemember.value.water, stationRemember.value.km),
                style = MaterialTheme.typography.body1,
                color = Color.White,
                modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 20.dp)
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 0.dp, 15.dp, 0.dp)
            ) {
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = stationRemember.value.value.toString(), //todo
                        style = MaterialTheme.typography.h3,
                        color = Color.White
                    )
                    Text(
                        text = "cm",
                        style = MaterialTheme.typography.body1,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(5.dp, 10.dp)
                    )
                }
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow),
                    contentDescription = "search",
                    Modifier
                        .clip(RoundedCornerShape(45.dp))
                        .background(Color.White)
                        .padding(10.dp)
                        .size(30.dp)
                        .rotate(45.0F) //todo

                )
            }
        }
    }
}

@Composable
fun nearestStations(db: DatabaseHandler) {
    //val nearestStationsRemember = remember { api.stations }
    Column(
        modifier = Modifier
            .padding(bottom = 25.dp)
    ) {
        Text(
            text = "Stationen aus der Umgebung",
            style = MaterialTheme.typography.h2,
            color = textDark,
            modifier = Modifier.padding(start = 25.dp, end = 25.dp)
        )
        LazyRow(contentPadding = PaddingValues(start = 25.dp, end = 25.dp, top = 20.dp)) {
            items(4) {
                Box(
                    modifier = Modifier
                        .padding(end = 15.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .background(boxBlue)
                        .clickable { /*todo*/ }
                ) {
                    Column(
                        modifier = Modifier.padding(15.dp)
                    ) {
                        Text(
                            text = "Schönebeck", //todo
                            style = MaterialTheme.typography.body1,
                            color = Color.White,
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier
                                .padding(bottom = 15.dp)
                        )

                        Text(
                            text = "203.0", // todo
                            style = MaterialTheme.typography.body1,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 30.sp
                        )


                        Text(
                            text = "cm",
                            style = MaterialTheme.typography.body1,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )

                    }

                }
            }
        }
    }
}

@Composable
fun rewind() {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp))
            .background(Color.White)
            .fillMaxWidth()
            .height(400.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(25.dp)
        ) {
            Text(
                text = "20-Tage Rückblick",
                style = MaterialTheme.typography.h2,
                color = textDark
            )
        }
    }
}