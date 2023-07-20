package com.example.ula_app.android.data

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class UserPreferences (
    val firstTime: Boolean = true,
    val firstDateTime: Instant = Clock.System.now(),

    // use the data store to record the fitness history
//    val stepsHistory: PersistentList<StepsWithDates> = persistentListOf()
    val stepsHistory: String = ""
)




