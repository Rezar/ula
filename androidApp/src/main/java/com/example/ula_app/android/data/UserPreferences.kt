package com.example.ula_app.android.data

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class UserPreferences (
    val firstTime: Boolean = true,
    val firstDateTime: Instant = Clock.System.now(),

    // use the data store to record the fitness history
    val stepsHistory: PersistentList<StepsWithDates> = persistentListOf()
)

@Serializable
data class StepsWithDates(
    val date: Instant,
    val steps: Int
)



