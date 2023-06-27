package com.example.ula_app.android.ui.lockgame.tictactoe

data class TicTacToeState(
    val circleWinCount: Int = 0,
    val crossWinCount: Int = 0,
    val drawCount: Int = 0,
    val hintText: String = "Player 'O' turn",
    val currentRole: CellValue = CellValue.Circle,
    val gameResult: GameResult = GameResult.None
)

enum class CellValue{
    Circle,
    Cross,
    None
}

enum class GameResult{
    Vertical1,
    Vertical2,
    Vertical3,
    Horizontal1,
    Horizontal2,
    Horizontal3,
    Diagonal1,
    Diagonal2,
    Draw,
    None
}