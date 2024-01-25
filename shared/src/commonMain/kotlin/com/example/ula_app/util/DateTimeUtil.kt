package com.example.ula_app.util

import kotlinx.datetime.Clock
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
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
        fun nowInInstant(): Instant {
            return Clock.System.now()
        }

        fun nowInstantShift(instant: Instant, value: Int, dateTimeUnit: DateTimeUnit): Instant {
            return when {
                value < 0 -> {
                    instant.minus(value, dateTimeUnit, getSystemTimeZone())
                }
                value > 0 -> {
                    instant.plus(value, dateTimeUnit, getSystemTimeZone())
                }

                else -> {
                    instant
                }
            }
        }

        /*
        * Get the time difference
        * */
        fun getDayDifference(next: Instant, prev: Instant): Long {
            return (next - prev).inWholeDays
        }

        fun getDayDifference(next: LocalDateTime, prev: LocalDateTime): Long {
            val nextInstant = next.toInstant(getSystemTimeZone())
            val prevInstant = prev.toInstant(getSystemTimeZone())

            return (nextInstant - prevInstant).inWholeDays
        }

        fun getDayDifference(next: Long, prev: Long): Long {
            val nextInstant = Instant.fromEpochSeconds(next)
            val prevInstant = Instant.fromEpochSeconds(prev)

            return (nextInstant - prevInstant).inWholeDays
        }

        /*
        * Get the local time
        * */
        fun nowInLocalDateTime(): LocalDateTime {
            val currentDateTime: Instant = nowInInstant()
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
        fun getDayOfWeek(kotlinInstant: Instant): DayOfWeek {
            val dateTime = kotlinInstant.toLocalDateTime(getSystemTimeZone())

            return dateTime.dayOfWeek
        }

        fun getDayOfWeek(epochSecond: Long): DayOfWeek {
            val instant = Instant.fromEpochSeconds(epochSecond)
            val dateTime = instant.toLocalDateTime(getSystemTimeZone())

            return dateTime.dayOfWeek
        }

        fun getAbbrOfDayOfWeek(dayOfWeek: DayOfWeek): String {
            return when (dayOfWeek) {
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
    }
}