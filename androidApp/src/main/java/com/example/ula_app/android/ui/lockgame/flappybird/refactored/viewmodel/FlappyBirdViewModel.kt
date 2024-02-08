package com.example.ula_app.android.ui.lockgame.flappybird.refactored.viewmodel

import androidx.lifecycle.ViewModel
import com.example.ula_app.android.ui.lockgame.flappybird.refactored.model.BirdState
import com.example.ula_app.android.ui.lockgame.flappybird.refactored.model.GameOverCauses
import com.example.ula_app.android.ui.lockgame.flappybird.refactored.model.GameStatus
import com.example.ula_app.android.ui.lockgame.flappybird.refactored.model.PipeDirection
import com.example.ula_app.android.ui.lockgame.flappybird.refactored.model.PipeState
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

        val newPipeStateList = listOf<PipeState>(
            viewState.value.pipeStateList[0].move(),
            viewState.value.pipeStateList[1].move()
        )

        val newBirdState = if (viewState.value.isTapping) {
            viewState.value.birdState
        } else {
            viewState.value.birdState.fall(viewState.value.safeZone)
        }

        _viewState.update { currentState ->
            currentState.copy(
                roadStateList = newRoadStateList,
                pipeStateList = newPipeStateList,
                birdState = newBirdState
            )
        }
    }

    fun initiateGameConfig(width: Float, height: Float) {
        _viewState.update { currentState ->
            currentState.copy(
                roadStateList = listOf<RoadState>(
                    RoadState(
                        xOffset = 0f,
                        threshold = 10f - width
                    ),
                    RoadState(
                        xOffset = width - 10f,
                        threshold = 10f - width
                    )
                ),
                pipeStateList = listOf(
                    PipeState(
                        xOffset = width * 0.5f + 62f,
                        threshold = -width * 0.5f - 32f
                    ),
                    PipeState(
                        xOffset = width + 62f,
                        threshold = -width * 0.5f - 32f
                    )
                ),
                birdState = BirdState(),
                safeZone = SafeZone(
                    width = width,
                    height = height
                ),
            )
        }
    }

    fun resetRoad() {
        val newRoadStateList = listOf<RoadState>(
            viewState.value.roadStateList[1],
            viewState.value.roadStateList[0].reset(viewState.value.safeZone.width - 10f),
        )

        _viewState.update { currentState ->
            currentState.copy(
                roadStateList = newRoadStateList
            )
        }
    }

    fun resetPipe() {
        val newPipeStateList = listOf<PipeState>(
            viewState.value.pipeStateList[1],
            viewState.value.pipeStateList[0].reset(viewState.value.safeZone.width * 0.5f + 34f)
        )

        _viewState.update { currentState ->
            currentState.copy(
                pipeStateList = newPipeStateList
            )
        }
    }

    // Check if the bird is hitting the ground
    fun isBirdHittingTheGround(): Boolean {
        /*
        * ---------------------------------------------------- Top of safe zone / Top of the screen
        *                          |                  |
        *                          |                  |
        *                          |                  |
        *                          |                  |
        *                          |                  |
        *                          |       Half Height of Safe Zone
        *                          |                  |
        *                          |                  |
        *                          |                  |
        *                          |                  |
        *                          |                  |
        *                          |                  |
        * ---------------------------------------------------- Central of safe zone
        *                          |                  |
        *                          |                  |
        *                          |                  |
        *                          |                  |
        *                          |                  |
        *                          |       Half Height of Safe Zone
        *                      yOffset of Bird        |
        *                          |                  |
        *                         ---                 |
        *                          |                  |
        *                      1/2 BirdHeight         |
        *                          |                  |
        * ---------------------------------------------------- Bottom of Safe Zone
        *
        * From this graph, we know if bird.yOffset + 1/2 bird.height <= safeZone.height, the bird hit
        * this ground.
        * */
        return viewState.value.birdState.yOffset + viewState.value.birdState.height * 0.5f <= viewState.value.safeZone.height * 0.5f
    }

    /*
        * ---------------------------------------------------- Top of safe zone / Top of the screen
        *                          |                  |
        *                      1/2 BirdHeight         |
        *                          |                  |
        *                         ---                 |
        *                          |                  |
        *                          |         Half Height of Safe Zone
        *                          |                  |
        *                      yOffset of Bird        |
        *                          |                  |
        *                          |                  |
        *                          |                  |
        *                          |                  |
        * ---------------------------------------------------- Central of safe zone
        *                          |                  |
        *                          |                  |
        *                          |                  |
        *                          |                  |
        *                          |                  |
        *                          |         Half Height of Safe Zone
        *                          |                  |
        *                          |                  |
        *                         ---                 |
        *                          |                  |
        *                          |                  |
        *                          |                  |
        * ---------------------------------------------------- Bottom of Safe Zone
        *
        * From this graph, we know if -bird.yOffset + 1/2 bird.height <= safeZone.height, the bird hit
        * this ground.
        * */
    fun isBirdHittingTheSky(): Boolean {
        return -viewState.value.birdState.yOffset + viewState.value.birdState.height * 0.5f <= viewState.value.safeZone.height * 0.5f
    }

    fun gameOver(gameOverCauses: GameOverCauses) {
        when (gameOverCauses) {
            GameOverCauses.BirdHitGround -> {

            }
            GameOverCauses.BirdHitSky -> {

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

