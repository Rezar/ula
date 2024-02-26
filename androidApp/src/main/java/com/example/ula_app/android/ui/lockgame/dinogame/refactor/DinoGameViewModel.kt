package com.example.ula_app.android.ui.lockgame.dinogame.refactor

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.ula_app.android.ui.lockgame.dinogame.refactor.model.CactusId
import com.example.ula_app.android.ui.lockgame.dinogame.refactor.model.CactusState
import com.example.ula_app.android.ui.lockgame.dinogame.refactor.model.DinoState
import com.example.ula_app.android.ui.lockgame.dinogame.refactor.model.GameStatus
import com.example.ula_app.android.ui.lockgame.dinogame.refactor.model.RoadId
import com.example.ula_app.android.ui.lockgame.dinogame.refactor.model.RoadState
import com.example.ula_app.android.ui.lockgame.dinogame.refactor.model.SafeZone
import com.example.ula_app.android.ui.lockgame.dinogame.refactor.model.ViewState
import com.example.ula_app.android.ui.lockgame.flappybird.model.PipeState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

// handleTap function is to modify isJump
// case 1: if isJump == false, isJump = true (dino start to jump)
// case 2: if isJump == true, return (when dino jump, ignore other taps while jumping

// autoTick function is to make the dino jump
//

class DinoGameViewModel: ViewModel() {

    private val _viewState = MutableStateFlow(ViewState())
    val viewState = _viewState.asStateFlow()

    // change the jump status to true when you tap
    fun handleTap() {

        if(isIdle()) {
            startGame()
        }

        if(viewState.value.dinoState.isJump) {
            return
        } else {

            val newDinoState = viewState.value.dinoState.startJump()

            _viewState.update { currentState ->
                currentState.copy(
                    dinoState = newDinoState
                )
            }
        }
    }


    // When the game is waiting, do nothing
    // When the game is running, move road, cactus, and dino
    // when the game is over, do nothing
    fun autoTick() {

        when(viewState.value.gameStatus) {
            GameStatus.Idle -> {

            }
            GameStatus.Running -> {
                if(isGameOver()) {
                    gameOver()
                    return
                }

                increaseScore()

                // Update road, cactus, and dino status
                val newRoadStateList = listOf(
                    viewState.value.roadStateList[0].move(),
                    viewState.value.roadStateList[1].move()
                )

                val newCactusStateList = listOf(
                    viewState.value.cactusStateList[0].move(),
                    viewState.value.cactusStateList[1].move()
                )

                val newDinoState = viewState.value.dinoState.handleJump()
                Log.i("Dinogame", "${newDinoState}")

                _viewState.update { currentState ->
                    currentState.copy(
                        roadStateList = newRoadStateList,
                        cactusStateList = newCactusStateList,
                        dinoState = newDinoState
                    )
                }
            }
            GameStatus.GameOver -> {

            }
        }
    }

    fun startGame() {
        _viewState.update { currentState ->
            currentState.copy(gameStatus = GameStatus.Running)
        }
    }

    fun initiateGameConfig(width: Float, height: Float) {

        Log.i("dinogame", "${width}, ${height}")

        _viewState.update { currentState ->


            val cactusId1 = CactusState.randomizeACactus()
            val cactusId2 = CactusState.randomizeACactus()

            currentState.copy(

                roadStateList = listOf(
                    RoadState(
                        roadId = RoadId.FIRST,
                        xOffset = 0f,
                        threshold = -width
                    ),
                    RoadState(
                        roadId = RoadId.SECOND,
                        xOffset = width,
                        threshold = -width
                    )
                ),

                cactusStateList = listOf(
                    CactusState(
                        cactusId = cactusId1,
                        xOffset = width * 0.5f + cactusId1.width * 0.5f + 2f,
                        threshold = -width * 0.5f - cactusId1.width * 0.5f - 2f
                    ),
                    CactusState(
                        cactusId = cactusId2,
                        xOffset = width * 0.5f + cactusId2.width * 0.5f + 211f + 2f,
                        threshold = -width * 0.5f - cactusId2.width * 0.5f - 2f
                    )
                ),

                dinoState = DinoState(),
                safeZone = SafeZone(
                    width = width,
                    height = height
                )
            )
        }
    }

