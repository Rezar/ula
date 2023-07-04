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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ula_app.android.R

data class Data(
    val weekday: String,
    val currentSteps: Int
)

@Composable
fun StatsDetailScreen(
    onBackClicked: () -> Unit = {}
) {

    val list = listOf(
        Data(
            weekday = "Monday",
            currentSteps = 1
        ),
        Data(
            weekday = "Tuesday",
            currentSteps = 2
        ),
        Data(
            weekday = "Wednesday",
            currentSteps = 3
        ),
        Data(
            weekday = "Thursday",
            currentSteps = 4
        ),
        Data(
            weekday = "Friday",
            currentSteps = 5
        ),
        Data(
            weekday = "Saturday",
            currentSteps = 6
        ),
        Data(
            weekday = "Sunday",
            currentSteps = 7
        ),
    )

    Column(
        modifier = Modifier.fillMaxSize()
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
            itemsIndexed(list) { index, item ->
                StatsDetailItem(
                    weekday = item.weekday,
                    currentSteps = item.currentSteps,
                    selectedIndex = index
                )
                if (index < list.size) {
                    Divider(color = Color.Black, thickness = 0.5.dp)
                }
            }
        }
    }
}

@Composable
fun StatsDetailItem(
    weekday: String = "Monday",
    currentSteps: Int = 5000,
    selectedIndex: Int = 0
) {


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .background(color = Color.White)
            .padding(10.dp),
        verticalArrangement = Arrangement.Center,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "${weekday}",
                modifier = Modifier.width(80.dp))
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = "${currentSteps} steps")
        }
        MonsterProgressBar(selectedIndex = selectedIndex)

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
    selectedIndex: Int = 0
) {
    // Stickman images.
    val stickman_1 = R.drawable.stickman_draft_bed
    val stickman_2 = R.drawable.stickman_draft_1
    val stickman_3 = R.drawable.stickman_draft_2
    val stickman_4 = R.drawable.stickman_draft_3
    val stickman_5 = R.drawable.stickman_draft_4
    val stickman_6 = R.drawable.stickman_draft_5

    val imageList = listOf(
        stickman_1,
        stickman_2,
        stickman_3,
        stickman_4,
        stickman_5,
        stickman_6
    )

    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        imageList.forEachIndexed{ index, item ->
            val painter = painterResource(id = item)
            Image(
                painter = painter,
                contentDescription = null,
                alpha = if (index == selectedIndex) 1f else 0.5f,
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