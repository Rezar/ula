package com.example.ula_app.android.ui.welcome

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.SliderDefaults
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ula_app.android.R
import com.example.ula_app.android.component.IconButton

@Composable
fun WelcomePage3(
    onPreviousButtonClicked: () -> Unit,
    onNextButtonClicked: (Int) -> Unit
) {
    val nextButtonIcon = painterResource(id = R.mipmap.next)
    val previousButtonIcon = painterResource(id = R.mipmap.back)
    var selectedGoal = remember {
        mutableStateOf(0)
    }

    var selectedGoalWeekly = remember {
        mutableStateOf(0)
    }

    var tabIndex by remember { mutableStateOf(0)}
    val tabTitles = listOf("Daily Goal", "Weekly Goal")

    var sliderValueDaily by remember {
        mutableStateOf(15000f)
    }

    var sliderValueWeekly by remember {
        mutableStateOf(50000f)
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.padding(
                start = Default.padding.start,
                end = Default.padding.end,
                top = Default.padding.top,
                bottom = Default.padding.bottom
            )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "FITNESS PLAN",
                    style = MaterialTheme.typography.h1
                )
            }
            Spacer(modifier = Modifier.height(60.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "Tell ULA the number of steps you intend to take. No, No, No you can not enter anything less than 5,000 steps >:)"
                )
            }
            Spacer(modifier = Modifier.height(40.dp))

            // tab row
            Column(
                modifier = Modifier
                    .padding(20.dp)
            ) {
                TabRow(
                    selectedTabIndex = tabIndex,
                    backgroundColor = Color.White,
                    contentColor = Color.Black
                ) {
                    tabTitles.forEachIndexed {index, title ->
                        Tab(
                            selected = tabIndex == index,
                            onClick = {
                                tabIndex = index
                            },
                            text = {
                                Text(
                                    text = title,
                                    fontWeight = FontWeight.Bold,
                                    style = MaterialTheme.typography.body2
                                )
                            }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))

                when (tabIndex) {
                    0 -> {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Slider(
                                value = sliderValueDaily,
                                onValueChange = { sliderValue_ ->
                                    sliderValueDaily = sliderValue_
                                },
                                onValueChangeFinished = {
                                    // this is called when the user completed selecting the value
                                    selectedGoal.value = sliderValueDaily.toInt()
                                    Log.d("MainActivity", "sliderValue = $sliderValueDaily")
                                },
                                valueRange = 5000f..30000f,
                                steps = 4,
                                colors = SliderDefaults.colors(
                                    thumbColor = Color(0xFFEF5366),
                                    activeTrackColor = Color(0xFF80EF5366)
                                )
                            )

                            Text(text = sliderValueDaily.toInt().toString())

                        }
                    }
                    1 -> {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Slider(
                                value = sliderValueWeekly,
                                onValueChange = { sliderValue_ ->
                                    sliderValueWeekly = sliderValue_
                                },
                                onValueChangeFinished = {
                                    // this is called when the user completed selecting the value
                                    selectedGoalWeekly.value = sliderValueWeekly.toInt()
                                    Log.d("MainActivity", "sliderValue = $sliderValueWeekly")
                                },
                                valueRange = 20000f..100000f,
                                steps = 7,
                                colors = SliderDefaults.colors(
                                    thumbColor = Color(0xFFEF5366),
                                    activeTrackColor = Color(0xFF80EF5366)
                                )
                            )

                            Text(text = sliderValueWeekly.toInt().toString())

                        }
                    }
                }
            }



            /*Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                stepOptions.forEach {
                    Row (
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = it.toInt() == selectedGoal.value,
                            onClick = {
                                selectedGoal.value = it.toInt()
                                Log.i("WelcomePage_Page3", "Plan_${selectedGoal.value}")
                            }
                        )
                        Text(
                            text = "${it} steps"
                        )
                    }
                }
            }*/
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                Icon = {
                    Icon(
                        painter = previousButtonIcon,
                        contentDescription = "Previous"
                    )
                },
                onClick = {
                    onPreviousButtonClicked()
                }
            )
            IconButton(
                enabled = selectedGoal.value !== 0,
                Icon = {
                    Icon(
                        painter = nextButtonIcon,
                        contentDescription = "Next"
                    )
                },
                onClick = {
                    onNextButtonClicked(selectedGoal.value)
                }
            )
        }
    }
}