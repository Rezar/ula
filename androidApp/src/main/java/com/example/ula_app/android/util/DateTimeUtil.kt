package com.example.ula_app.android.util

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone

class DateTimeUtil {

    companion object {
        /*
        * Get the current time zone
        * */
        fun getSystemTimeZone(): TimeZone {
            return TimeZone.currentSystemDefault()
        }

        /*
        * Get the current date and time
        * default as UTC
        * */
        fun getCurrentDateTime(): Instant {
            return Clock.System.now()
        }

        /*
        * Get the time difference
        * */
        fun getDayDifference(next: Instant, previous: Instant): Long {
            return (next - previous).inWholeDays
        }
    }
}