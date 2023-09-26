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
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LinearProgressIndicator
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
import androidx.compose.ui.platform.LocalContext
import com.example.ula_app.android.Singleton
import com.example.ula_app.android.data.UserPreferences
import com.example.ula_app.android.ui.viewmodel.UserPreferencesViewModel
import com.example.ula_app.util.DateTimeUtil

@Composable
fun StatsDetailScreen(
    onBackClicked: () -> Unit = {}
) {
    val context = LocalContext.current
    val userPreferencesViewModel= Singleton.getInstance<UserPreferencesViewModel>()
    val stepViewModel= Singleton.getInstance<StepViewModel>()

    // stepHistory list from datastore or state
    val stepHistoryUiState by stepViewModel.userState.collectAsState()
    val userPreUiState by userPreferencesViewModel.userPreferencesState.collectAsState()

    val stepHistoryList = stepHistoryUiState.data

    Column(
        modifier = Modifier
            .padding(20.dp)
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
                    progressIndex = stepViewModel.getMonsterProgress(userPreUiState.dailyGoal, item.steps),
                    userPreUiState = userPreUiState
                )

                // create a linear progress bar
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(3.dp),
                    backgroundColor = Color.LightGray,
                    color = Color.Red,
                    progress =
                        if(item.steps <= userPreUiState.dailyGoal)
                                (item.steps/userPreUiState.dailyGoal.toFloat())
                        else 1.0f,

                )

                /*if (index < stepHistoryList.size) {
                    Divider(
                        color = Color.Black,
                        thickness = 0.5.dp
                    )
                }*/
            }
        }
    }
}

@Composable
fun StatsDetailItem(
    weekday: String = "",
    currentSteps: Int = 5000,
    progressIndex: Int = 0,
    userPreUiState: UserPreferences
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
                modifier = Modifier.width(140.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))

            // if the user choose to display steps in settings tab, then display the number.
            // Otherwise do not display the steps
            if (userPreUiState.displaySteps) {
                Text(text = "${currentSteps} steps")
            }
        }

        // compare with goal to get the monster's index
        MonsterProgressBar(selectedIndex = progressIndex, userPreUiState = userPreUiState)

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
    selectedIndex: Int = 0,
    userPreUiState: UserPreferences
) {

    // Stickman images.
    val stickman_1 = R.drawable.stickman_draft_bed
    val stickman_2 = R.drawable.stickman_draft_4
    val stickman_3 = R.drawable.stickman_draft_3
    val stickman_4 = R.drawable.stickman_draft_2
    val stickman_5 = R.drawable.stickman_draft_1
    val stickman_6 = R.drawable.stickman_draft_5

    val stickmanList = listOf(
        stickman_1,
        stickman_2,
        stickman_3,
        stickman_4,
        stickman_5,
        stickman_6
    )

    // Monster images.
    val monster_1 =R.drawable.stickman_draft_monster_5
    val monster_2 =R.drawable.stickman_draft_monster_4
    val monster_3 =R.drawable.stickman_draft_monster_1
    val monster_4 = R.drawable.stickman_draft_monster_3
    val monster_5 = R.drawable.stickman_draft_monster_2
    val monster_6 = R.drawable.stickman_draft_monster_6

    val monsterList = listOf(
        monster_1,
        monster_2,
        monster_3,
        monster_4,
        monster_5,
        monster_6
    )

    if(userPreUiState.displayMonster) {
        displayImage(selectedIndex = selectedIndex, imageList = monsterList)
    } else {
        displayImage(selectedIndex = selectedIndex, imageList = stickmanList)
    }

}

@Composable
fun displayImage(
    selectedIndex: Int = 0,
    imageList: List<Int>
){
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