    fun resetCactus() {

        val newCactusStateList = listOf(
            viewState.value.cactusStateList[1],
            viewState.value.cactusStateList[0].reset(
                (viewState.value.safeZone.width + CactusState.randomizeACactus().width) + (viewState.value.safeZone.width + CactusState.randomizeACactus().width) * ( 0.16f + Math.random().toFloat() * (0.6f - 0.16f)) + 2f
            )
        )

        _viewState.update { currentState ->
            currentState.copy(
                cactusStateList = newCactusStateList
            )
        }

    }

    fun resetRoad() {

        val newRoadStateList = listOf(
            viewState.value.roadStateList[1],
            viewState.value.roadStateList[0].reset(viewState.value.safeZone.width)
        )

        _viewState.update { currentState ->
            currentState.copy(
                roadStateList = newRoadStateList
            )
        }

    }

    fun restartGame() {

        val cactusId1 = CactusState.randomizeACactus()
        val cactusId2 = CactusState.randomizeACactus()

        _viewState.update { currentState ->
            currentState.copy(

                roadStateList = listOf(
                    RoadState(
                        roadId = RoadId.FIRST,
                        xOffset = 0f,
                        threshold = -viewState.value.safeZone.width
                    ),
                    RoadState(
                        roadId = RoadId.SECOND,
                        xOffset = viewState.value.safeZone.width,
                        threshold = -viewState.value.safeZone.width
                    )
                ),

                cactusStateList = listOf(
                    CactusState(
                        cactusId = cactusId1,
                        xOffset = viewState.value.safeZone.width * 0.5f + cactusId1.width * 0.5f + 2f,
                        threshold = -viewState.value.safeZone.width * 0.5f - cactusId1.width * 0.5f - 2f
                    ),
                    CactusState(
                        cactusId = cactusId2,
                        xOffset = viewState.value.safeZone.width * 0.5f + cactusId2.width * 0.5f + 211f + 2f,
                        threshold = -viewState.value.safeZone.width * 0.5f - cactusId2.width * 0.5f - 2f
                    )
                ),
                dinoState = DinoState(),
                gameStatus = GameStatus.Idle,
                score = 0
            )
        }
    }



    fun increaseScore() {
        val dinoEdge = viewState.value.dinoState.dinoEdge(viewState.value.safeZone)

        val newCactusState = viewState.value.cactusStateList.map { cactusState ->
            val cactusEdge = cactusState.cactusEdge(viewState.value.safeZone)
            if(!cactusState.counted && cactusEdge.right < dinoEdge.left) {
                _viewState.update { currentState ->
                    currentState.copy(
                        score = currentState.score + 1,
                        bestScore = (currentState.score + 1).coerceAtLeast(currentState.bestScore)
                    )
                }
                cactusState.count()
            } else {
                cactusState
            }
        }

        _viewState.update { currentState ->
            currentState.copy(
                cactusStateList = newCactusState
            )
        }
    }



    fun isDinoHittingTheCactus(): Boolean {

        val dinoEdge = viewState.value.dinoState.dinoEdge(viewState.value.safeZone)

        val cactusState = viewState.value.cactusStateList

        cactusState.forEach { cactusState ->
            val cactusEdge = cactusState.cactusEdge(viewState.value.safeZone)

            // if the dino is on the left or right of the cactus, it does not hit the cactus
            // or if the dino is not passing or passed the cactus, it does not hit the cactus
            if(dinoEdge.right < cactusEdge.left || dinoEdge.left > cactusEdge.right) {
                return false
            }

            if((dinoEdge.right >= cactusEdge.left && dinoEdge.left <= cactusEdge.right) && dinoEdge.bottom >= cactusEdge.top) {
                return true
            }
        }

        return false
    }


    fun gameOver() {
        if(viewState.value.gameStatus == GameStatus.Running) {
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

    fun isGameOver(): Boolean {
        if(viewState.value.gameStatus == GameStatus.GameOver) {
            return true
        }

        return isDinoHittingTheCactus()
    }



}