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
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.ula_app.android.R
import com.example.ula_app.android.component.IconButton
import com.example.ula_app.android.data.DataSource.stepOptions

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
            Spacer(modifier = Modifier.height(60.dp))
            Column(
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
                            selected = it == selectedGoal.value,
                            onClick = {
                                selectedGoal.value = it
                                Log.i("WelcomePage_Page3", "Plan_${selectedGoal.value}")
                            }
                        )
                        Text(
                            text = "${it} steps"
                        )
                    }
                }
            }
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