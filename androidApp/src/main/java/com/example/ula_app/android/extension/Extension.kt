package com.example.ula_app.android.extension

import kotlinx.datetime.DayOfWeek

fun DayOfWeek.abbr(): String {

    return when (this) {
        DayOfWeek.MONDAY -> {
            "MON"
        }
        DayOfWeek.TUESDAY -> {
            "TUE"
        }
        DayOfWeek.WEDNESDAY -> {
            "WED"
        }
        DayOfWeek.THURSDAY -> {
            "THUR"
        }
        DayOfWeek.FRIDAY -> {
            "FRI"
        }
        DayOfWeek.SATURDAY -> {
            "SAT"
        }
        DayOfWeek.SUNDAY -> {
            "SUN"
        }

        else -> {
            ""
        }
    }
}