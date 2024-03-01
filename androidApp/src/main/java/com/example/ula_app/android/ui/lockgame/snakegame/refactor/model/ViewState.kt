package com.example.ula_app.android.ui.lockgame.snakegame.refactor.model

data class ViewState(
    val foodState: Coordinate = Coordinate.randomCoordinate(listOf(Coordinate(16, 2), Coordinate(16, 1))),
    val snakeStateList: List<Coordinate> = listOf(Coordinate(16, 2), Coordinate(16, 1)),
    val snakeDirection: SnakeDirection = SnakeDirection.Down,
    val gameStatus: GameStatus = GameStatus.Idle,
    val score: Int = 0,
    val bestScore: Int = 0
) {

}


enum class GameStatus {
    Idle,
    Running,
    GameOver
}

enum class SnakeDirection {
    Up,
    Down,
    Left,
    Right
}

const val boardSize = 32