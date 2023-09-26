package com.example.ula_app.android.data

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class UserPreferences (
    val firstTime: Boolean = true,
    val firstDateTime: Instant = Clock.System.now(),

    // settings
    val displaySteps: Boolean = false,
    val displayMonster: Boolean = false,
    val maxThreshold: Double = .2,
    val minThreshold: Double =.2,
    val effectiveDays: Int = 3,
    val effectiveDate: Instant = Clock.System.now(),
    val dailyGoal: Int = 5000,
    val weeklyGoal: Int = 20000,

    // use the data store to record the fitness history
//    val stepsHistory: PersistentList<StepsWithDates> = persistentListOf()
    val stepsHistory: String = ""
)




