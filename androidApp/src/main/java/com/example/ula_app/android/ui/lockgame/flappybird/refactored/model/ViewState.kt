package com.example.ula_app.android.ui.lockgame.flappybird.refactored.model

data class ViewState(
    val roadStateList: List<RoadState> = emptyList(),
    val birdState: BirdState = BirdState(),
    val safeZone: SafeZone = SafeZone(),
    val gameStatus: GameStatus = GameStatus.Ide,
    val isTapping: Boolean = false
)

data class SafeZone(
    var top: Float = 0f,
    var bottom: Float = 0f,
    var left: Float = 0f,
    var right: Float = 0f
) {
    fun initiated(): Boolean {
        return top != 0f || bottom != 0f || left != 0f || right != 0f
    }
}

enum class GameStatus {
    Ide,
    Running,
    GameOver
}

enum class GameOverCauses {
    BirdHitGround,
    BirdHitPipe
}


