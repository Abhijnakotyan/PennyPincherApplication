package com.example.pennypincherapplication.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

object DateUtil {
    const val DATE_FORMAT = "dd-MM-yyyy"

    @RequiresApi(Build.VERSION_CODES.O)
    fun getCurrentDate(): String {
        return LocalDate.now().format(DateTimeFormatter.ofPattern(DATE_FORMAT))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getCurrentWeeksDates(): List<String> {
        val dates = mutableListOf<String>()
        val now = LocalDate.now()

        for (day in DayOfWeek.values()) {
            val localDate = now.with(DayOfWeek.valueOf(day.name))
            dates.add(getFormattedDate(localDate))
        }

        return dates
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun currentMonthOfYear(): String {
        val date = LocalDate.now().format(DateTimeFormatter.ofPattern(DATE_FORMAT))
        val split = date.split("-")
        return "${split[1]}-${split[2]}"
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDayName(dateString: String): String? {
        val formatter = DateTimeFormatter.ofPattern(DATE_FORMAT, Locale.US)
        return try {
            val date = LocalDate.parse(dateString, formatter)
            date.dayOfWeek.name.capitalize(Locale.ROOT) // Use Java's built-in functionality to get the day name
        } catch (e: Exception) {
            null // Handle parsing exceptions gracefully
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getFormattedDate(date: LocalDate): String {
        val formatter = DateTimeFormatter.ofPattern(DATE_FORMAT)
        return date.format(formatter)
    }
}
