package com.example.ula_app.android.ui.lockgame.flappybird.refactored.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.ula_app.android.ui.lockgame.flappybird.refactored.model.BirdState
import com.example.ula_app.android.ui.lockgame.flappybird.refactored.model.GameOverCauses
import com.example.ula_app.android.ui.lockgame.flappybird.refactored.model.GameStatus
import com.example.ula_app.android.ui.lockgame.flappybird.refactored.model.RoadState
import com.example.ula_app.android.ui.lockgame.flappybird.refactored.model.SafeZone
import com.example.ula_app.android.ui.lockgame.flappybird.refactored.model.ViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FlappyBirdViewModel: ViewModel() {
    private val _viewState = MutableStateFlow(ViewState())
    val viewState = _viewState.asStateFlow()

    fun handleTap() {
        if (isGameOver()) {
            return
        }

        if (isIde()) {
            startGame()
        }

        _viewState.update { currentState ->
            currentState.copy(
                isTapping = true,
                birdState = viewState.value.birdState.lift(viewState.value.safeZone)
            )
        }

        _viewState.update { currentState ->
            currentState.copy(
                isTapping = false
            )
        }
    }

    fun startGame() {
        _viewState.update { currentState ->
            currentState.copy(
                gameStatus = GameStatus.Running
            )
        }
    }

    fun autoTick() {
        if (isGameOver()) {
            return
        }

        val newRoadStateList = listOf<RoadState>(
            viewState.value.roadStateList[0].move(),
            viewState.value.roadStateList[1].move()
        )

        val newBirdState = if (viewState.value.isTapping) {
            viewState.value.birdState
        } else {
            viewState.value.birdState.fall(viewState.value.safeZone)
        }

        Log.i("BirdHeight", "${newBirdState.yOffset}")

        _viewState.update { currentState ->
            currentState.copy(
                roadStateList = newRoadStateList,
                birdState = newBirdState
            )
        }
    }

    fun initiateGameConfig(width: Float, height: Float) {
        _viewState.update { currentState ->
            currentState.copy(
                safeZone = SafeZone(top = 0f, bottom = height, left = 0f, right = width),
                roadStateList = listOf(RoadState(threshold = width - 10f), RoadState(xOffset = width - 10f, threshold = width - 10f)),
                birdState = BirdState()
            )
        }
    }

    fun resetRoad() {
        val newRoadStateList = listOf(
            viewState.value.roadStateList[1],
            viewState.value.roadStateList[0].reset(),
        )

        _viewState.update { currentState ->
            currentState.copy(
                roadStateList = newRoadStateList
            )
        }
    }

    fun isHittingTheGround(): Boolean {
        /*
        * ------------------------------------ Upper bound of screen
        *      |                  |
        *  yOffset of Bird        |
        *      |                  |
        *     ---          Height of Safe Zone
        *      |                  |
        *  1/2 BirdHeight         |
        *      |                  |
        * ------------------------------------ Lower bound of Safe Zone
        *
        * From this graph, we know if bird.yOffset + 1/2 bird.height >= safeZone.height, the bird hit
        * this ground.
        * */
        return viewState.value.birdState.yOffset + viewState.value.birdState.height / 2 <= viewState.value.safeZone.bottom / 2
    }

    fun gameOver(gameOverCauses: GameOverCauses) {
        when (gameOverCauses) {
            GameOverCauses.BirdHitGround -> {

            }
            GameOverCauses.BirdHitPipe -> {

            }
        }
    }

    fun isIde(): Boolean {
        return viewState.value.gameStatus == GameStatus.Ide
    }

    fun isRunning(): Boolean {
        return viewState.value.gameStatus == GameStatus.Running
    }

    fun isGameOver(): Boolean {
        return viewState.value.gameStatus == GameStatus.GameOver
    }
}

