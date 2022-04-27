package com.example.levelapp.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.levelapp.ui.theme.background
import com.example.levelapp.ui.theme.boxLightBlue
import com.example.levelapp.ui.theme.textDark
import com.example.levelapp.ui.theme.white

@Composable
fun QuellenScreen(navController: NavController) {
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
                        text = "Quellen",
                        style = MaterialTheme.typography.h1,
                        color = textDark
                    )
                    IconButton(modifier = Modifier.alpha(0f), onClick = {}) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Placeholder")
                    }
                }
            }

            Column(
                modifier = Modifier
                    .padding(25.dp)
                    .fillMaxWidth()
                    .height(100.dp),
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Text(text = "Gewässerdaten", style = MaterialTheme.typography.h2)
                Text(
                    text = "PEGELONLINE",
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.Bold
                )
                val context = LocalContext.current
                val intent = remember {
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://www.pegelonline.wsv.de/")
                    )
                }

                Button(
                    colors = ButtonDefaults.buttonColors(backgroundColor = white),
                    onClick = { context.startActivity(intent) }) {
                    Text(text = "https://www.pegelonline.wsv.de/")
                }
            }

        }
    }

}