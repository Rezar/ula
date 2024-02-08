package com.example.ula_app.android.ui.lockgame.flappybird.refactored.model

data class ViewState(
    val roadStateList: List<RoadState> = emptyList(),
    val pipeStateList: List<PipeState> = emptyList(),
    val birdState: BirdState = BirdState(),
    val safeZone: SafeZone = SafeZone(),
    val gameStatus: GameStatus = GameStatus.Ide,
    val isTapping: Boolean = false
)

data class SafeZone(
    var width: Float = 0f,
    var height: Float = 0f
) {
    fun initiated(): Boolean {
        return width != 0f || height != 0f
    }
}

data class ObjectEdge(
    var top: Float = 0f,
    var bottom: Float = 0f,
    var left: Float = 0f,
    var right: Float = 0f
)

enum class GameStatus {
    Ide,
    Running,
    GameOver
}

enum class GameOverCauses {
    BirdHitGround,
    BirdHitSky,
    BirdHitPipe
}


