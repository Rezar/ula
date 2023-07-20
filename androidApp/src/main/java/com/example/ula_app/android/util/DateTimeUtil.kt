package com.example.ula_app.android.util

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

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

        /*
        * Get the local time
        * */
        fun getLocalDateTime(): LocalTime {
            val currentDateTime: Instant = getCurrentDateTime()
            return currentDateTime.toLocalDateTime(getSystemTimeZone()).time
        }

        /*
        * Get the local date
        * */
        fun getLocalDate(kotlinInstant: Instant): LocalDate {
            val date = kotlinInstant.toLocalDateTime(TimeZone.currentSystemDefault()).date  // Type of LocalDate

            return date
        }
        /*
        * Get the date of the week
        * */
        fun getDayOfWeek(kotlinInstant: Instant): String {
            val dateTime = kotlinInstant.toLocalDateTime(getSystemTimeZone())
            val dayOfWeek = dateTime.dayOfWeek

            return dayOfWeek.toString()
        }
    }
}