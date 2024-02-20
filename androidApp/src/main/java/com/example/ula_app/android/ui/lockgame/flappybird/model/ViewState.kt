package com.example.ula_app.android.ui.lockgame.flappybird.model

data class ViewState(
    val roadStateList: List<RoadState> = emptyList(),
    val pipeStateList: List<PipeState> = emptyList(),
    val birdState: BirdState = BirdState(),
    val safeZone: SafeZone = SafeZone(),
    val gameStatus: GameStatus = GameStatus.Ide,
    val isTapping: Boolean = false,
    val score: Int = 0,
    val bestScore: Int = 0
) {
    companion object {
        val DefaultGameSetting: ViewState = ViewState(

        )
    }
}

data class SafeZone(
    var width: Float = 0f,
    var height: Float = 0f
) {
    fun initiated(): Boolean {
        return width != 0f || height != 0f
    }
}

// A data structure represents the top, bottom, left, and right bound the object.
data class ObjectEdge(
    var top: Float = 0f,
    var bottom: Float = 0f,
    var left: Float = 0f,
    var right: Float = 0f
)

/**
 * A enum class represents the status of the game.
 *
 * Ide: Status before user play the game
 * Running: Status when user is playing the game.
 * GameOver: Status when bird died in the game.
 *
 *                                        tap the screen            keep scoring
 * user first time enter the game -> Ide ----------------> Running -------------->
 *                                    ^                        |
 *                                    |                        | bird died in the game.
 *                                    |                        |
 *                                    | press restart button   v      press exit button
 *                                    -------------------- GameOver -------------------> Exit the game
 * */
enum class GameStatus {
    Ide,
    Running,
    GameOver
}


