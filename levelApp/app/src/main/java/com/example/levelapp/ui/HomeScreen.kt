package com.example.levelapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.levelapp.AppDataModel
import com.example.levelapp.R
import com.example.levelapp.Screen
import com.example.levelapp.database.data.StationDb
import com.example.levelapp.ui.theme.*
import com.example.levelapp.util.StringUtil
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.fade
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MyViewModel : ViewModel() {
    private val _isRefreshing = MutableStateFlow(false)

    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing.asStateFlow()

    fun refresh(appData: AppDataModel) {
        // This doesn't handle multiple 'refreshing' tasks, don't use this
        viewModelScope.launch {
            _isRefreshing.emit(true)
            appData.setSelectedStation(
                appData.db.updateMeasurement(
                    appData.api.getMeasurement(
                        appData.db.getSelected()
                    )
                )
            )
            _isRefreshing.emit(false)
        }
    }
}

@Composable
fun HomeScreen(navController: NavController, appData: AppDataModel) {

    val state = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = state,
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .height(70.dp)
                    .clip(RoundedCornerShape(bottomStart = 15.dp, bottomEnd = 15.dp))
                    .shadow(elevation = 5.dp),
                backgroundColor = boxLightBlue,
//                elevation = 5.dp,
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
//                        .height(130.dp)
//                        .padding(25.dp)
                ) {
                    IconButton(onClick = { scope.launch { if (state.drawerState.isClosed) state.drawerState.open() else state.drawerState.close() } }) {
                        Icon(Icons.Default.Menu, contentDescription = "menu")
                    }
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
//                            .background(Color.White)
                            .padding(15.dp)
                            .size(20.dp)

                    )

                }
            }
        },
        drawerShape = RoundedCornerShape(0.dp, 20.dp, 20.dp, 0.dp),

        // Inhalt Menü
        drawerContent = {
            Column(
                modifier = Modifier
                    .padding(25.dp)
            ) {
                val stationRemember = remember { appData.selectedStationDb }
                Text(
                    text = StringUtil.toLeadingCapitalLetterName(stationRemember.value.longname),
                    style = MaterialTheme.typography.h2,
                    color = textDark,
                    modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 5.dp)
                )
                Text(
                    text = StringUtil.waterAndKilometer(
                        stationRemember.value.water,
                        stationRemember.value.km
                    ),
                    style = MaterialTheme.typography.body1,
                    color = textDark,
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
                            text = stationRemember.value.value.toString(),
                            style = MaterialTheme.typography.h3,
                            color = textDark
                        )
                        Text(
                            text = "cm",
                            style = MaterialTheme.typography.body1,
                            color = textDark,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(5.dp, 10.dp)
                        )
                    }
                }
            }


            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(backgroundColor = white),
            ) {
                Spacer(modifier = Modifier.width(10.dp))
                Icon(Icons.Outlined.Settings, contentDescription = "settings")
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = "Einstellungen")
                Spacer(modifier = Modifier.weight(1f))
            }

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                onClick = { /*TODO*/ },
                colors = ButtonDefaults.buttonColors(backgroundColor = white),
            ) {
                Spacer(modifier = Modifier.width(10.dp))
                Icon(Icons.Outlined.Info, contentDescription = "settings")
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = "Infos")
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    ) {
        val viewModel: MyViewModel = viewModel()
        val isRefreshing by viewModel.isRefreshing.collectAsState()

        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing),
            onRefresh = { viewModel.refresh(appData) },
        ) {
            Column(Modifier.verticalScroll(rememberScrollState())) {
                mainView(appData)
                nearestStations(appData)
            }
        }
    }

}

@Composable
fun mainView(appData: AppDataModel) {
    val stationRemember = remember { appData.selectedStationDb }
    Box(
        modifier = Modifier
            .padding(start = 25.dp, end = 25.dp, bottom = 25.dp, top = 25.dp)
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
                text = StringUtil.changeDateFormat(stationRemember.value.timestamp),
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
                text = StringUtil.waterAndKilometer(
                    stationRemember.value.water,
                    stationRemember.value.km
                ),
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
                        text = stationRemember.value.value.toString(),
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
                if (stationRemember.value.trend == 1) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_white),
                        contentDescription = "trend",
                        tint = Color.White,
                        modifier = Modifier
                            .clip(RoundedCornerShape(45.dp))
                            //                            .background(Color.White)
                            .padding(10.dp)
                            .size(50.dp)
                            .rotate(315.0F)
                    )
                } else if (stationRemember.value.trend == -1) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_white),
                        contentDescription = "trend",
                        tint = Color.White,
                        modifier = Modifier
                            .clip(RoundedCornerShape(45.dp))
                            //                            .background(Color.White)
                            .padding(10.dp)
                            .size(50.dp)
                            .rotate(45.0F)
                    )
                } else {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_white),
                        contentDescription = "trend",
                        tint = Color.White,
                        modifier = Modifier
                            .clip(RoundedCornerShape(45.dp))
                            //                            .background(Color.White)
                            .padding(10.dp)
                            .size(50.dp)

                    )
                }
            }
        }
    }

}

fun getNearest(appData: AppDataModel) {

    GlobalScope.launch {
        // verhindern, dass die ausgewählte Station mit erscheint
        var apiList = appData.api.getNearestStations(appData)
        var selected = appData.selectedStationDb.value
        var toDelete = StationDb()
        for (station in apiList) {
            if (station.uuid == selected.uuid) {
                toDelete = station
            }
        }
        apiList.remove(toDelete)
        appData.nearestStations.swapList(apiList)
    }
}

/**
 * clears and adds new list, so there is only one recomposing
 */
fun <T> SnapshotStateList<T>.swapList(newList: List<T>) {
    clear()
    addAll(newList)
}


@Composable
fun nearestStations(appData: AppDataModel) {
    getNearest(appData)
//    val nearestStationsRemember = remember { appData.nearestStations }
    val nearestStationsRemember = appData.nearestStations
    Column(
        modifier = Modifier
            .padding(bottom = 25.dp)
    ) {
        Text(
            text = "Pegel entlang des Gewässers",
            style = MaterialTheme.typography.h2,
            color = textDark,
            modifier = Modifier.padding(start = 25.dp, end = 25.dp)
        )
        LazyRow(contentPadding = PaddingValues(start = 25.dp, end = 25.dp, top = 20.dp)) {
            items(nearestStationsRemember) {
                Box(
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .background(boxBlue)
                        .clickable {
                            selectNearestStation(it, appData)
                        }
                        .placeholder(
                            visible = nearestStationsRemember.size == 5,//todo
                            color = boxLightBlue,
                            highlight = PlaceholderHighlight.fade(
                                highlightColor = boxBlue,
                            )
                        )
                ) {
                    Column(
                        modifier = Modifier.padding(15.dp)
                    ) {
                        Text(
                            text = StringUtil.toLeadingCapitalLetterName(it.longname),
                            style = MaterialTheme.typography.body1,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(bottom = 5.dp)
                        )

                        Text(
                            text = "km " + it.km.toString(),
                            style = MaterialTheme.typography.body1,
                            color = Color.White,
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier
                                .padding(bottom = 15.dp)
                        )

                        Text(
                            text = it.value.toString(),
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

fun selectNearestStation(stationDb: StationDb, appData: AppDataModel) {
    appData.setSelectedStation(stationDb)
    getNearest(appData)
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

