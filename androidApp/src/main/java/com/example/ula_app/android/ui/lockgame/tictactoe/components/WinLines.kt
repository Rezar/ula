package com.example.ula_app.android.ui.lockgame.tictactoe.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ula_app.android.ui.lockgame.tictactoe.Default

@Composable
fun VerticalWinLine1() {
    Canvas(modifier = Modifier.size(Default.board.size)) {
        drawLine(
            color = Default.winLine.color,
            strokeWidth = Default.winLine.thickness,
            cap = StrokeCap.Round,
            start = Offset(x = size.width*1/6, y = 0f),
            end = Offset(x = size.width*1/6, y = size.height)
        )
    }
}

@Composable
fun VerticalWinLine2() {
    Canvas(modifier = Modifier.size(Default.board.size)) {
        drawLine(
            color = Default.winLine.color,
            strokeWidth = Default.winLine.thickness,
            cap = StrokeCap.Round,
            start = Offset(x = size.width*3/6, y = 0f),
            end = Offset(x = size.width*3/6, y = size.height)
        )
    }
}

@Composable
fun VerticalWinLine3() {
    Canvas(modifier = Modifier.size(Default.board.size)) {
        drawLine(
            color = Default.winLine.color,
            strokeWidth = Default.winLine.thickness,
            cap = StrokeCap.Round,
            start = Offset(x = size.width*5/6, y = 0f),
            end = Offset(x = size.width*5/6, y = size.height)
        )
    }
}

@Composable
fun HorizontalWinLine1() {
    Canvas(modifier = Modifier.size(Default.board.size)) {
        drawLine(
            color = Default.winLine.color,
            strokeWidth = Default.winLine.thickness,
            cap = StrokeCap.Round,
            start = Offset(x = 0f, y = size.height*1/6),
            end = Offset(x = size.width, y = size.height*1/6)
        )
    }
}

@Composable
fun HorizontalWinLine2() {
    Canvas(modifier = Modifier.size(Default.board.size)) {
        drawLine(
            color = Default.winLine.color,
            strokeWidth = Default.winLine.thickness,
            cap = StrokeCap.Round,
            start = Offset(x = 0f, y = size.height*3/6),
            end = Offset(x = size.width, y = size.height*3/6)
        )
    }
}

@Composable
fun HorizontalWinLine3() {
    Canvas(modifier = Modifier.size(Default.board.size)) {
        drawLine(
            color = Default.winLine.color,
            strokeWidth = Default.winLine.thickness,
            cap = StrokeCap.Round,
            start = Offset(x = 0f, y = size.height*5/6),
            end = Offset(x = size.width, y = size.height*5/6)
        )
    }
}

@Composable
fun DiagonalWinLine1() {
    Canvas(modifier = Modifier.size(Default.board.size)) {
        drawLine(
            color = Default.winLine.color,
            strokeWidth = Default.winLine.thickness,
            cap = StrokeCap.Round,
            start = Offset(x = 0f, y = 0f),
            end = Offset(x = size.width, y = size.height)
        )
    }
}

@Composable
fun DiagonalWinLine2() {
    Canvas(modifier = Modifier.size(Default.board.size)) {
        drawLine(
            color = Default.winLine.color,
            strokeWidth = Default.winLine.thickness,
            cap = StrokeCap.Round,
            start = Offset(x = 0f, y = size.height),
            end = Offset(x = size.width, y = 0f)
        )
    }
}

@Preview
@Composable
fun Preview() {
    VerticalWinLine1()
    VerticalWinLine2()
    VerticalWinLine3()
    HorizontalWinLine1()
    HorizontalWinLine2()
    HorizontalWinLine3()
    DiagonalWinLine1()
    DiagonalWinLine2()
}