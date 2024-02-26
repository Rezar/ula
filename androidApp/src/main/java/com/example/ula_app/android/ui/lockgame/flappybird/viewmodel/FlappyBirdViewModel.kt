package com.example.ula_app.android.ui.lockgame.flappybird.viewmodel

import androidx.lifecycle.ViewModel
import com.example.ula_app.android.ui.lockgame.flappybird.model.BirdState
import com.example.ula_app.android.ui.lockgame.flappybird.model.GameStatus
import com.example.ula_app.android.ui.lockgame.flappybird.model.PipeDirection
import com.example.ula_app.android.ui.lockgame.flappybird.model.PipeState
import com.example.ula_app.android.ui.lockgame.flappybird.model.RoadState
import com.example.ula_app.android.ui.lockgame.flappybird.model.SafeZone
import com.example.ula_app.android.ui.lockgame.flappybird.model.ViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FlappyBirdViewModel: ViewModel() {
    private val _viewState = MutableStateFlow(ViewState())
    val viewState = _viewState.asStateFlow()

    fun handleTap() {
        // If game over, no actions are accepted.
        if (isGameOver()) {
            return
        }

        if (isIdle()) {
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

    fun restartGame() {
        _viewState.update { currentState ->
            currentState.copy(
                roadStateList = listOf<RoadState>(
                    RoadState(
                        xOffset = 0f,
                        threshold = 10f - viewState.value.safeZone.width
                    ),
                    RoadState(
                        xOffset = viewState.value.safeZone.width - 10f,
                        threshold = 10f - viewState.value.safeZone.width
                    )
                ),
                pipeStateList = listOf(
                    PipeState(
                        xOffset = viewState.value.safeZone.width * 0.5f + PipeState.TOP_WIDTH * 0.5f + 2f,
                        threshold = -viewState.value.safeZone.width * 0.5f - PipeState.TOP_WIDTH * 0.5f - 2f
                    ),
                    PipeState(
                        xOffset = viewState.value.safeZone.width + PipeState.TOP_WIDTH + 4f,
                        threshold = -viewState.value.safeZone.width * 0.5f - PipeState.TOP_WIDTH * 0.5f - 2f
                    )
                ),
                birdState = BirdState(),
                safeZone = SafeZone(
                    width = currentState.safeZone.width,
                    height = currentState.safeZone.height
                ),
                gameStatus = GameStatus.Idle,
                isTapping = false,
                score = 0
            )
        }
    }

    // for the position of the pipe, road, and bird in different game status
    // will always execute this function whenever you open the game
    // when the game is waiting, move the road.
    // When the game is running, move the road, pipe and bird.
    // When the game is over, do nothing (stop on where you were. )
    fun autoTick() {
        when (viewState.value.gameStatus) {
            GameStatus.Idle -> {
                // Update road
                val newRoadStateList = listOf<RoadState>(
                    viewState.value.roadStateList[0].move(),
                    viewState.value.roadStateList[1].move()
                )

                _viewState.update { currentState ->
                    currentState.copy(
                        roadStateList = newRoadStateList
                    )
                }
            }
            GameStatus.Running -> {
                if (isGameOver()) {
                    gameOver()
                    return
                }

                if (viewState.value.isTapping) {
                    return
                }

                increaseScore()

                // Update road, pipe, and bird states
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
            GameStatus.GameOver -> {

            }
        }
    }

    // Ultra hard!!!!
    fun increaseScore() {
        val birdEdge = viewState.value.birdState.birdEdge(viewState.value.safeZone)

        // This map function has two purpose.
        // 1. If the bird passed the pipe, change pipe.counted to true and increase score by 1.
        // 2. Update pipeStateList.
        val newPipeStateList = viewState.value.pipeStateList.map { pipeState ->
            val pipeEdge = pipeState.pipeEdge(viewState.value.safeZone)
            if (!pipeState.counted && pipeEdge.right < birdEdge.left) {
                _viewState.update { currentState ->
                    currentState.copy(
                        score = currentState.score + 1,
                        bestScore = (currentState.score + 1).coerceAtLeast(currentState.bestScore)
                    )
                }
                pipeState.count()
            } else {
                pipeState
            }
        }

        _viewState.update { currentState ->
            currentState.copy(
                pipeStateList = newPipeStateList
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
                        xOffset = width * 0.5f + PipeState.TOP_WIDTH * 0.5f + 2f,
                        threshold = -width * 0.5f - PipeState.TOP_WIDTH * 0.5f - 2f
                    ),
                    PipeState(
                        xOffset = width + PipeState.TOP_WIDTH + 4f,
                        threshold = -width * 0.5f - PipeState.TOP_WIDTH * 0.5f - 2f
                    )
                ),
                birdState = BirdState(), // defined in the birdState fields already.
                safeZone = SafeZone(
                    width = width,
                    height = height
                )
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
            viewState.value.pipeStateList[0].reset(viewState.value.safeZone.width * 0.5f + PipeState.TOP_WIDTH * 0.5f + 2f)
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
        * From this graph, we know if -bird.yOffset + 1/2 bird.height >= safeZone.height, the bird hit
        * the ground.
        * */
        if (viewState.value.birdState.yOffset >= 0) {
            return false
        }

        return -viewState.value.birdState.yOffset + viewState.value.birdState.height * 0.5f >= viewState.value.safeZone.height * 0.5f
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
        * From this graph, we know if bird.yOffset + 1/2 bird.height >= safeZone.height, the bird hit
        * the ground.
        * */
        if (viewState.value.birdState.yOffset <= 0) {
            return false
        }

        return viewState.value.birdState.yOffset + viewState.value.birdState.height * 0.5f >= viewState.value.safeZone.height * 0.5f
    }

    // Ultra hard!!!
    // TODO: when the bird hit the downward pipe, the game does not end. it is a bug for unknown reason.
    fun isBirdHittingThePipe(): Boolean {
        // calculate top, bottom, left, right bound of the bird.
        val birdEdge = viewState.value.birdState.birdEdge(viewState.value.safeZone)

        val pipeStateList = viewState.value.pipeStateList
        pipeStateList.forEach { pipeState ->
            when (pipeState.direction) {
                // If it's a up pipe.
                PipeDirection.Up -> {
                    val pipeEdge = pipeState.pipeEdge(viewState.value.safeZone)

                    // bird is closing to the pipe or away from the pipe.
                    if (birdEdge.right < pipeEdge.left || birdEdge.left > pipeEdge.right) {
                        return false
                    }

                    if ((birdEdge.right >= pipeEdge.left && birdEdge.left <= pipeEdge.right) && birdEdge.bottom >= pipeEdge.top) {
                        return true
                    }
                }
                // If it's a down pipe.
                PipeDirection.Down -> {
                    // calculate bottom, left, right bound of this pipe
                    val pipeEdge = pipeState.pipeEdge(viewState.value.safeZone)

                    // bird is closing to the pipe or away from the pipe.
                    if (birdEdge.right < pipeEdge.left || birdEdge.left > pipeEdge.right) {
                        return false
                    }

                    if ((birdEdge.right >= pipeEdge.left && birdEdge.left <= pipeEdge.right) && birdEdge.top <= pipeEdge.bottom) {
                        return true
                    }
                }
            }
        }

        return false
    }

    fun gameOver() {
        if (viewState.value.gameStatus == GameStatus.Running) {
            _viewState.update { currentState ->
                currentState.copy(
                    gameStatus = GameStatus.GameOver
                )
            }
        }
    }

    fun isIdle(): Boolean {
        return viewState.value.gameStatus == GameStatus.Idle
    }

    fun isRunning(): Boolean {
        return viewState.value.gameStatus == GameStatus.Running
    }

    fun isGameOver(): Boolean {
        if (viewState.value.gameStatus == GameStatus.GameOver) {
            return true
        }

        return isBirdHittingTheSky()
                || isBirdHittingTheGround()
                || isBirdHittingThePipe()
    }
}

