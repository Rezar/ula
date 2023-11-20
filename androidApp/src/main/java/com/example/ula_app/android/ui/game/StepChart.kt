package com.example.ula_app.android.ui.game

import android.graphics.Typeface
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ula_app.android.ULAApplication
import com.example.ula_app.android.extension.abbr
import com.example.ula_app.android.ui.viewmodel.StepViewModel
import com.example.ula_app.android.ui.viewmodel.UserPreferencesViewModel
import com.example.ula_app.android.data.StepsWithDate
import com.example.ula_app.util.DateTimeUtil
import com.patrykandpatrick.vico.compose.axis.horizontal.bottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.startAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.column.columnChart
import com.patrykandpatrick.vico.compose.component.lineComponent
import com.patrykandpatrick.vico.compose.component.overlayingComponent
import com.patrykandpatrick.vico.compose.component.shapeComponent
import com.patrykandpatrick.vico.compose.component.textComponent
import com.patrykandpatrick.vico.compose.dimensions.dimensionsOf
import com.patrykandpatrick.vico.compose.style.currentChartStyle
import com.patrykandpatrick.vico.core.DefaultDimens
import com.patrykandpatrick.vico.core.axis.AxisPosition
import com.patrykandpatrick.vico.core.axis.formatter.AxisValueFormatter
import com.patrykandpatrick.vico.core.axis.formatter.PercentageFormatAxisValueFormatter
import com.patrykandpatrick.vico.core.chart.decoration.ThresholdLine
import com.patrykandpatrick.vico.core.chart.insets.Insets
import com.patrykandpatrick.vico.core.component.marker.MarkerComponent
import com.patrykandpatrick.vico.core.component.shape.DashedShape
import com.patrykandpatrick.vico.core.component.shape.LineComponent
import com.patrykandpatrick.vico.core.component.shape.ShapeComponent
import com.patrykandpatrick.vico.core.component.shape.Shapes
import com.patrykandpatrick.vico.core.component.shape.cornered.Corner
import com.patrykandpatrick.vico.core.component.shape.cornered.MarkerCorneredShape
import com.patrykandpatrick.vico.core.context.MeasureContext
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer
import com.patrykandpatrick.vico.core.entry.FloatEntry
import com.patrykandpatrick.vico.core.entry.entryOf
import com.patrykandpatrick.vico.core.extension.copyColor
import com.patrykandpatrick.vico.core.extension.half
import com.patrykandpatrick.vico.core.marker.Marker
import com.patrykandpatrick.vico.core.chart.dimensions.HorizontalDimensions
import com.patrykandpatrick.vico.core.chart.layout.HorizontalLayout

private const val TAG = "StepChart"


@Composable
fun StepChart(
//    onBackClicked: () -> Unit = {},
){
    val context = LocalContext.current
    val userPreferencesViewModel= ULAApplication.getInstance<UserPreferencesViewModel>()
    val stepViewModel= ULAApplication.getInstance<StepViewModel>()

    // stepHistory list from datastore or state
    val stepHistoryUiState by stepViewModel.userState.collectAsState()
    val userPreUiState by userPreferencesViewModel.userPreferencesState.collectAsState()
    val stepHistoryList = stepViewModel.mockStepsHistoryIfNeeded(stepHistoryUiState.stepsHistory)

    val chartEntryModelProducer = ChartEntryModelProducer(getSteps(stepHistoryList))

    // line components
    val COLUMN_WIDTH_DP = 16f
    // axis definition
    val START_AXIS_LABEL_COUNT = 6
    val startAxisValueFormatter = PercentageFormatAxisValueFormatter<AxisPosition.Vertical.Start>()

    val BOTTOM_AXIS_LABEL_SPACING = 3
    val BOTTOM_AXIS_LABEL_OFFSET = 1
    val bottomAxisValue = getDays(stepHistoryList)
    val horizontalLayout = HorizontalLayout.FullWidth(
        startPaddingDp = DefaultDimens.COLUMN_OUTSIDE_SPACING.half,
        endPaddingDp = DefaultDimens.COLUMN_OUTSIDE_SPACING.half,
    )

    val thresholdLine = rememberThresholdLine(userPreUiState.dailyGoal)
    val defaultColumns = currentChartStyle.columnChart.columns


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White)
            .padding(15.dp)
    ) {

        Chart(
            chart = columnChart(
                columns = remember(defaultColumns) {
                    defaultColumns.map {defaultColumn ->
                        LineComponent(defaultColumn.color, COLUMN_WIDTH_DP, defaultColumn.shape)
                    }
                },
                decorations = remember(thresholdLine) {listOf(thresholdLine)}
            ),
            chartModelProducer = chartEntryModelProducer,
            startAxis = startAxis(valueFormatter = startAxisValueFormatter, maxLabelCount = START_AXIS_LABEL_COUNT),
            bottomAxis = bottomAxis(valueFormatter = bottomAxisValue),
//        bottomAxis = bottomAxis(labelSpacing = BOTTOM_AXIS_LABEL_SPACING, labelOffset = BOTTOM_AXIS_LABEL_OFFSET),
            marker = rememberMarker(),
            horizontalLayout = horizontalLayout
        )
    }

}



fun getSteps(stepHistory: List<StepsWithDate>): ArrayList<FloatEntry>{
    val steps = ArrayList<FloatEntry>()

    stepHistory.forEachIndexed { index, item ->
        steps += entryOf(index.toFloat(), item.steps.toFloat())
    }
    return steps
}

