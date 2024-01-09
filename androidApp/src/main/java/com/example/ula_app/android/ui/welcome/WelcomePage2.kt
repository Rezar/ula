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
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.ula_app.android.R
import com.example.ula_app.android.ui.component.IconButton

@Composable
fun WelcomePage2(
    onPreviousButtonClicked: () -> Unit,
    onNextButtonClicked: () -> Unit
) {
    // Read icon and images from drawable folder.
    val nextButtonIcon = painterResource(id = R.drawable.next)
    val previousButtonIcon = painterResource(id = R.drawable.back)
    val arrowIcon = painterResource(id = R.drawable.arrows)
    val adultNormalMonster = painterResource(id = R.drawable.ic_stickman_4)
    val adultFitMonster = painterResource(id = R.drawable.stickman_draft_monster_5aug2014)
    val adultSleepingMonster = painterResource(id = R.drawable.bed)
    val adultFatMonster = painterResource(id = R.drawable.stickman_fat)


    val leftPartWeight = 4.5f
    val middlePartWeight = 1f
    val rightPartWeight = 4.5f

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
                bottom = Default.padding.bottom,
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Ok?",
                    style = MaterialTheme.typography.h1
                )
            }

            Spacer(modifier = Modifier.height(60.dp))

            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Column(
                    modifier = Modifier.weight(leftPartWeight),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = adultNormalMonster,
                        contentDescription = null,
                        modifier = Modifier.scale(1.4f)
                    )
                }

                Column(
                    modifier = Modifier.weight(middlePartWeight),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = arrowIcon,
                        contentDescription = null
                    )
                }

                Column(
                    modifier = Modifier.weight(rightPartWeight),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = adultFitMonster,
                        contentDescription = null
                    )
                }

            }

            Spacer(modifier = Modifier.height(60.dp))

            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Column(
                    modifier = Modifier.weight(leftPartWeight),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = adultSleepingMonster,
                        contentDescription = null,
                        modifier = Modifier.scale(0.75f)
                    )
                }

                Column(
                    modifier = Modifier.weight(middlePartWeight),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = arrowIcon,
                        contentDescription = null
                    )
                }

                Column(
                    modifier = Modifier.weight(rightPartWeight),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = adultFatMonster,
                        contentDescription = null,
                        modifier = Modifier.scale(0.5f)
                    )
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
                    Log.i("WelcomePage_Page2", "Click Back on the Page 2")
                }
            )
            IconButton(
                Icon = {
                    Icon(
                        painter = nextButtonIcon,
                        contentDescription = "Next"
                    )
                },
                onClick = {
                    onNextButtonClicked()
                    Log.i("WelcomePage_Page2", "Click Next on the Page 2")
                }
            )
        }
    }
}