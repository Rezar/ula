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
    board = Board(
        size = 300.dp,
        padding = 10.dp,
        backgroundColor = Color.White
    ),
    cell = Cell(
        size = 60.dp,
        padding = 0.dp,
        backgroundColor = Color.White
    ),
    boardLine = BoardLine(
        thickness = 5f,
        color = Color.Gray
    ),
    winLine = WinLine(
        thickness = 10f,
        color = Color.Red
    ),
    label = Label(
        circle = Circle(
            size = 60.dp,
            padding = 5.dp,
            color = Color.Green,
            thickness = 20f
        ),
        cross = Cross(
            size = 60.dp,
            padding = 5.dp,
            color = Color.Yellow,
            thickness = 20f
        )
    )
)