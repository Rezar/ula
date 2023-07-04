package com.example.ula_app.android.ui.game

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

private const val TAG = "StatsScreen"

enum class StatsScreenSegment() {
    StatsDetail,
    StatsTrend
}

@Composable
fun StatsScreen() {
    var currentScreen by remember {
        mutableStateOf("")
    }

    when (currentScreen) {
        StatsScreenSegment.StatsDetail.name -> {
            StatsDetailScreen(
                onBackClicked = {
                    currentScreen = ""
                }
            )
        }
        StatsScreenSegment.StatsTrend.name -> {
            StatsTrendScreen(
                onBackClicked = {
                    currentScreen = ""
                }
            )
        }
        else -> {
            Column(
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
            }
        }
    }


}