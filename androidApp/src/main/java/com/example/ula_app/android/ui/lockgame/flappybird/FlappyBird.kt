package com.example.ula_app.android.ui.lockgame.flappybird

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ula_app.android.ui.lockgame.flappybird.components.Background
import com.example.ula_app.android.ui.lockgame.flappybird.components.Bird

@Composable
fun FlappyBird(
    flappyBirdViewModel: FlappyBirdViewModel = viewModel()
) {

    val flappyBirdUiState = flappyBirdViewModel.flappyBirdState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Background()
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Bird()
        }

    }
}

@Preview
@Composable
fun prevFlappyBird() {
    FlappyBird()
}