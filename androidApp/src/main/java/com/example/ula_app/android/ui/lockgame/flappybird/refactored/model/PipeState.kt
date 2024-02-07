package com.example.ula_app.android.ui.lockgame.flappybird.refactored.model

data class PipeState(
    var xOffset: Float = 0f,
    var yOffset: Float = 0f,
    var direction: PipeDirection = PipeDirection.Up
) {
    fun move() {

    }

    fun reset() {

    }
}

enum class PipeDirection {
    Up,
    Down
}
