package com.example.ula_app.android.ui.lockgame.dinogame.refactor

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
            viewState.value.dinoState.startJump()
        }
    }


    // When the game is waiting, do nothing
    // When the game is running, move road, cactus, and dino
    // when the game is over, do nothing
    fun autoTick() {

        if(isGameOver()) {
            gameOver()
            return
        }





        if(viewState.value.dinoState.isJump) {
            viewState.value.dinoState.handleJump()
        }
    }

    fun startGame() {
        _viewState.update { currentState ->
            currentState.copy(gameStatus = GameStatus.Running)
        }
    }

    fun initiateGameConfig(width: Float, height: Float) {
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
                        xOffset = width * 0.5f + cactusId1.width + 2f,
                        threshold = -width * 0.5f - cactusId1.width * 0.5f - 2f
                    ),
                    CactusState(
                        cactusId = cactusId2,
                        xOffset = -width * 0.5f - cactusId2.width * 0.5f + 30f - 2f,
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
            viewState.value.cactusStateList[0].reset(0.5f * (viewState.value.safeZone.width + CactusState.randomizeACactus().width) + ( 1/4 + Math.random().toFloat() * (3/4 - 1/4)) + 2f)
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
                        xOffset = 0f
                    ),
                    RoadState(
                        roadId = RoadId.SECOND,
                        xOffset = currentState.safeZone.width
                    ),
                ),

                cactusStateList = listOf(
                    CactusState(
                        cactusId = cactusId1,
                        xOffset = currentState.safeZone.width * 0.5f + cactusId1.width + 2f,
                    ),
                    CactusState(
                        cactusId = cactusId2,
                        xOffset = -currentState.safeZone.width * 0.5f - cactusId2.width * 0.5f + 30f - 2f,
                    )
                ),
                dinoState = DinoState(),
                gameStatus = GameStatus.Idle,
                score = 0
            )
        }
    }







    fun increaseScore() {

    }








    fun isDinoHittingTheCactus(): Boolean {

        return true
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