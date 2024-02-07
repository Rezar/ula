package com.example.ula_app.android.ui.lockgame.flappybird.refactored.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ula_app.android.R
import com.example.ula_app.android.ui.lockgame.flappybird.refactored.viewmodel.FlappyBirdViewModel

@Composable
fun NearForeground() {

    val viewModel: FlappyBirdViewModel = viewModel()

    val state by viewModel.viewState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Divider between background and foreground
        Divider(
            color = Color.DarkGray,
            thickness = 2.dp
        )

        // Road
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.18f)
        ) {
            state.roadStateList.forEach { roadState ->
                Image(
                    painter = painterResource(id = R.drawable.background_road),
                    contentScale = ContentScale.FillBounds,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .offset(x = roadState.xOffset.pxToDp())
                )
            }
        }

        // Earth
        Image(
            painter = painterResource(id = R.drawable.background_earth),
            contentScale = ContentScale.FillBounds,
            contentDescription = null,
            modifier = Modifier
                .weight(0.82f)
                .fillMaxSize()
        )

        if (state.safeZone.initiated()) {
            state.roadStateList.forEachIndexed { index, roadState ->
                if (roadState.xOffset <= - roadState.threshold) {
                    viewModel.resetRoad()
                }
            }
        }

//        // Send road reset action when road dismissed.
//        if (state.playZoneSize.first > 0) {
////            val playZoneWidthInDP = with(LocalDensity.current) {
////                state.playZoneSize.first.toDp()
////            }
//            state.roadStateList.forEachIndexed { index, roadState ->
//                LogUtil.printLog(message = "Road offset:${roadState.offset}")
//                if (roadState.offset <= - TempRoadWidthOffset) {
//                    // Road need reset.
//                    LogUtil.printLog(message = "Road reset")
//                    viewModel.dispatch(GameAction.RoadExit, roadIndex = index)
//                }
//            }
//        }
    }
}

@Preview(widthDp = 411, heightDp = 180)
@Composable
fun previewForeground() {
    NearForeground()
}