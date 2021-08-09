package com.example.levelapp.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.levelapp.R

val segeoUi = FontFamily(
    listOf(
        Font(R.font.segoe_ui, FontWeight.Normal),
        Font(R.font.segoe_ui_bold, FontWeight.Bold),
    )
)




// Set of Material typography styles to start with
val Typography = Typography(
    h1 = TextStyle(
        fontFamily = segeoUi,
        fontWeight = FontWeight.Bold,
        fontSize = 35.sp
    ),
    h2 = TextStyle(
        fontFamily = segeoUi,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    ),
    body1 = TextStyle(
        fontFamily = segeoUi,
        fontWeight = FontWeight.Normal,
        fontSize = 15.sp
    ),
    h3 = TextStyle(
        fontFamily = segeoUi,
        fontWeight = FontWeight.Bold,
        fontSize = 60.sp
    ),
    body2 = TextStyle(
        fontFamily = segeoUi,
        fontWeight = FontWeight.Bold,
        fontSize = 15.sp
    )

)