package com.example.pennypincherapplication.util

import org.joda.time.DateTime
import org.joda.time.DateTimeConstants
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

object DateUtil {
    const val DATE_FORMAT = "dd-MM-yyyy"

    fun getCurrentDate(): String {
        return DateTime.now().toString(DATE_FORMAT)
    }

    fun getCurrentWeeksDates(): List<String> {
        val dates = mutableListOf<String>()
        val now = LocalDate.now()

        for (day in DateTimeConstants.MONDAY..DateTimeConstants.SUNDAY) {
            val localDate = now.withDayOfWeek(day)
            dates.add(getFormattedDate(localDate, DATE_FORMAT))
        }

        return dates
    }

    fun currentMonthOfYear(): String {
        val date = DateTime.now().toString(DATE_FORMAT)
        val split = date.split("-")
        return "${split[1]}-${split[2]}"
    }

    fun getDayName(dateString: String): String? {
        val formatter = SimpleDateFormat("dd-MM-yyyy", Locale.US)
        return try {
            val date = formatter.parse(dateString)
            val calendar = Calendar.getInstance().apply { time = date }
            calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.US)
        } catch (e: Exception) {
            null // Handle parsing exceptions gracefully
        }
    }

    private fun getFormattedDate(date: LocalDate, format: String): String {
        val simpleDateFormat = SimpleDateFormat(format)
        return simpleDateFormat.format(date.toDate())
    }
}
