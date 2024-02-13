package com.example.ula_app.android.ui.lockgame.flappybird.refactored.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ula_app.android.R
import com.example.ula_app.android.ui.lockgame.flappybird.refactored.viewmodel.FlappyBirdViewModel

@Composable
fun Bird(
    viewModel: FlappyBirdViewModel
) {
    val uiState by viewModel.viewState.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.bird),
            contentScale = ContentScale.FillBounds,
            contentDescription = null,
            modifier = Modifier
                .size(Dp(uiState.birdState.width), Dp(uiState.birdState.height))
                .align(Alignment.Center)
                .offset(y = Dp(uiState.birdState.yOffset))
                .rotate(degrees = uiState.birdState.degree)  // Rotate 90 degree when dying/ over.
        )
    }
}

@Preview(widthDp = 411, heightDp = 660)
@Composable
fun PreviewBird() {
    Bird(viewModel = viewModel())
}