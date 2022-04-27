package com.example.levelapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.levelapp.ui.theme.background
import com.example.levelapp.ui.theme.boxLightBlue
import com.example.levelapp.ui.theme.textDark

@Composable
fun LizenzScreen(navController: NavController) {
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
                        text = "Lizenz",
                        style = MaterialTheme.typography.h1,
                        color = textDark
                    )
                    IconButton(modifier = Modifier.alpha(0f), onClick = {}) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Placeholder")
                    }
                }
            }

            Text(
                modifier = Modifier.padding(25.dp), text = "Personal Use License\n" +
                        "\n" +
                        "The content owner grants the buyer a non-exclusive, perpetual, personal use\n" +
                        "license to view, download, display, and copy the content, subject to the\n" +
                        "following restrictions:\n" +
                        "\n" +
                        "The content is licensed for personal use only, not commercial use. The\n" +
                        "content may not be used in any way whatsoever in which you charge money,\n" +
                        "collect fees, or receive any form of remuneration. The content may not be\n" +
                        "resold, relicensed, sub-licensed, rented, leased, or used in advertising.\n" +
                        "\n" +
                        "Title and ownership, and all rights now and in the future, of and for the\n" +
                        "content remain exclusively with the content owner.\n" +
                        "\n" +
                        "There are no warranties, express or implied. The content is provided 'as\n" +
                        "is.'\n" +
                        "\n" +
                        "Neither the content owner, payment processing service, nor hosting service\n" +
                        "will be liable for any third party claims or incidental, consequential, or\n" +
                        "other damages arising out of this license or the buyer's use of the content."
            )
        }
    }

}