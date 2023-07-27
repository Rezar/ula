package com.example.ula_app.android.ui.game

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ula_app.android.data.DataSource
import com.example.ula_app.android.ui.viewmodel.UserPreferencesViewModel
import com.example.ula_app.android.util.DateTimeUtil
import kotlin.math.max

private const val TAG = "SettingScreen"

enum class SettingAdvancedSegment(){
    SettingAdvanced,
}

@Composable
fun SettingScreen(
    userPreferencesViewModel: UserPreferencesViewModel = viewModel()
) {
    val userPreUiState by userPreferencesViewModel.userPreferencesState.collectAsState()

    var currentScreen by remember {
        mutableStateOf("")
    }

    var stepCountSwitchON by remember {
        mutableStateOf(userPreUiState.displaySteps)
    }

    var progressMonsterSwitchON by remember {
        mutableStateOf(userPreUiState.displayMonster)
    }



    var effectiveDays by remember {
        mutableStateOf(TextFieldValue(userPreUiState.effectiveDays.toString()))
    }

    var effectiveDate by remember {
        mutableStateOf(userPreUiState.effectiveDate)
    }

    var selectedGoal by remember {
//        mutableStateOf(DataSource.stepOptions[0])
        mutableStateOf(userPreUiState.goal)
    }

/*    var goalOptionExpanded by remember {
        mutableStateOf(false)
    }*/

    // TODO: wait to add features
    var selectedGoalWeekly by remember {
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


    when(currentScreen) {
        SettingAdvancedSegment.SettingAdvanced.name -> {
            SettingAdvancedScreen(
                onBackClicked = {
                    currentScreen = ""
                },
                userPreferencesViewModel = userPreferencesViewModel
            )
        }

        else -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(15.dp)
            ) {

                Row(
                    modifier = Modifier
                        .padding(15.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Display Step Counts"
                    )

                    Switch(
                        checked = stepCountSwitchON,
                        onCheckedChange = { switchState ->
                            stepCountSwitchON = switchState
//                    userPreferencesViewModel.setDisplaySteps(true)
                        },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.Gray,
                            checkedTrackColor = Color.LightGray
                        )
                    )

                }

                Row(
                    modifier = Modifier
                        .padding(15.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Display Character"
                    )

                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){
                        Switch(
                            checked = progressMonsterSwitchON,
                            onCheckedChange = { switchState ->
                                progressMonsterSwitchON = switchState
                            },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.Gray,
                                checkedTrackColor = Color.LightGray
                            )
                        )

                        Text(text = if (progressMonsterSwitchON) "Monster" else "Human")

                    }


                }

                Row(
                    modifier = Modifier
                        .padding(15.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Effective Day"
                    )

                    TextField(
                        modifier = Modifier
                            .width(80.dp),
                        value = effectiveDays,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        ),
                        onValueChange = { newText ->
                            effectiveDays = newText
                        },
                        enabled = DateTimeUtil.getDayDifference(DateTimeUtil.getCurrentDateTime(), effectiveDate) >= effectiveDays.text.toLong()
                    )

                }

                Column(
                    modifier = Modifier
                        .padding(15.dp)
                        .fillMaxWidth(),
//                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier.width(200.dp),
                        text = "Change Goal"
                    )

                    Column(
                        modifier = Modifier
                            .padding(20.dp)
                    ) {
                        TabRow(selectedTabIndex = tabIndex) {
                            tabTitles.forEachIndexed {index, title ->
                                Tab(
                                    selected = tabIndex == index,
                                    onClick = {
                                        tabIndex = index
                                    },
                                    text = {
                                        Text(
                                            text = title,
                                            style = MaterialTheme.typography.caption
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
                                        value = selectedGoal.toFloat(),
                                        onValueChange = { sliderValue_ ->
                                            sliderValueDaily = sliderValue_
                                        },
                                        onValueChangeFinished = {
                                            // this is called when the user completed selecting the value
                                            selectedGoal = sliderValueDaily.toInt()
                                            Log.d("MainActivity", "sliderValue = $sliderValueDaily")
                                        },
                                        valueRange = 5000f..30000f,
                                        steps = 4,
                                        colors = SliderDefaults.colors(
                                            thumbColor = Color.Black,
                                            activeTrackColor = Color.Gray
                                        ),
                                        enabled = DateTimeUtil.getDayDifference(DateTimeUtil.getCurrentDateTime(), effectiveDate) >= effectiveDays.text.toLong()
                                    )

                                    Text(text = selectedGoal.toInt().toString())

                                }
                            }
                            1 -> {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Slider(
                                        value = selectedGoalWeekly.toFloat(),
                                        onValueChange = { sliderValue_ ->
                                            sliderValueWeekly = sliderValue_
                                        },
                                        onValueChangeFinished = {
                                            // this is called when the user completed selecting the value
                                            selectedGoalWeekly = sliderValueWeekly.toInt()
                                            Log.d("MainActivity", "sliderValue = $sliderValueWeekly")
                                        },
                                        valueRange = 20000f..100000f,
                                        steps = 7,
                                        colors = SliderDefaults.colors(
                                            thumbColor = Color.Black,
                                            activeTrackColor = Color.Gray
                                        ),
                                        enabled = DateTimeUtil.getDayDifference(DateTimeUtil.getCurrentDateTime(), effectiveDate) >= effectiveDays.text.toLong()
                                    )

                                    Text(text = sliderValueWeekly.toInt().toString())

                                }
                            }
                        }
                    }

                    /*Dropdown(
                        selectedIndex = changeGoal.toString(),
                        expand = goalOptionExpanded,
                        dropdownTitle = "",
                        dropdownOptions = DataSource.stepOptions,
                        onDropdownClicked = {
                            goalOptionExpanded = it
                        },
                        onDropdownItemClicked = {
                            changeGoal = it.toInt()
                        },
                        enable = DateTimeUtil.getDayDifference(DateTimeUtil.getCurrentDateTime(), effectiveDate) > effectiveDays.text.toLong()
                    )*/

                }

                Row(
                    modifier = Modifier
                        .padding(15.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // advanced settings button
                    Button(
                        onClick = {
                            currentScreen = SettingAdvancedSegment.SettingAdvanced.name
                        },
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black),
                    ){
                        Text(
                            text = "Advanced Settings",
                            style = MaterialTheme.typography.caption
                        )
                    }


                    // Save button
                    Button(
                        onClick = {
                            userPreferencesViewModel.setDisplaySteps(stepCountSwitchON)
                            userPreferencesViewModel.setDisplayMonster(progressMonsterSwitchON)
                            userPreferencesViewModel.setEffectiveDays(effectiveDays.text.toInt())
                            userPreferencesViewModel.setEffectiveDate(effectiveDate)
                            userPreferencesViewModel.setGoal(selectedGoal)
                            Log.i("SettingsScreen", "Effective?: ${DateTimeUtil.getDayDifference(DateTimeUtil.getCurrentDateTime(), effectiveDate) > effectiveDays.text.toLong()}")
                            Log.i("SettingsScreen", "EffectiveDate: ${effectiveDate}")


                        },
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black),
                    ){
                        Text(
                            text = "Save!",
                            style = MaterialTheme.typography.caption
                        )
                    }
                }



            }
        }
    }
}