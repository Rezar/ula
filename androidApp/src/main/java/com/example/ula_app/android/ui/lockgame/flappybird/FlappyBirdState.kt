package com.example.ula_app.android.ui.lockgame.flappybird

data class FlappyBirdState(
    val birdSpeedX: Float = Default.bird.initialSpeedX
)

enum class GameStatus {
    Running,
    Pause
}
