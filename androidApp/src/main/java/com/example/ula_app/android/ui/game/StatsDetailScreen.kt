package com.example.ula_app.android.ui.game

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ula_app.android.R
import com.example.ula_app.android.data.DataSource
import com.example.ula_app.android.ui.lockgame.tictactoe.Default

@OptIn(ExperimentalTextApi::class)
@Composable
fun StatsDetailScreen() {

}

@Composable
fun StatsDetailItem(
    weekday: String = "Monday",
    currentSteps: Int = 5000,
    bodyStatus: DataSource.MonsterBodyStatusOptions = DataSource.MonsterBodyStatusOptions.Fat
) {
    val m1 = painterResource(id = R.drawable.stickman_draft_bed)
    val m2 = painterResource(id = R.drawable.stickman_draft_1)
    val m3 = painterResource(id = R.drawable.stickman_draft_2)
    val m4 = painterResource(id = R.drawable.stickman_draft_3)
    val m5 = painterResource(id = R.drawable.stickman_draft_4)

    val imageList: List<Image>

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "${weekday}")
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = "${currentSteps} steps")
        }
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Body status: ${bodyStatus.name}")
        }
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {

        }
    }
}

@Preview
@Composable
fun prevStatsDetailScreen() {
    StatsDetailItem()
}