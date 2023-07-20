package com.example.ula_app.android.ui.game

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import com.example.ula_app.android.ui.viewmodel.UserPreferencesViewModel
import kotlin.math.max

private const val TAG = "SettingScreen"

@Composable
fun SettingScreen(
    userPreferencesViewModel: UserPreferencesViewModel = viewModel()
) {

    var stepCountSwitchON by remember {
        mutableStateOf(false)
    }

    var progressMonsterSwitchON by remember {
        mutableStateOf(false)
    }

    var maxThreshold by remember {
        mutableStateOf(TextFieldValue(""))
    }

    var minThreshold by remember {
        mutableStateOf(TextFieldValue(""))
    }

    var effectiveDays by remember {
        mutableStateOf(TextFieldValue(""))
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
                    userPreferencesViewModel.setDisplaySteps(true)
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
                        userPreferencesViewModel.setDisplayMonster(true)
                    },
//                    thumbContent = thumbContent,
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
                }
            )

        }

        /*
        * TODO: add change goal
        * */
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
                }
            )

        }

    }
}