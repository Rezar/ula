package com.example.ula_app.android.ui.lockgame.flappybird

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ula_app.android.ui.lockgame.flappybird.components.FarBackground
import com.example.ula_app.android.ui.lockgame.flappybird.components.Bird
import com.example.ula_app.android.ui.lockgame.flappybird.components.CloseBackground

@Composable
fun FlappyBird(
    flappyBirdViewModel: FlappyBirdViewModel = viewModel()
) {

    val flappyBirdUiState = flappyBirdViewModel.flappyBirdState.collectAsState()

    Column(
        modifier = Modifier
                .fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
                    .fillMaxHeight(Default.background.sceneWeight)
        ) {
            FarBackground()
        }

        Box(
            modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
                    .fillMaxHeight(1 - Default.background.sceneWeight)
        ) {
            CloseBackground()
        }
    }
}

@Preview
@Composable
fun prevFlappyBird() {
    FlappyBird()
}