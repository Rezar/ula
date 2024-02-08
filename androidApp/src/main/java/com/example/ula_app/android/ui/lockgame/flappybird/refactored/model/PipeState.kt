package com.example.ula_app.android.ui.lockgame.flappybird.refactored.model

import androidx.core.util.toRange

data class PipeState(
    var direction: PipeDirection = randomPipeDirection(),
    var pillarHeight: Float = randomPillarHeight(),
    var xOffset: Float = 0f,
    var threshold: Float = 0f
) {
    private val X_SPAN: Float = 5f

    fun isPassTheThreshold(): Boolean {
        return xOffset <= threshold
    }

    fun move(): PipeState = copy(xOffset = xOffset - X_SPAN)

    fun reset(targetOffset: Float): PipeState = copy(
        direction = randomPipeDirection(),
        pillarHeight = randomPillarHeight(),
        xOffset = targetOffset
    )
}

enum class PipeDirection {
    Up,
    Down
}

private fun randomPipeDirection(): PipeDirection {
    return if (Math.random() <= 0.5) PipeDirection.Up else PipeDirection.Down
}

private fun randomPillarHeight(): Float {
    val upperBound = 500f
    val lowerBound = 150f

    return lowerBound + Math.random().toFloat() * (upperBound - lowerBound)
}
