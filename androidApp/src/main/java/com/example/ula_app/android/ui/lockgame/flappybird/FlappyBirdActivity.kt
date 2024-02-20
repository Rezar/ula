package com.example.ula_app.android.ui.lockgame.flappybird

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ula_app.android.ui.lockgame.flappybird.ui.FlappyBird
import com.example.ula_app.android.ui.lockgame.flappybird.viewmodel.FlappyBirdViewModel
import com.example.ula_app.android.ui.theme.AppTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

val AUTO_TICK_INTERVAL = 50L

class FlappyBirdActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme {
                val viewModel: FlappyBirdViewModel = viewModel()

                LaunchedEffect(key1 = Unit) {
                    while (isActive) {
                        delay(AUTO_TICK_INTERVAL) // interval: 50ms
                        viewModel.autoTick()
                    }
                }

                FlappyBird(viewModel = viewModel)
            }
        }
    }
}