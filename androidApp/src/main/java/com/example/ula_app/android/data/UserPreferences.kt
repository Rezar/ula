package com.example.ula_app.android.data

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

data class UserPreferences (
    val firstTime: Boolean = true,
    val firstDateTime: Instant = Clock.System.now()
)



