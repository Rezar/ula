package com.example.ula_app.android.ui.game

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.ula_app.android.ULAApplication
import com.example.ula_app.android.ui.component.SettingFieldsRow
import com.example.ula_app.android.ui.viewmodel.UserPreferencesViewModel
import com.example.ula_app.util.DateTimeUtil
import java.io.IOException

private const val TAG = "SettingScreen"

enum class SettingAdvancedSegment(){
    SettingAdvanced,
}

@Composable
fun SettingScreen() {
    val context = LocalContext.current

    val userPreferencesViewModel: UserPreferencesViewModel = ULAApplication.getInstance<UserPreferencesViewModel>()
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
        mutableStateOf(userPreUiState.effectiveDays)
    }

    var effectiveDate by remember {
        mutableStateOf(userPreUiState.effectiveDate)
    }

    /*var selectedGoalDaily by remember {
        mutableStateOf(userPreUiState.goal)
    }*/

/*    var goalOptionExpanded by remember {
        mutableStateOf(false)
    }*/

    // TODO: wait to add features--------------------------------------------------------------------------------------------
    /*var selectedGoalWeekly by remember {
        mutableStateOf(0)
    }*/

    var tabIndex by remember { mutableStateOf(0)}
    val tabTitles = listOf("Daily Goal", "Weekly Goal")

    var sliderValueDaily by remember {
        mutableStateOf(userPreUiState.dailyGoal.toFloat())
    }

    var sliderValueWeekly by remember {
        mutableStateOf(userPreUiState.weeklyGoal.toFloat())
    }


    when(currentScreen) {
        SettingAdvancedSegment.SettingAdvanced.name -> {
            SettingAdvancedScreen(
                onBackClicked = {
                    currentScreen = ""
                }
            )
        }

        else -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(15.dp)
            ) {

                SettingFieldsRow(
                    text = "Display Step Counts",
                    input = {

                        Switch(
                            checked = stepCountSwitchON,
                            onCheckedChange = { switchState ->
                                stepCountSwitchON = switchState
//                    userPreferencesViewModel.setDisplaySteps(true)
                            },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = MaterialTheme.colors.onPrimary,
                                checkedTrackColor = MaterialTheme.colors.onSecondary,
                                uncheckedThumbColor = Color.Gray,
                                uncheckedTrackColor = Color.DarkGray
                            )
                        )
                    }
                )

                SettingFieldsRow(
                    text = "Display Character",
                    input = {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.End
                        ){
                            Switch(
                                checked = progressMonsterSwitchON,
                                onCheckedChange = { switchState ->
                                    progressMonsterSwitchON = switchState
                                },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = MaterialTheme.colors.onPrimary,
                                    checkedTrackColor = MaterialTheme.colors.onSecondary,
                                    uncheckedThumbColor = Color.Gray,
                                    uncheckedTrackColor = Color.DarkGray
                                )
                            )

                            Text(
                                text = if (progressMonsterSwitchON) "Monster" else "Human",
                                style = MaterialTheme.typography.h4
                            )

                        }
                    }
                )


                SettingFieldsRow(
                    text = "Effective Days",
                    input = {
                        TextField(
                            modifier = Modifier
                                .width(80.dp),
                            value = TextFieldValue(effectiveDays.toString()),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number
                            ),
                            onValueChange = { newText ->
                                effectiveDays = newText.text.toInt()
                            },
                            enabled = DateTimeUtil.getDayDifference(DateTimeUtil.nowInInstant(), effectiveDate) >= effectiveDays
                        )
                    }
                )

                Column(
                    modifier = Modifier
                        .padding(15.dp)
                        .fillMaxWidth(),
//                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier.width(200.dp),
                        text = "Change Goal",
                        style = MaterialTheme.typography.h4
                    )

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
                                        // TODO: Daily are using two state variables, use only one in the future
                                        value = sliderValueDaily,
                                        onValueChange = { sliderValue_ ->
                                            sliderValueDaily = sliderValue_
                                        },
//                                        onValueChangeFinished = {
//                                            // this is called when the user completed selecting the value
//                                            selectedGoalDaily = sliderValueDaily.toInt()
//                                            Log.d("MainActivity", "sliderValue = $sliderValueDaily")
//                                        },
                                        valueRange = 5000f..30000f,
                                        steps = 4,
                                        colors = SliderDefaults.colors(
                                            thumbColor = MaterialTheme.colors.onPrimary,
                                            activeTrackColor = MaterialTheme.colors.onSecondary
                                        ),
                                        enabled = DateTimeUtil.getDayDifference(DateTimeUtil.nowInInstant(), effectiveDate) >= effectiveDays
                                    )

                                    Text(text = sliderValueDaily.toInt().toString())

                                }
                            }
                            1 -> {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Slider(
                                        // TODO: No matter the value of selectedGoalWeekly, it always at the start of the slider, fix it.
                                        value = sliderValueWeekly,
                                        onValueChange = { sliderValue_ ->
                                            sliderValueWeekly = sliderValue_
                                        },
//                                        onValueChangeFinished = {
//                                            // this is called when the user completed selecting the value
//                                            selectedGoalWeekly = sliderValueWeekly.toInt()
//                                            Log.d("MainActivity", "sliderValue = $sliderValueWeekly")
//                                        },
                                        valueRange = 20000f..100000f,
                                        steps = 7,
                                        colors = SliderDefaults.colors(
                                            thumbColor = MaterialTheme.colors.onPrimary,
                                            activeTrackColor = MaterialTheme.colors.onSecondary
                                        ),
                                        enabled = DateTimeUtil.getDayDifference(DateTimeUtil.nowInInstant(), effectiveDate) >= effectiveDays
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
                            try {
                                userPreferencesViewModel.setDisplaySteps(stepCountSwitchON)
                                userPreferencesViewModel.setDisplayMonster(progressMonsterSwitchON)
                                userPreferencesViewModel.setEffectiveDays(effectiveDays)
                                userPreferencesViewModel.setEffectiveDate(effectiveDate)
//                                userPreferencesViewModel.setGoal(selectedGoalDaily)
                                userPreferencesViewModel.setDailyGoal(sliderValueDaily.toInt())
                                userPreferencesViewModel.setWeeklyGoal(sliderValueWeekly.toInt())
                                Log.i("SettingsScreen", "Effective?: ${
                                    DateTimeUtil.getDayDifference(
                                        DateTimeUtil.nowInInstant(), effectiveDate) > effectiveDays}")
                                Log.i("SettingsScreen", "EffectiveDate: ${effectiveDate}")
                                Toast.makeText(
                                    context,
                                    "Setting saved successfully",
                                    Toast.LENGTH_LONG,
                                ).show()
                            } catch (e: IOException) {
                                Toast.makeText(
                                    context,
                                    "Setting saved failure",
                                    Toast.LENGTH_LONG,
                                ).show()
                            }


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