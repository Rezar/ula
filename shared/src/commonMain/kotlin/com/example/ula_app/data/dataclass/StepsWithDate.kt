package com.example.ula_app.data.dataclass

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable

@Serializable
data class StepsWithDate(
    val date: Long,
    var steps: Int
)