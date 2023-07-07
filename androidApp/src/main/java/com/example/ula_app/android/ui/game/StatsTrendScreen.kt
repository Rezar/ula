package com.example.ula_app.android.ui.game

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.max
import kotlin.math.min

data class LineChart(
    val width: Dp,
    val height: Dp,
    val aspectRatio: Float,
    val leftPadding: Float,
    val rightPadding: Float,
    val topPadding: Float,
    val bottomPadding: Float,
    val maxValue: Float
)

data class Line(
    val thickness: Float,
    val color: Color,
    val alpha: Float
)

data class LevelLine(
    val thickness: Float,
    val color: Color
)

data class LineNode(
    val radius: Float,
    val color: Color,
    val alpha: Float
)

data class Axis(
    val thickness: Float = 6f,
    val color: Color
)

data class Config(
    val lineChart: LineChart,
    val line: Line,
    val levelLine: LevelLine,
    val lineNode: LineNode,
    val xAxis: Axis,
    val yAxis: Axis
)

// LineChart configuration.
val Default: Config = Config(
    lineChart = LineChart(
        width = 0.dp,
        height = 0.dp,
        aspectRatio = 4/3f,
        leftPadding = 0f,
        rightPadding = 0f,
        topPadding = 0f,
        bottomPadding = 0f,
        // If current step >= maxValue, it will shrink currentStep to maxValue and draw it on the plot.
        maxValue = 5500f
    ),
    // Line.
    line = Line(
        thickness = 10f,
        color = Color(0xFFFFB52E),
        alpha = 0.2f
    ),
    levelLine = LevelLine(
        thickness = 4f,
        color = Color.Black
    ),
    // Node on the line.
    lineNode = LineNode(
        radius = 14f,
        color = Color(0xFFFFB52E),
        alpha = 1f
    ),
    xAxis = Axis(
        thickness = 10f,
        color = Color.Black
    ),
    yAxis = Axis(
        color = Color.Black
    )
)

// Data of dates and footsteps.
data class StepData(
    val dates: String,
    val steps: Float
)

// Level.
data class Level(
    val level: Float,
    // Left padding of the level text.
    val leftPadding: Float
)

@OptIn(ExperimentalTextApi::class)
@Composable
fun StatsTrendScreen(
    onBackClicked: () -> Unit = {}
) {

    // Example Data of steps with dates.
    val exampleData = listOf<StepData>(
        StepData(
            dates = "11.11",
            steps = 1000f
        ),
        StepData(
            dates = "11.12",
            steps = 2000f
        ),
        StepData(
            dates = "11.13",
            steps = 3000f
        ),
        StepData(
            dates = "11.14",
            steps = 40000f
        ),
        StepData(
            dates = "11.15",
            steps = 3000f
        ),
    )

    // Level in the LineChart.
    val levels: List<Level> = listOf(
        Level(
            level = 0f,
            leftPadding = 84f
        ),
        Level(
            level = 1250f,
            leftPadding = 0f
        ),
        Level(
            level = 2500f,
            leftPadding = 0f
        ),
        Level(
            level = 3750f,
            leftPadding = 0f
        ),
        Level(
            level = 5000f,
            leftPadding = 0f
        )
    )

    Column(
        modifier = Modifier
            .padding(20.dp)
            .aspectRatio(Default.lineChart.aspectRatio)
    ) {
        val textMeasurer = rememberTextMeasurer()
        // Left, right, top, bottom padding of the LineChart.
        val leftPadding = 160f
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
            /*// x axis
            drawLine(
                color = Color.Black,
                strokeWidth = 5f,
                start = Offset(x = padding, y = size.height - padding),
                end = Offset(x = size.width - padding, y = size.height - padding)
            )
            // y axis
            drawLine(
                color = Color.Black,
                strokeWidth = 5f,
                start = Offset(x = padding, y = size.height - padding),
                end = Offset(x = padding, y = padding)
            )
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

            }*/


            exampleData.forEachIndexed { index, item ->
                // Draw dates under the x axis.
                drawText(
                    textMeasurer,
                    item.dates,
                    Offset(
                        x = leftPadding + (size.width - rightPadding - 50f) / exampleData.size * index,
                        y = size.height - 60f
                    ),
                    style = TextStyle(
                        color = Default.xAxis.color
                    )
                )
            }

            levels.forEachIndexed { index, item ->
                // Draw scales on y axis.
                drawText(
                    textMeasurer,
                    item.level.toString(),
                    Offset(
                        x = item.leftPadding,
                        y = size.height - bottomPadding - (size.height - bottomPadding) / levels.size * index)
                    )
                // Draw x axis and lines for different scales on y axis.
                drawLine(
                    color = Default.xAxis.color,
                    strokeWidth = if (index == 0) Default.xAxis.thickness else Default.levelLine.thickness,
                    start = Offset(
                        x = leftPadding,
                        y = size.height - bottomPadding + 35f - (size.height - bottomPadding) / levels.size * index
                    ),
                    end = Offset(
                        x = size.width - rightPadding,
                        y = size.height - bottomPadding + 35f - (size.height - bottomPadding) / levels.size * index
                    ),
                    alpha = if (index == 0) 1f else 0.2f,
                    cap = StrokeCap.Butt
                )
            }

            val path = Path()
            exampleData.forEachIndexed{ index, item ->

                if (index == 0) {
                    path.moveTo(
                        x = 50f + leftPadding + (size.width - rightPadding - 50f) / exampleData.size * index,
                        y = size.height - bottomPadding + 35f - min(Default.lineChart.maxValue, item.steps) / 5000 * (size.height - bottomPadding - (size.height - bottomPadding) / levels.size)
                    )
                } else {
                    path.lineTo(
                        x = 50f + leftPadding + (size.width - rightPadding - 50f) / exampleData.size * index,
                        y = size.height - bottomPadding + 35f - min(Default.lineChart.maxValue, item.steps) / 5000 * (size.height - bottomPadding - (size.height - bottomPadding) / levels.size)
                    )
                }
            }

            drawPath(
                path = path,
                color = Default.line.color,
                style = Stroke(
                    width = Default.line.thickness
                ),
                alpha = Default.line.alpha
            )

            exampleData.forEachIndexed { index, item ->
                Log.i("StatsTrendScreen", "x: ${50f + leftPadding + (size.width - rightPadding) / exampleData.size * index}, y: ${size.height - bottomPadding + 35f - item.steps / 5000 * (size.height - bottomPadding - (size.height - bottomPadding) / levels.size)}")

                drawCircle(
                    color = Default.lineNode.color,
                    radius = Default.lineNode.radius,
                    center = Offset(
                        x = 50f + leftPadding + (size.width - rightPadding - 50f) / exampleData.size * index,
                        y = size.height - bottomPadding + 35f - min(Default.lineChart.maxValue, item.steps) / 5000 * (size.height - bottomPadding - (size.height - bottomPadding) / levels.size)
                    ),
                    alpha = Default.lineNode.alpha
                )
            }


        }
    }
}

@Preview
@Composable
fun prevStatsTrendScreen() {
    StatsTrendScreen()
}