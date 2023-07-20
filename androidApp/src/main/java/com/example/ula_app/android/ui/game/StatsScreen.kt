package com.example.ula_app.android.ui.game

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ula_app.android.ui.viewmodel.GoalViewModel
import com.example.ula_app.android.ui.viewmodel.StepViewModel
import com.mutualmobile.composesensors.rememberStepCounterSensorState
import com.example.ula_app.android.ui.viewmodel.UserPreferencesViewModel

private const val TAG = "StatsScreen"

enum class StatsScreenSegment() {
    StatsDetail,
//    StatsTrend
}

@Composable
fun StatsScreen(
    stepViewModel: StepViewModel = viewModel(),
    userPreferencesViewModel: UserPreferencesViewModel = viewModel()
) {

    // stepHistory list from datastore or state
//    val stepHistoryUiState by stepViewModel.userState.collectAsState()
//    val userPreUiState by userPreferencesViewModel.userPreferencesState.collectAsState()

    var currentScreen by remember {
        mutableStateOf("")
    }

    val stepSensor = rememberStepCounterSensorState()

    when (currentScreen) {
        StatsScreenSegment.StatsDetail.name -> {
            StatsDetailScreen(
                onBackClicked = {
                    currentScreen = ""
                },
                stepViewModel = stepViewModel,
                userPreferencesViewModel = userPreferencesViewModel
            )
        }
/*        StatsScreenSegment.StatsTrend.name -> {
            StepChart(
                onBackClicked = {
                    currentScreen = ""
                },
                stepViewModel = stepViewModel,
                goalViewModel = goalViewModel
            )
        }*/
        else -> {

            Column(
                modifier = Modifier.padding(20.dp)
            ) {
                Text(
                    text = "Summary",
                    modifier = Modifier,
                    style = MaterialTheme.typography.h1
                )

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp)
                        .clickable {
                            currentScreen = StatsScreenSegment.StatsDetail.name
                        },
                    elevation = 10.dp
                ) {

                    Column() {
                        Row(
                            modifier = Modifier
                                .padding(15.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Steps",
                                style = MaterialTheme.typography.h4
                            )

                            Text(
                                text = "Fitness Detail >",
                                style = MaterialTheme.typography.h4
                            )
                        }

                        Row(
                            modifier = Modifier
                                .padding(15.dp)
                                .fillMaxWidth(),
//                                .height(40.dp),
                            verticalAlignment = Alignment.Bottom,
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Text(
//                                text = "${goalUiState.steps}", //TODO: change and link it to the sensor to show the current steps
                                text = "${stepSensor.stepCount}", // read current step from sensor.
                                fontSize = 30.sp
                            )
                            Column(
                                verticalArrangement = Arrangement.Bottom,
//                                modifier = Modifier.fillMaxHeight()
                            ){
                                Text(
                                    text = "steps",
                                    color = Color.Gray,
                                    style = MaterialTheme.typography.h4
                                )
                            }
                        }

                    }

                }

                StepChart(stepViewModel, userPreferencesViewModel)

            }



/*            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "This is $TAG", fontSize = 18.sp)
                Button(
                    onClick = {
                        currentScreen = StatsScreenSegment.StatsDetail.name
                    }
                ) {
                    Text(text = "to Detail")
                }
                Button(
                    onClick = {
                        currentScreen = StatsScreenSegment.StatsTrend.name
                    }
                ) {
                    Text(text = "to Trend")
                }
            }*/
        }
    }
}