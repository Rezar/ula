package com.example.ula_app.android.ui.lockgame.dinogame.model


data class ViewState(
    val roadStateList: List<RoadState> = emptyList(), // List of two roads
    val cactusStateList: List<CactusState> = emptyList(), // List of two or three cactus
    val dinoState: DinoState = DinoState(),
    val safeZone: SafeZone = SafeZone(),
    val gameStatus: GameStatus = GameStatus.Idle,
    val score: Int = 0,
    val bestScore: Int = 0,
) {

}

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
    Idle,
    Running,
    GameOver
}

