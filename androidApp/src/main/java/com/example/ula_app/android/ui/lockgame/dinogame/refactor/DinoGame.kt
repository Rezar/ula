package com.example.ula_app.android.ui.lockgame.dinogame.refactor

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.platform.LocalContext
import android.content.pm.ActivityInfo
import android.os.Build
import android.view.MotionEvent
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import com.example.ula_app.android.ui.lockgame.dinogame.refactor.ui.Cactus
import com.example.ula_app.android.ui.lockgame.dinogame.refactor.ui.FarBackground
import com.example.ula_app.android.ui.lockgame.dinogame.refactor.ui.NearForeground


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DinoGame(
    viewModel: DinoGameViewModel
) {
    // TODO: may make the game as a landscape only composable

    val density = LocalDensity.current

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.4f)
                .run {
                    pointerInteropFilter {
                        when (it.action) {
                            MotionEvent.ACTION_DOWN -> { // will execute when you tap the screen
//                            viewModel.handleTap()
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
                    .onGloballyPositioned {//get the height and width of the box in pixel
                        val width = with(density) {
                            it.size.width.toDp()  // but jetpack compose needs a Dp instead of pixel
                        }
                        val height = with(density) {
                            it.size.height.toDp()
                        }

                        // initiate the width and height when you start the game
//                    viewModel.initiateGameConfig(width.value, height.value)
                    }
            ) {
                FarBackground()

                Cactus()


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
}



@Composable
fun ScreenOrientationProvider(
    orientation: Int = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED,
    children: @Composable () -> Unit
) {
    val activity = (LocalContext.current as? ComponentActivity)
        ?: error("No access to activity. Use LocalActivity from androidx.activity:activity-compose")
    activity.apply {
        requestedOrientation = orientation
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O && orientation == ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE) {
            requestedOrientation =
                ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
        }
    }

    children()
}
