package com.example.ula_app.android.data

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

data class Goal(
    // Whether is first time.
    val firstTime: Boolean = false,

    // Steps goal per day.
    val steps: Int = 5000,

    // get the date time that you first open this app
    val firstDateTime: Instant = Clock.System.now()
)