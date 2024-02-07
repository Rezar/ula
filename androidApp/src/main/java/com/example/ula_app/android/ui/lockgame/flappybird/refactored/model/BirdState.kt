package com.example.ula_app.android.ui.lockgame.flappybird.refactored.model

data class BirdState(
    var xOffset: Float = 0f,
    var yOffset: Float = 0f,
    var degree: Float = 0f,
    var width: Float = 174f,
    var height: Float = 122f
) {
    fun lift(safeZone: SafeZone): BirdState = copy(
        yOffset = Math.max(-safeZone.bottom / 2 + height / 2, yOffset - LIFT_Y_SPAN),
        degree = -10f
    )

    fun fall(safeZone: SafeZone): BirdState = copy(
        yOffset = Math.min(safeZone.bottom / 2 - height / 2, yOffset + FALL_Y_SPAN),
        degree = Math.min(35f, degree + 1f)
    )
}

val LIFT_Y_SPAN: Float = 160f
val FALL_Y_SPAN: Float = 6f