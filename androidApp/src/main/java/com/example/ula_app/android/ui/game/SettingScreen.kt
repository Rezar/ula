package com.example.ula_app.android.ui.game

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ula_app.android.data.DataSource
import com.example.ula_app.android.ui.viewmodel.UserPreferencesViewModel
import com.example.ula_app.android.util.DateTimeUtil
import kotlin.math.max

private const val TAG = "SettingScreen"

@Composable
fun SettingScreen(
    userPreferencesViewModel: UserPreferencesViewModel = viewModel()
) {
    val userPreUiState by userPreferencesViewModel.userPreferencesState.collectAsState()

    var stepCountSwitchON by remember {
        mutableStateOf(userPreUiState.displaySteps)
    }

    var progressMonsterSwitchON by remember {
        mutableStateOf(userPreUiState.displayMonster)
    }

    var maxThreshold by remember {
        mutableStateOf(TextFieldValue(userPreUiState.maxThreshold.toString()))
    }

    var minThreshold by remember {
        mutableStateOf(TextFieldValue(userPreUiState.minThreshold.toString()))
    }

    var effectiveDays by remember {
        mutableStateOf(TextFieldValue(userPreUiState.effectiveDays.toString()))
    }

    var effectiveDate by remember {
        mutableStateOf(userPreUiState.effectiveDate)
    }

    var changeGoal by remember {
//        mutableStateOf(DataSource.stepOptions[0])
        mutableStateOf(userPreUiState.goal)
    }

    var goalOptionExpanded by remember {
        mutableStateOf(false)
    }




    Column(
        modifier = Modifier.fillMaxSize().padding(15.dp)
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
                text = "Display Monster"
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
                text = "Max Threshold (Percent)"
            )

            /*
            * TODO: only allowing numbers------------------------------------------------------------------
            * */
            TextField(
                modifier = Modifier.width(80.dp),
                value = maxThreshold,
                onValueChange = { newText ->
                    maxThreshold = newText
                }
            )

        }

        Row(
            modifier = Modifier
                .padding(15.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Min Threshold (Percent)"
            )

            /*
            * TODO: only allowing numbers------------------------------------------------------------------
            * */
            TextField(
                modifier = Modifier.width(80.dp),
                value = minThreshold,
                onValueChange = { newText ->
                    minThreshold = newText
                }
            )

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

            /*
            * TODO: only allowing numbers------------------------------------------------------------------
            * */
            TextField(
                modifier = Modifier.width(80.dp),
                value = effectiveDays,
                onValueChange = { newText ->
                    effectiveDays = newText
                },
                enabled = DateTimeUtil.getDayDifference(DateTimeUtil.getCurrentDateTime(), effectiveDate) > effectiveDays.text.toLong()
            )

        }

        Row(
            modifier = Modifier
                .padding(15.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                modifier = Modifier.width(200.dp),
                text = "Change Goal"
            )

            Dropdown(
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
            )

        }

        Row(
            modifier = Modifier
                .padding(15.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                onClick = {
                    userPreferencesViewModel.setDisplaySteps(stepCountSwitchON)
                    userPreferencesViewModel.setDisplayMonster(progressMonsterSwitchON)
                    userPreferencesViewModel.setMaxThreshold(maxThreshold.text.toDouble())
                    userPreferencesViewModel.setMinThreshold(minThreshold.text.toDouble())
                    userPreferencesViewModel.setEffectiveDays(effectiveDays.text.toInt())
                    userPreferencesViewModel.setEffectiveDate(effectiveDate)
                    userPreferencesViewModel.setGoal(changeGoal)
                    Log.i("SettingsScreen", "Effective?: ${DateTimeUtil.getDayDifference(DateTimeUtil.getCurrentDateTime(), effectiveDate) > effectiveDays.text.toLong()}")
                    Log.i("SettingsScreen", "EffectiveDate: ${effectiveDate}")
                },
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black),
            ){
                Text(text = "Save!")
            }
        }



    }
}