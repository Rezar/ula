package com.example.ula_app.android.ui.lockgame.flappybird.refactored.ui

import android.view.MotionEvent.ACTION_DOWN
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import com.example.ula_app.android.ui.lockgame.flappybird.refactored.viewmodel.FlappyBirdViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun FlappyBird(
    viewModel: FlappyBirdViewModel
) {
    val density = LocalDensity.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .run {
                pointerInteropFilter {
                    when (it.action) {
                        ACTION_DOWN -> {
                            viewModel.handleTap()
                            return@pointerInteropFilter false
                        }

                        else -> {
                            return@pointerInteropFilter false
                        }
                    }
                }
            }

    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally)
                .weight(0.90f)
                .onGloballyPositioned {
                    val width = with(density) {
                        it.size.width.toDp()
                    }
                    val height = with(density) {
                        it.size.height.toDp()
                    }
                    viewModel.initiateGameConfig(width.value, height.value)
                }
        ) {
            FarBackground()

            PipeGroup(
                viewModel = viewModel
            )
            
            Bird(
                viewModel = viewModel
            )

            ScoreBoard(
                viewModel = viewModel
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.10f)
        ) {
            NearForeground()
        }
    }
}

@Composable
fun Dp.dpToPx() = with(LocalDensity.current) { this@dpToPx.toPx() }

@Composable
fun Float.pxToDp() = with(LocalDensity.current) { this@pxToDp.toDp() }

@Composable
fun Int.pxToDp() = with(LocalDensity.current) { this@pxToDp.toDp() }