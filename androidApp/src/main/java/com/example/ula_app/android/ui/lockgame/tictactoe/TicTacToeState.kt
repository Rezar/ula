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
    // Hint text when player take turns.
    role = RoleHintText(
        circle = "Player 'O' turn",
        cross = "Player 'X' turn"
    ),
    // hint text when one of the players has won.
    win = WinHintText(
        circle = "Player 'O' win",
        cross = "Player 'X' win"
    )
)

// State
data class TicTacToeState(
    // How many times O has won.
    val circleWinCount: Int = 0,
    // How many times X has won.
    val crossWinCount: Int = 0,
    // How many times draw.
    val drawCount: Int = 0,
    // Hint text.
    val hintText: String = TicTacToeHintText.role.circle,
    // Current role: O or X, initially set to O.
    val currentRole: CellValue = CellValue.Circle,
    // The result of the game, initially set to None.
    val gameResult: GameResult = GameResult.None
)

// Value in the board cell.
enum class CellValue{
    Circle,
    Cross,
    None
}

// The result of the game.
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