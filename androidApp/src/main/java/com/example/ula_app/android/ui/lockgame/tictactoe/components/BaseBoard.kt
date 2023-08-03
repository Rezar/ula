package com.example.ula_app.android.ui.lockgame.tictactoe.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ula_app.android.ui.lockgame.tictactoe.Default

/*
* Cross lines in the TicTacToe Board.
* */
@Composable
fun BaseBoard() {
    Canvas(
        modifier = Modifier
            .width(Default.board.size)
            .aspectRatio(1f)
            .padding(Default.board.padding),
    ) {
        drawLine(
            color = Default.boardLine.color,
            strokeWidth = Default.boardLine.thickness,
            cap = StrokeCap.Round,
            start = Offset(x = size.width / 3, y = 0f),
            end = Offset(x = size.width / 3, y = size.height)
        )
        drawLine(
            color = Default.boardLine.color,
            strokeWidth = Default.boardLine.thickness,
            cap = StrokeCap.Round,
            start = Offset(x = size.width * 2 / 3, y = 0f),
            end = Offset(x = size.width * 2 / 3, y = size.height)
        )
        drawLine(
            color = Default.boardLine.color,
            strokeWidth = Default.boardLine.thickness,
            cap = StrokeCap.Round,
            start = Offset(x = 0f, y = size.height / 3),
            end = Offset(x = size.width, y = size.height / 3)
        )
        drawLine(
            color = Default.boardLine.color,
            strokeWidth = Default.boardLine.thickness,
            cap = StrokeCap.Round,
            start = Offset(x = 0f, y = size.height * 2 / 3),
            end = Offset(x = size.width, y = size.height * 2 / 3)
        )
    }
}

//@Preview
//@Composable
//fun Prev() {
//    BaseBoard()
//}