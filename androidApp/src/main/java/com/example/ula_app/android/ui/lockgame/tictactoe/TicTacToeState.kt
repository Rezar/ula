package com.example.ula_app.android.ui.lockgame.tictactoe

data class RoleHintText(
    val circle: String,
    val cross: String
)

data class WinHintText(
    val circle: String,
    val cross: String
)

data class HintText(
    val role: RoleHintText,
    val win: WinHintText
)

val TicTacToeHintText: HintText = HintText(
    role = RoleHintText(
        circle = "Player 'O' turn",
        cross = "Player 'X' turn"
    ),
    win = WinHintText(
        circle = "Player 'O' win",
        cross = "Player 'X' win"
    )
)

data class TicTacToeState(
    val circleWinCount: Int = 0,
    val crossWinCount: Int = 0,
    val drawCount: Int = 0,
    val hintText: String = TicTacToeHintText.role.circle,
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