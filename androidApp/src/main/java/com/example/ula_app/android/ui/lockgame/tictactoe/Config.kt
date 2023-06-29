package com.example.ula_app.android.ui.lockgame.tictactoe

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Board(
    val size: Dp,
    val padding: Dp,
    val backgroundColor: Color
)

data class Cell(
    val size: Dp,
    val padding: Dp,
    val backgroundColor: Color
)

data class BoardLine(
    val thickness: Float,
    val color: Color
)

data class WinLine(
    val thickness: Float,
    val color: Color
)

data class Circle(
    val size: Dp,
    val padding: Dp,
    val color: Color,
    val thickness: Float
)

data class Cross(
    val size: Dp,
    val padding: Dp,
    val color: Color,
    val thickness: Float
)

data class Label(
    val circle: Circle,
    val cross: Cross
)

data class Config(
    val board: Board,
    val cell: Cell,
    val boardLine: BoardLine,
    val winLine: WinLine,
    val label: Label
)

val Default = Config(
    // Board
    board = Board(
        // Width and height of the board.
        size = 300.dp,
        // Padding of the board.
        padding = 10.dp,
        // Background color of the board.
        backgroundColor = Color.White
    ),
    // Cell
    cell = Cell(
        // Width and height of the cell.
        size = 60.dp,
        // Padding of the board.
        padding = 0.dp,
        // Background color of the board.
        backgroundColor = Color.White
    ),
    // Cross line of the board
    boardLine = BoardLine(
        // Thickness.
        thickness = 5f,
        // Color of the Cross line.
        color = Color.Gray
    ),
    winLine = WinLine(
        // Thickness.
        thickness = 10f,
        // Color of the Win line.
        color = Color.Red
    ),
    label = Label(
        // O
        circle = Circle(
            // Size of Circle.
            size = 60.dp,
            // Padding of Circle.
            padding = 5.dp,
            // Color of the Circle.
            color = Color.Green,
            // Thickness.
            thickness = 20f
        ),
        // X
        cross = Cross(
            // Size of Cross.
            size = 60.dp,
            // Padding of Cross.
            padding = 5.dp,
            // Color of Cross.
            color = Color.Yellow,
            // Thickness.
            thickness = 20f
        )
    )
)