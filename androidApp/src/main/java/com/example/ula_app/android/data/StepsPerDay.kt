package com.example.ula_app.android.data

import kotlinx.serialization.Serializable

@Serializable
data class StepsPerDay(
    var preStepCount: Int = 0,
    var stepCount: Int = 0
)
