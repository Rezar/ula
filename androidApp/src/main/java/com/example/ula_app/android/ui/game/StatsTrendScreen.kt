package com.example.ula_app.android.ui.game

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalTextApi::class)
@Composable
fun StatsTrendScreen() {
    val dates: List<String> = listOf(
        "11.11",
        "11.12",
        "11.13",
        "11.14",
        "11.15"
    )

    Column(
        modifier = Modifier
            .padding(20.dp)
    ) {
        val textMeasurer = rememberTextMeasurer()
        val padding: Float = 100f

        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            drawLine(
                color = Color.Black,
                strokeWidth = 5f,
                start = Offset(x = padding, y = size.height - padding),
                end = Offset(x = size.width - padding, y = size.height - padding)
            )
            drawLine(
                color = Color.Black,
                strokeWidth = 5f,
                start = Offset(x = padding, y = size.height - padding),
                end = Offset(x = padding, y = padding)
            )
            dates.forEachIndexed { index, item ->
                drawText(textMeasurer, item, Offset(x = 100f + (size.width - padding) / dates.size * index, y = size.height - 50f))
            }
        }
    }
}