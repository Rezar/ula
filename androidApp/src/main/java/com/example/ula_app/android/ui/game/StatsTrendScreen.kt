package com.example.ula_app.android.ui.game

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalTextApi::class)
@Composable
fun StatsTrendScreen(
    onBackClicked: () -> Unit = {}
) {
    val steps: List<String> = listOf(
        "0",
        "1250",
        "2500",
        "3750",
        "5000"
    )

    val dates: List<String> = listOf(
        "11.11",
        "11.12",
        "11.13",
        "11.14",
        "11.15"
    )

    val dots: List<Int> = listOf(
        1000,
        2000,
        3000,
        4000,
        5000
    )

    Column(
        modifier = Modifier
            .padding(20.dp)
    ) {
        val textMeasurer = rememberTextMeasurer()
        val padding: Float = 100f
        val leftPadding = 120f
        val rightPadding = 60f
        val topPadding = 0f
        val bottomPadding = 100f

        BackHandler(true) {
            onBackClicked()
        }
        IconButton(
            onClick = {
                onBackClicked()
            }
        ) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = null
            )
        }
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {
            // x axis
//            drawLine(
//                color = Color.Black,
//                strokeWidth = 5f,
//                start = Offset(x = padding, y = size.height - padding),
//                end = Offset(x = size.width - padding, y = size.height - padding)
//            )
            // y axis
//            drawLine(
//                color = Color.Black,
//                strokeWidth = 5f,
//                start = Offset(x = padding, y = size.height - padding),
//                end = Offset(x = padding, y = padding)
//            )
            drawIntoCanvas { canvas ->
                val paint = Paint()
                paint.style = PaintingStyle.Fill
                paint.color = Color.Green

                val textPaintXAxis = android.graphics.Paint()
                textPaintXAxis.style = PaintingStyle.Fill
                textPaintXAxis.color = Color.Black


                val coordinatePaint = Paint()
                coordinatePaint.strokeWidth = 5f
                coordinatePaint.style = PaintingStyle.Fill
                coordinatePaint.color = Color.Black

                canvas.translate(0f, size.height)
                canvas.scale(1f, -1f)
                canvas.drawCircle(Offset(100f, 100f), 100f, paint)

                canvas.drawLine(Offset(x = bottomPadding, y = leftPadding), Offset(x = bottomPadding, y = size.height - rightPadding), coordinatePaint)
                canvas.drawLine(Offset(x = bottomPadding, y = leftPadding), Offset(x = size.width - topPadding, y = leftPadding), coordinatePaint)

                dates.forEachIndexed { index, item ->
                    canvas.nativeCanvas.drawText(item, bottomPadding, leftPadding + index.toFloat() / dates.size * (size.height - rightPadding), textPaintXAxis)
                }

            }


            /*dates.forEachIndexed { index, item ->
                // Draw dates under the x axis.
                drawText(textMeasurer, item, Offset(x = leftPadding + (size.width - rightPadding).toFloat() / dates.size * index, y = 60f))
            }*/

            /*steps.forEachIndexed { index, item ->
                // Draw scales on y axis.
                drawText(textMeasurer, item, Offset(x = 0f, y = size.height - bottomPadding - (size.height - bottomPadding).toFloat() / steps.size * index))
                // Draw x axis and lines for different scales on y axis.
                drawLine(
                    color = Color.Black,
                    strokeWidth = 5f,
                    start = Offset(x = leftPadding, y = size.height - bottomPadding + 35f - (size.height - bottomPadding).toFloat() / steps.size * index),
                    end = Offset(x = size.width - rightPadding, y = size.height - bottomPadding + 35f - (size.height - bottomPadding).toFloat() / steps.size * index),
                    alpha = if (index == 0) 1f else 0.2f,
                    cap = StrokeCap.Butt
                )
            }

            dots.forEachIndexed{ index, item ->
                drawCircle(
                    color = Color.Green,
                    radius = 5f,
                    center = Offset(x = leftPadding + (size.width - rightPadding).toFloat() / dates.size * index, y = size.height - bottomPadding - item.toFloat() / 5000 * (size.height - bottomPadding))

                )
                Log.i("StatsTrendScreen", "${item.toFloat() / 5000 * (size.height - bottomPadding)}")
            }*/


            /*dates.forEachIndexed { index, item ->
                // Draw dates under the x axis.
                drawText(textMeasurer, item, Offset(x = leftPadding + (size.width - rightPadding).toFloat() / dates.size * index, y = size.height - 60f))
            }

            steps.forEachIndexed { index, item ->
                // Draw scales on y axis.
                drawText(textMeasurer, item, Offset(x = 0f, y = size.height - bottomPadding - (size.height - bottomPadding).toFloat() / steps.size * index))
                // Draw x axis and lines for different scales on y axis.
                drawLine(
                    color = Color.Black,
                    strokeWidth = 5f,
                    start = Offset(x = leftPadding, y = size.height - bottomPadding + 35f - (size.height - bottomPadding).toFloat() / steps.size * index),
                    end = Offset(x = size.width - rightPadding, y = size.height - bottomPadding + 35f - (size.height - bottomPadding).toFloat() / steps.size * index),
                    alpha = if (index == 0) 1f else 0.2f,
                    cap = StrokeCap.Butt
                )
            }

            dots.forEachIndexed{ index, item ->
                drawCircle(
                    color = Color.Green,
                    radius = 5f,
                    center = Offset(x = leftPadding + (size.width - rightPadding).toFloat() / dates.size * index, y = size.height - bottomPadding - item.toFloat() / 5000 * (size.height - bottomPadding))

                )
                Log.i("StatsTrendScreen", "${item.toFloat() / 5000 * (size.height - bottomPadding)}")
            }*/
        }
    }
}