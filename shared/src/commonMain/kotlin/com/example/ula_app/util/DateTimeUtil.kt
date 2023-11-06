package com.example.ula_app.util

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
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
        fun getCurrentInstant(): Instant {
            return Clock.System.now()
        }

        /*
        * Get the time difference
        * */
        fun getDayDifference(next: Instant, previous: Instant): Long {
            return (next - previous).inWholeDays
        }

        fun getDayDifference(next: Long, previous: Long): Long {
            val nextInstant = Instant.fromEpochSeconds(next)
            val previousInstant = Instant.fromEpochSeconds(previous)

            return (nextInstant - previousInstant).inWholeDays
        }

        fun getDayDifference(previous: Long): Long {
            val currentInstant = getCurrentInstant()
            val previousInstant = Instant.fromEpochSeconds(previous)

            return (currentInstant - previousInstant).inWholeDays
        }

        /*
        * Get the local time
        * */
        fun getLocalDateTime(): LocalDateTime {
            val currentDateTime: Instant = getCurrentInstant()
            return currentDateTime.toLocalDateTime(getSystemTimeZone())
        }

//        /*
//        * Get the local date
//        * */
//        fun getLocalDate(kotlinInstant: Instant): LocalDate {
//            val date = kotlinInstant.toLocalDateTime(TimeZone.currentSystemDefault()).date  // Type of LocalDate
//
//            return date
//        }
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