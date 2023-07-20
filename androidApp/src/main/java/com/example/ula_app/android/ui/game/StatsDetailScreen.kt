package com.example.ula_app.android.ui.game

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ula_app.android.R
import com.example.ula_app.android.ui.viewmodel.StepViewModel
import androidx.compose.runtime.getValue
import com.example.ula_app.android.ui.viewmodel.GoalViewModel
import com.example.ula_app.android.ui.viewmodel.StepsWithDates
import com.example.ula_app.android.util.DateTimeUtil
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant

@Composable
fun StatsDetailScreen(
    onBackClicked: () -> Unit = {},
    stepViewModel: StepViewModel = viewModel(),
    goalViewModel: GoalViewModel = viewModel()
) {

    // stepHistory list from datastore or state
    val stepHistoryUiState by stepViewModel.userState.collectAsState()
    val goalUiState by goalViewModel.uiState.collectAsState()

    Log.i("StatsDetailScreen", "goalSteps: ${goalUiState.steps}")

    val stepHistoryList = stepHistoryUiState.data

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {
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
        Text(
            text = "Stats Detail",
            fontSize = 40.sp
        )
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            itemsIndexed(stepHistoryList) { index, item ->
                StatsDetailItem(
                    weekday = DateTimeUtil.getDayOfWeek(item.date),
                    currentSteps = item.steps,
                    progressIndex = stepViewModel.getMonsterProgress(goalUiState.steps, item.steps)
                )
                if (index < stepHistoryList.size) {
                    Divider(
                        color = Color.Black,
                        thickness = 0.5.dp
                    )
                }
            }
        }
    }
}

@Composable
fun StatsDetailItem(
    weekday: String = "",
    currentSteps: Int = 5000,
    progressIndex: Int = 0
) {


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(10.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "${weekday}",
                modifier = Modifier.width(120.dp))
            Spacer(modifier = Modifier.width(16.dp))

            // TODO: make it invisible if the user set it in the settings tab ----------------------------------------------------
            Text(text = "${currentSteps} steps")
        }

        // compare with goal to get the monster's index
        MonsterProgressBar(selectedIndex = progressIndex)

    }
}

/*
* Monster Progress Bar.
* param:
*       selectedIndex: The index of monster and monster images in the imageList. The selected image is the current
*                      body status.
* */
@Composable
fun MonsterProgressBar(
    selectedIndex: Int = 0
) {

    /*
    * TODO: Add the monster progress images when building the settings tab. -------------------------------------------------------
    * */

    // Stickman images.
    val stickman_1 = R.drawable.stickman_draft_bed
    val stickman_2 = R.drawable.stickman_draft_1
    val stickman_3 = R.drawable.stickman_draft_2
    val stickman_4 = R.drawable.stickman_draft_3
    val stickman_5 = R.drawable.stickman_draft_4
    val stickman_6 = R.drawable.stickman_draft_5

    val imageList = listOf(
        stickman_1,
        stickman_2,
        stickman_3,
        stickman_4,
        stickman_5,
        stickman_6
    )

    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        imageList.forEachIndexed{ index, item ->
            val painter = painterResource(id = item)
            Image(
                painter = painter,
                contentDescription = null,
                alpha = if (index <= selectedIndex) 1f else 0.5f,
                modifier = Modifier.weight(painter.intrinsicSize.width / painter.intrinsicSize.height)
            )
        }
    }
}

@Preview
@Composable
fun prevStatsDetailScreen() {
    StatsDetailScreen()
}