package com.example.ula_app.android.ui.lockgame.flappybird

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FlappyBirdViewModel : ViewModel() {

    private val _flappyBirdState = MutableStateFlow(FlappyBirdState())
    val flappyBirdState: StateFlow<FlappyBirdState> = _flappyBirdState.asStateFlow()

    fun setBirdSpeedX(birdSpeedX: Float) {
        _flappyBirdState.update { currentState ->
            currentState.copy(
                birdSpeedX = birdSpeedX
            )
        }
    }
}