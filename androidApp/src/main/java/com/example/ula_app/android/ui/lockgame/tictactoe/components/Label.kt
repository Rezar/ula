package com.example.ula_app.android.ui.lockgame.tictactoe.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.example.ula_app.android.ui.lockgame.tictactoe.Default

@Composable
fun Circle() {
    Canvas(
        modifier = Modifier
            .size(Default.label.circle.size)
            .padding(Default.label.circle.padding)
    ) {
        drawCircle(
            color = Default.label.circle.color,
            style = Stroke(width = Default.label.circle.thickness)
        )
    }
}

@Composable
fun Cross() {
    Canvas(
        modifier = Modifier
            .size(Default.label.cross.size)
            .padding(Default.label.cross.padding)
    ) {
        drawLine(
            color = Default.label.cross.color,
            strokeWidth = Default.label.cross.thickness,
            cap = StrokeCap.Round,
            start = Offset(x = 0f, y = 0f),
            end = Offset(x = size.width, y = size.height)
        )
        drawLine(
            color = Default.label.cross.color,
            strokeWidth = Default.label.cross.thickness,
            cap = StrokeCap.Round,
            start = Offset(x = 0f, y = size.height),
            end = Offset(x = size.width, y = 0f)
        )
    }
}