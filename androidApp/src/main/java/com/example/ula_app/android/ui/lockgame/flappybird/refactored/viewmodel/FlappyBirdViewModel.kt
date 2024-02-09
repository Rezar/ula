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

    // Check if the bird is hitting the sky
    fun isBirdHittingTheSky(): Boolean {
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
        return -viewState.value.birdState.yOffset + viewState.value.birdState.height * 0.5f <= viewState.value.safeZone.height * 0.5f
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

    fun isBirdHittingThePipe(): Boolean {
        // TODO(add rotate factor in bird bound calculate)
        // calculate top, bottom, left, right bound of the bird.
        val topBound = viewState.value.safeZone.height * 0.5f + viewState.value.birdState.yOffset - viewState.value.birdState.height * 0.5f
        val bottomBound = viewState.value.safeZone.height * 0.5f + viewState.value.birdState.yOffset + viewState.value.birdState.height * 0.5f
        val leftBound = viewState.value.safeZone.width * 0.5f - viewState.value.birdState.width * 0.5f
        val rightBound = viewState.value.safeZone.width * 0.5f + viewState.value.birdState.width * 0.5f

        val pipeStateList = viewState.value.pipeStateList
        pipeStateList.forEach { pipeState ->
            // If it's a up pipe.
            if (pipeState.direction == PipeDirection.Up) {
                // calculate top, left, right bound of this pipe.
                val topBound = viewState.value.safeZone.height - pipeState.pillarHeight - PipeState.TOP_HEIGHT
                val leftBound = (viewState.value.safeZone.width - PipeState.PILLAR_WIDTH) * 0.5f
                val rightBound = (viewState.value.safeZone.width + PipeState.PILLAR_WIDTH) * 0.5f
            }
            // If it's a down pipe.
            else if (pipeState.direction == PipeDirection.Down) {
                // calculate bottom, left, right bound of this pipe
                val bottomBound = pipeState.pillarHeight + PipeState.TOP_HEIGHT
                val leftBound = (viewState.value.safeZone.width - PipeState.PILLAR_WIDTH) * 0.5f
                val rightBound = (viewState.value.safeZone.width + PipeState.PILLAR_WIDTH) * 0.5f
            }
        }

        return false
    }

    fun gameOver(gameOverCauses: GameOverCauses) {
        when (gameOverCauses) {
            GameOverCauses.BirdHitSky -> {

            }
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
        if (viewState.value.gameStatus == GameStatus.Ide) {
            return false
        }

        // case 1: bird hit sky
        // case 2: bird hit ground
        // case 3: bird hit pipe
        if (
            viewState.value.gameStatus == GameStatus.GameOver ||
            isBirdHittingTheSky() ||
            isBirdHittingTheGround()
        ) {
            return true
        }

        return false
    }
}