fun getDays(stepHistory: List<StepsWithDate>): AxisValueFormatter<AxisPosition.Horizontal.Bottom> {
    val datesString = mutableListOf<String>()
    stepHistory.forEach {
        datesString += DateTimeUtil.getDayOfWeek(it.date).abbr()
    }

    val dates = AxisValueFormatter<AxisPosition.Horizontal.Bottom> { x, _ -> datesString[x.toInt() % datesString.size] }
    return dates
}

@Composable
fun rememberThresholdLine(
    goalSteps: Int
): ThresholdLine{

    // threshold component definition
    val THRESHOLD_LINE_VALUE = goalSteps
    val thresholdLineLabelMarginValue = 4.dp
    val thresholdLineLabelHorizontalPaddingValue = 8.dp
    val thresholdLineLabelVerticalPaddingValue = 2.dp
    val thresholdLineThickness = 2.dp
    val thresholdLineLabelPadding =
        dimensionsOf(thresholdLineLabelHorizontalPaddingValue, thresholdLineLabelVerticalPaddingValue)
    val thresholdLineLabelMargins = dimensionsOf(thresholdLineLabelMarginValue)

    val color2 = Color(0xff757575)

    val line = shapeComponent(strokeWidth = thresholdLineThickness, strokeColor = color2)
    val label = textComponent(
        color = Color.Black,
        background = shapeComponent(Shapes.pillShape, color2),
        padding = thresholdLineLabelPadding,
        margins = thresholdLineLabelMargins,
        typeface = Typeface.MONOSPACE,
    )
    return remember(line, label) {
        ThresholdLine(thresholdValue = THRESHOLD_LINE_VALUE.toFloat(), lineComponent = line, labelComponent = label)
    }
}

@Composable
internal fun rememberMarker(): Marker {
    val labelBackgroundColor = MaterialTheme.colorScheme.surface
    val labelBackground = remember(labelBackgroundColor) {
        ShapeComponent(labelBackgroundShape, labelBackgroundColor.toArgb()).setShadow(
            radius = LABEL_BACKGROUND_SHADOW_RADIUS,
            dy = LABEL_BACKGROUND_SHADOW_DY,
            applyElevationOverlay = true,
        )
    }
    val label = textComponent(
        background = labelBackground,
        lineCount = LABEL_LINE_COUNT,
        padding = labelPadding,
        typeface = Typeface.MONOSPACE,
    )
    val indicatorInnerComponent = shapeComponent(Shapes.pillShape, MaterialTheme.colorScheme.surface)
    val indicatorCenterComponent = shapeComponent(Shapes.pillShape, Color.White)
    val indicatorOuterComponent = shapeComponent(Shapes.pillShape, Color.White)
    val indicator = overlayingComponent(
        outer = indicatorOuterComponent,
        inner = overlayingComponent(
            outer = indicatorCenterComponent,
            inner = indicatorInnerComponent,
            innerPaddingAll = indicatorInnerAndCenterComponentPaddingValue,
        ),
        innerPaddingAll = indicatorCenterAndOuterComponentPaddingValue,
    )
    val guideline = lineComponent(
        MaterialTheme.colorScheme.onSurface.copy(GUIDELINE_ALPHA),
        guidelineThickness,
        guidelineShape,
    )
    return remember(label, indicator, guideline) {
        object : MarkerComponent(label, indicator, guideline) {
            init {
                indicatorSizeDp = INDICATOR_SIZE_DP
                onApplyEntryColor = { entryColor ->
                    indicatorOuterComponent.color = entryColor.copyColor(INDICATOR_OUTER_COMPONENT_ALPHA)
                    with(indicatorCenterComponent) {
                        color = entryColor
                        setShadow(radius = INDICATOR_CENTER_COMPONENT_SHADOW_RADIUS, color = entryColor)
                    }
                }
            }

            override fun getInsets(
                context: MeasureContext,
                outInsets: Insets,
                horizontalDimensions: HorizontalDimensions,
            ) = with(context) {
                outInsets.top = label.getHeight(context) + labelBackgroundShape.tickSizeDp.pixels +
                        LABEL_BACKGROUND_SHADOW_RADIUS.pixels * SHADOW_RADIUS_MULTIPLIER -
                        LABEL_BACKGROUND_SHADOW_DY.pixels
            }
        }
    }
}

private const val LABEL_BACKGROUND_SHADOW_RADIUS = 4f
private const val LABEL_BACKGROUND_SHADOW_DY = 2f
private const val LABEL_LINE_COUNT = 1
private const val GUIDELINE_ALPHA = .2f
private const val INDICATOR_SIZE_DP = 36f
private const val INDICATOR_OUTER_COMPONENT_ALPHA = 32
private const val INDICATOR_CENTER_COMPONENT_SHADOW_RADIUS = 12f
private const val GUIDELINE_DASH_LENGTH_DP = 8f
private const val GUIDELINE_GAP_LENGTH_DP = 4f
private const val SHADOW_RADIUS_MULTIPLIER = 1.3f

private val labelBackgroundShape = MarkerCorneredShape(Corner.FullyRounded)
private val labelHorizontalPaddingValue = 8.dp
private val labelVerticalPaddingValue = 4.dp
private val labelPadding = dimensionsOf(labelHorizontalPaddingValue, labelVerticalPaddingValue)
private val indicatorInnerAndCenterComponentPaddingValue = 5.dp
private val indicatorCenterAndOuterComponentPaddingValue = 10.dp
private val guidelineThickness = 2.dp
private val guidelineShape = DashedShape(Shapes.pillShape, GUIDELINE_DASH_LENGTH_DP, GUIDELINE_GAP_LENGTH_DP)



@Preview
@Composable
fun prevStatsTrendScreen2() {
    StepChart()
}