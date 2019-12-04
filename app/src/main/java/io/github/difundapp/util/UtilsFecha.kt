package io.github.difundapp.util

import java.text.SimpleDateFormat
import java.util.*


private const val DATE_PATTERN = "yyyy-MM-dd"

class UtilsFecha {

    fun conversionATextoFechaNormalizado(timestamp: Long): String {
        val date = Date(timestamp)
        val formatter = SimpleDateFormat(DATE_PATTERN, Locale.US)

        return formatter.format(date)
    }
}