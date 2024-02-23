package com.example.ula_app.android.ui.lockgame.dinogame.refactor.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ula_app.android.R
import com.example.ula_app.android.ui.lockgame.dinogame.refactor.DinoGameViewModel

@Composable
fun NearForeground() {

    val viewModel: DinoGameViewModel = viewModel()

    val uiState by viewModel.viewState.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        Box(
            modifier = Modifier
                .weight(0.18f)
                .fillMaxWidth()
        ) {
            uiState.roadStateList.forEach { roadState ->
                Image(
                    painter = painterResource(id = roadState.roadId.id),
                    contentScale = ContentScale.FillBounds,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .offset(x = Dp(roadState.xOffset))

                )

                if(uiState.safeZone.initiated()) {
                    uiState.roadStateList.forEachIndexed {index, roadState ->
                        if (roadState.isPassTheThreshold()) {
//                            TODO: reset the road after building the viewModel
//                            viewModel.resetRoad()
                        }
                    }
                }
            }
        }

    }

}