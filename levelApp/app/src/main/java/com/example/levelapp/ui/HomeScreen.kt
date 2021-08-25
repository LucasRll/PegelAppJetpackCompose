package com.example.levelapp.ui

import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.levelapp.R
import com.example.levelapp.Screen
import com.example.levelapp.api.Requests
import com.example.levelapp.database.DatabaseHandler
import com.example.levelapp.database.data.StationDb
import com.example.levelapp.ui.theme.*
import com.example.levelapp.util.StringUtil
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.fade
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MyViewModel : ViewModel() {
    private val _isRefreshing = MutableStateFlow(false)

    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing.asStateFlow()

    fun refresh(db: DatabaseHandler) {
        // This doesn't handle multiple 'refreshing' tasks, don't use this
        viewModelScope.launch {
            // A fake 2 second 'refresh'
            _isRefreshing.emit(true)
            val api = Requests()
            db.updateMeasurement(api.getMeasurement(db.getSelected()))
            _isRefreshing.emit(false)
        }
    }
}

@Composable
fun HomeScreen(navController: NavController, db: DatabaseHandler) {

    val viewModel: MyViewModel = viewModel()
    val isRefreshing by viewModel.isRefreshing.collectAsState()

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing),
        onRefresh = { viewModel.refresh(db) },
    ) {
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
                if (stationRemember.value.trend == 1) { //todo kein Kreis
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow),
                        contentDescription = "search",
                        Modifier
                            .clip(RoundedCornerShape(45.dp))
                            .background(Color.White)
                            .padding(10.dp)
                            .size(30.dp)
                            .rotate(315.0F)
                    )
                } else if (stationRemember.value.trend == -1) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow),
                        contentDescription = "search",
                        Modifier
                            .clip(RoundedCornerShape(45.dp))
                            .background(Color.White)
                            .padding(10.dp)
                            .size(30.dp)
                            .rotate(45.0F)
                    )
                } else {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow),
                        contentDescription = "search",
                        Modifier
                            .clip(RoundedCornerShape(45.dp))
                            .background(Color.White)
                            .padding(10.dp)
                            .size(30.dp)

                    )
                }
            }
        }
    }

}

fun getNearest(db: DatabaseHandler) {
        GlobalScope.launch {
            val api = Requests()
            api.getNearestStations(db.selectedStationDb.value, db)
        }
}



@Composable
fun nearestStations(db: DatabaseHandler) {
    getNearest(db)
    val nearestStationsRemember = remember { db.nearestStations }
    Column(
        modifier = Modifier
            .padding(bottom = 25.dp)
    ) {
        Text(
            text = "Pegel aus der Umgebung",
            style = MaterialTheme.typography.h2,
            color = textDark,
            modifier = Modifier.padding(start = 25.dp, end = 25.dp)
        )
        LazyRow(contentPadding = PaddingValues(start = 25.dp, end = 25.dp, top = 20.dp)) {
            items(nearestStationsRemember) {
                Box(
                    modifier = Modifier
                        .padding(end = 15.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .background(boxBlue)
                        .clickable { /*todo*/ }
                        .placeholder(
                            visible = nearestStationsRemember.size == 0,//todo
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