package com.example.ula_app.android.data

import kotlinx.serialization.Serializable

@Serializable
data class StepsPerDay(
    var preStepCount: Int = 0, // step count from t = 0 to n-1
    var stepCount: Int = 0 // step count from t = n assume n is today
)
