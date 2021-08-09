package com.example.levelapp.ui

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import com.example.levelapp.R
import com.example.levelapp.Screen
import com.example.levelapp.ui.theme.background
import com.example.levelapp.ui.theme.boxBlue
import com.example.levelapp.ui.theme.boxDarkBlue
import com.example.levelapp.ui.theme.boxLightBlue

@Composable
fun SearchScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .background(background)
            .fillMaxSize()
    ) {
        Column {
            SearchBox()
            SearchResults(navController)
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
fun SearchResults(navController: NavController) {
    Box(
        modifier = Modifier
            //.padding(start = 25.dp, end = 25.dp)
            .fillMaxWidth()
    ) {
        LazyColumn( // TODO: 09.08.2021 LazyColumnFor
            contentPadding = PaddingValues(start = 25.dp, end = 25.dp, top = 25.dp)
        ) {
            items(10) {
                Box(
                    modifier = Modifier
                        .padding(bottom = 5.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .background(boxBlue)
                        .fillMaxWidth()
                        .clickable {
                            navController.popBackStack()
                        }
                ) {
                    Column(
                        modifier = Modifier
                            .padding(25.dp)
                    ) {
                        Text(
                            text = "Magdeburg-Strombrücke",// todo
                            style = MaterialTheme.typography.h2,
                            color = Color.White,
                            modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 5.dp)
                        )
                        Text(
                            text = "Elbe (326.67 km)",// todo
                            style = MaterialTheme.typography.body1,
                            color = Color.White,
                        )

                    }
                }
            }
        }
    }
}

