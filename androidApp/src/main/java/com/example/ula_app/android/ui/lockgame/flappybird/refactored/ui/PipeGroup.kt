package com.example.ula_app.android.ui.lockgame.flappybird.refactored.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ula_app.android.R
import com.example.ula_app.android.ui.lockgame.flappybird.refactored.model.PipeDirection
import com.example.ula_app.android.ui.lockgame.flappybird.refactored.model.PipeState
import com.example.ula_app.android.ui.lockgame.flappybird.refactored.viewmodel.FlappyBirdViewModel

@Composable
fun PipeGroup(
    viewModel: FlappyBirdViewModel = viewModel()
) {
    val uiState by viewModel.viewState.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        uiState.pipeStateList.forEach { pipeState ->
            when (pipeState.direction) {
                PipeDirection.Up -> {
                    UpPipe(
                        pillarHeight = Dp(pipeState.pillarHeight),
                        xOffset = Dp(pipeState.xOffset)
                    )
                }
                PipeDirection.Down -> {
                    DownPipe(
                        pillarHeight = Dp(pipeState.pillarHeight),
                        xOffset = Dp(pipeState.xOffset)
                    )
                }
            }
        }
    }

    if (uiState.safeZone.initiated()) {
        uiState.pipeStateList.forEach { pipeState ->
            if (pipeState.isPassTheThreshold()) {
                viewModel.resetPipe()
            }
        }
    }
}

@Composable
fun UpPipe(
    pillarHeight: Dp = 200.dp,
    xOffset: Dp = 0.dp
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .offset(x = xOffset),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Pipe Cover
            Image(
                painter = painterResource(id = R.drawable.pipe_cover),
                contentScale = ContentScale.FillBounds,
                contentDescription = null,
                modifier = Modifier.size(60.dp, 30.dp)
            )

            // Pipe Pillar
            Image(
                painter = painterResource(id = R.drawable.pipe_pillar),
                contentScale = ContentScale.FillBounds,
                contentDescription = null,
                modifier = Modifier.size(50.dp, pillarHeight)
            )
        }
    }
}

@Composable
fun DownPipe(
    pillarHeight: Dp = 200.dp,
    xOffset: Dp = 0.dp
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .offset(x = xOffset),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Pipe Pillar
            Image(
                painter = painterResource(id = R.drawable.pipe_pillar),
                contentScale = ContentScale.FillBounds,
                contentDescription = null,
                modifier = Modifier.size(50.dp, pillarHeight)
            )

            // Pipe Cover
            Image(
                painter = painterResource(id = R.drawable.pipe_cover),
                contentScale = ContentScale.FillBounds,
                contentDescription = null,
                modifier = Modifier.size(60.dp, 30.dp)
            )
        }
    }
}

@Preview(widthDp = 411, heightDp = 660)
@Composable
fun PipePreview() {
    PipeGroup()
}