package com.example.ula_app.android.ui.lockgame.dinogame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ula_app.android.ui.theme.AppTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

val AUTO_TICK_INTERVAL = 50L

class DinoGameActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme {
                val viewModel: DinoGameViewModel = viewModel()

                LaunchedEffect(key1 = Unit) {
                    while(isActive) {
                        delay(AUTO_TICK_INTERVAL)
                        viewModel.autoTick()
                    }
                }

                DinoGame(viewModel = viewModel)
            }
        }
    }

}