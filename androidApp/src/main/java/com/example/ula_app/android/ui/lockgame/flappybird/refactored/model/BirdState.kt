package com.example.ula_app.android.ui.lockgame.flappybird.refactored.model

data class BirdState(
    var xOffset: Float = 0f,
    var yOffset: Float = 0f,
    var width: Float = 40f,
    var height: Float = 28f,
    var degree: Float = 0f
) {
    private val LIFT_Y_SPAN: Float = 130f
    private val FALL_Y_SPAN: Float = 7f

    fun lift(safeZone: SafeZone): BirdState = copy(
        // yOffset - LIFT_Y_SPAN >= (bird.height - safeZone.bottom) / 2
        yOffset = Math.max((height - safeZone.height) / 2f, yOffset - LIFT_Y_SPAN),
        degree = -10f
    )

    fun fall(safeZone: SafeZone): BirdState = copy(
        // yOffset + FALL_Y_SPAN <= (safeZone.height - bird.height) / 2
        yOffset = Math.min((safeZone.height - height) / 2f, yOffset + FALL_Y_SPAN),
        // bird.degree + 1f <= 35f
        degree = Math.min(35f, degree + 1f)
    )

//    fun birdEdge(safeZone: SafeZone): ObjectEdge {
//
//    }
}

