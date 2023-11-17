package com.example.ula_app.data.dataclass

import kotlinx.serialization.Serializable

@Serializable
data class StepsPerDay(
    var preStepCount: Int = 0,
    var stepCount: Int = 0
)
