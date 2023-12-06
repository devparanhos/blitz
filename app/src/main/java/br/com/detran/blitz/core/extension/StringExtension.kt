package br.com.detran.blitz.core.extension

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun String.formatDate(): String{
    var newDate = ""

    this.forEachIndexed { index, c ->
        if (index == 2 || index == 4) {
            newDate += "/$c"
        } else {
            newDate += c
        }
    }

    return newDate
}

fun String.formatTime() : String {
    var newTime = ""

    this.forEachIndexed { index, c ->
        if (index == 2) {
            newTime += ":$c"
        } else {
            newTime += c
        }
    }

    return newTime
}

fun String.isDateValid(format: String = "dd/MM/yyyy"): Boolean {
    val dateFormat = SimpleDateFormat(format, Locale.getDefault())
    dateFormat.isLenient = false

    try {
        val inputDate = dateFormat.parse(this)
        val currentDate = Calendar.getInstance().time

        if (inputDate != null && !inputDate.before(currentDate)) {
            return true
        }
    } catch (e: ParseException) {
        return false
    }

    return false
}

fun String.isTimeValid( format: String = "HH:mm"): Boolean {
    val timeFormat = SimpleDateFormat(format, Locale.getDefault())
    timeFormat.isLenient = false

    return try {
        val parsedTime = timeFormat.parse(this)
        parsedTime != null
    } catch (e: ParseException) {
        false
    }
}
