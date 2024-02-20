package com.example.ula_app.android.ui.lockgame.flappybird.model

data class BirdState(
    var xOffset: Float = 0f,
    var yOffset: Float = 0f,
    var width: Float = WIDTH,
    var height: Float = HEIGHT,
    var degree: Float = 0f
) {
    companion object {
        // Width of the bird.
        const val WIDTH: Float = 40f
        // Height of the bird.
        const val HEIGHT: Float = 28f
        // Lifting distance per tap.
        const val LIFT_Y_SPAN: Float = 100f
        // Falling distance per tick.
        const val FALL_Y_SPAN: Float = 9f
    }

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

    // TODO: Need a better way to calculate the edges of the bird. Including calculate new edges when the bird rotates
    fun birdEdge(safeZone: SafeZone): ObjectEdge {
        val birdTopBound = safeZone.height * 0.5f + yOffset - height * 0.5f
        val birdBottomBound = safeZone.height * 0.5f + yOffset + height * 0.5f
        val birdLeftBound = safeZone.width * 0.5f - width * 0.5f
        val birdRightBound = safeZone.width * 0.5f + width * 0.5f

        return ObjectEdge(
            top = birdTopBound,
            bottom = birdBottomBound,
            left = birdLeftBound,
            right = birdRightBound
        )
    }
}

