package com.example.ula_app.android.ui.lockgame.flappybird.ui

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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ula_app.android.R
import com.example.ula_app.android.ui.lockgame.flappybird.viewmodel.FlappyBirdViewModel

@Composable
fun NearForeground(
    viewModel: FlappyBirdViewModel = viewModel()
) {

    val uiState by viewModel.viewState.collectAsState()

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
                .weight(0.18f)
                .fillMaxWidth()
        ) {
            uiState.roadStateList.forEach { roadState ->
                Image(
                    painter = painterResource(id = R.drawable.background_road),
                    contentScale = ContentScale.FillBounds,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .offset(x = Dp(roadState.xOffset))
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

        if (uiState.safeZone.initiated()) {
            uiState.roadStateList.forEachIndexed { index, roadState ->
                if (roadState.isPassTheThreshold()) {
                    viewModel.resetRoad()
                }
            }
        }
    }
}

@Preview(widthDp = 411, heightDp = 180)
@Composable
fun PreviewForeground() {
    NearForeground()
}