package com.example.levelapp.util

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.text.toLowerCase
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class StringUtil {

    companion object {
        private fun String.capitalizeWords(): String =
            split("-", " ").map {
                it.lowercase().replaceFirstChar {
                    if (it.isLowerCase()) it.titlecase(
                        Locale.getDefault()
                    ) else it.toString()
                }
            }.joinToString(" ")

        fun toLeadingCapitalLetterName(string: String): String {
            return string.capitalizeWords()
        }

        fun waterAndKilometer(water: String, km: Double): String {
            return water.capitalizeWords() + ", Kilometer " + km
        }

        fun changeDateFormat(strDate: String): String {
            return if (strDate != "") {
                val sourceSdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault())
                val requiredSdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
                val requiredMdf = SimpleDateFormat("HH:mm", Locale.getDefault())
                val dateStr = requiredSdf.format(sourceSdf.parse(strDate))
                val timeStr = requiredMdf.format(sourceSdf.parse(strDate))

                "$dateStr $timeStr Uhr"
            } else {
                strDate
            }
        }

    }
}