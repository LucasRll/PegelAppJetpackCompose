package com.example.levelapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.levelapp.ui.LizenzOpenSourceScreen
import com.example.levelapp.ui.theme.background
import com.example.levelapp.ui.theme.boxLightBlue
import com.example.levelapp.ui.theme.textDark
import com.example.levelapp.ui.theme.white

@Composable
fun InfoScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .background(background)
            .fillMaxSize()
    ) {
        Column() {
            TopAppBar(
                modifier = Modifier
                    .height(70.dp)
                    .clip(RoundedCornerShape(bottomStart = 15.dp, bottomEnd = 15.dp))
                    .shadow(elevation = 5.dp),
                backgroundColor = boxLightBlue,

                ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
//                        .height(130.dp)
//                        .padding(25.dp)
                ) {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "back")
                    }
                    Text(
                        text = "Infos",
                        style = MaterialTheme.typography.h1,
                        color = textDark
                    )
                    IconButton(modifier = Modifier.alpha(0f), onClick = {}) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Placeholder")
                    }
                }
            }

            content(navController)
        }
    }
}

@Composable
fun content(navController: NavController) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState()),
    ) {
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = white),
        ) {
            Text(text = "Datenschutz")
        }
        Button(
            onClick = { navController.navigate(Screen.QuellenScreen.route) },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = white),
        ) {
            Text(text = "Quellenhinweise")
        }
        Button(
            onClick = { navController.navigate(Screen.LizenzScreen.route) },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = white),
        ) {
            Text(text = "Lizenz")
        }
        Button(
            onClick = { navController.navigate(Screen.LizenzOpenSourceScreen.route) },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = white),
        ) {
            Text(text = "Lizenzdetails für Open-Source-Software")
        }
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = white),
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Version")
                Text(text = "0.0.1", fontWeight = FontWeight.Light)
            }
        }
    }
}