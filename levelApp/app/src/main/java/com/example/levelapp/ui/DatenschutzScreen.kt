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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.levelapp.ui.theme.background
import com.example.levelapp.ui.theme.boxLightBlue
import com.example.levelapp.ui.theme.textDark

@Composable
fun DatenschutzScreen(navController: NavController) {
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
                        text = "Datenschutz",
                        style = MaterialTheme.typography.h1,
                        color = textDark
                    )
                    IconButton(modifier = Modifier.alpha(0f), onClick = {}) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Placeholder")
                    }
                }
            }

            Column(modifier = Modifier.padding(25.dp)) {
                Text(text = "Datenschutz", style = MaterialTheme.typography.h1)
                Text(text = "I. Allgemein", style = MaterialTheme.typography.h2)
                Text(
                    text = "Wir nehmen den Schutz Ihrer personenbezogenen Daten sehr ernst und behandeln diese\n" +
                            "vertraulich und entsprechend der gesetzlichen Datenschutzvorschriften sowie dieser\n" +
                            "Datenschutzerklärung. Diese Datenschutzerklärung gilt für unsere mobilen iPhone- und AndroidApps (im Folgenden „APP“). In ihr werden Art, Zweck und Umfang der Datenerhebung im\n" +
                            "Rahmen der APP-Nutzung erläutert. Wir weisen darauf hin, dass die Datenübertragung im\n" +
                            "Internet Sicherheitslücken aufweisen kann. Ein lückenloser Schutz der Daten vor dem Zugriff\n" +
                            "durch Dritte ist nicht möglich.",
                    style = MaterialTheme.typography.body1
                )
                Text(
                    text = "Verantwortliche Stelle",
                    style = MaterialTheme.typography.body1,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Verantwortliche Stelle für die Datenverarbeitung im Rahmen dieser APP ist:\n" +
                            "Webdesign Lucas Rolle\n" +
                            "Große Diesdorfer Str. 247\n" +
                            "D – 39108 Magdeburg\n" +
                            "E-Mail: kontak@lucas-rolle.de\n" +
                            "Webseite: www.lucas-rolle.de\n" +
                            "Tel.: +49 162 8005096\n" +
                            "„Verantwortliche Stelle“ ist die Stelle, die personenbezogene Daten (z. B. Namen, E-MailAdressen etc.) erhebt, verarbeitet oder nutzt.",
                    style = MaterialTheme.typography.body1
                )
            }
        }
    }

}