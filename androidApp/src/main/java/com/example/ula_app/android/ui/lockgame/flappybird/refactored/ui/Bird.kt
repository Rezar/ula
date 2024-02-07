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
import com.example.ula_app.android.R
import com.example.ula_app.android.ui.lockgame.flappybird.model.BirdSizeHeight
import com.example.ula_app.android.ui.lockgame.flappybird.model.BirdSizeWidth
import com.example.ula_app.android.ui.lockgame.flappybird.model.DefaultBirdHeightOffset
import com.example.ula_app.android.ui.lockgame.flappybird.refactored.model.GameOverCauses
import com.example.ula_app.android.ui.lockgame.flappybird.refactored.viewmodel.FlappyBirdViewModel

@Composable
fun Bird(
    viewModel: FlappyBirdViewModel
) {
    val uiState by viewModel.viewState.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        if (uiState.safeZone.initiated()) {
            if (viewModel.isHittingTheGround()) {
                viewModel.gameOver(GameOverCauses.BirdHitGround)
            }
        }

        /*if (state.playZoneSize.second > 0) {
            val playZoneHeightInDP = with(LocalDensity.current) {
                state.playZoneSize.second.toDp()
            }

            LogUtil.printLog(message = "Zone height:$playZoneHeightInDP  bird offset:${state.birdState.birdHeight}")
            val fallingThreshold = BirdHitGroundThreshold

            if (correctBirdHeight + fallingThreshold >= playZoneHeightInDP / 2) {
                // Send hit to ground action.
                LogUtil.printLog(message = "Bird hit ground")

                viewModel.dispatch(GameAction.HitGround)

                // Make sure bird not fall over ground.
                correctBirdHeight = playZoneHeightInDP / 2 - fallingThreshold
            }
        }*/

        Image(
            painter = painterResource(id = R.drawable.bird),
            contentScale = ContentScale.FillBounds,
            contentDescription = null,
            modifier = Modifier
                .size(uiState.birdState.width.pxToDp(), uiState.birdState.height.pxToDp())
                .align(Alignment.Center)
                .offset(y = uiState.birdState.yOffset.pxToDp())
                .rotate(degrees = uiState.birdState.degree)  // Rotate 90 degree when dying/ over.
        )
    }
}

@Preview(widthDp = 411, heightDp = 660)
@Composable
fun PreviewBird() {
    Box(Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.bird),
            contentScale = ContentScale.FillBounds,
            contentDescription = null,
            modifier = Modifier
                .size(BirdSizeWidth, BirdSizeHeight)
                .align(Alignment.Center)
                .offset(y = DefaultBirdHeightOffset)
        )
    }
}