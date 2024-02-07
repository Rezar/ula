package com.example.ula_app.android.ui.lockgame.flappybird.refactored.model

data class RoadState(
    var xOffset: Float = 0f,
    var threshold: Float = 0f
) {
    fun move(): RoadState = copy(xOffset = xOffset - X_SPAN)

    fun reset(): RoadState = copy(xOffset = threshold)
}

val X_SPAN: Float = 5f