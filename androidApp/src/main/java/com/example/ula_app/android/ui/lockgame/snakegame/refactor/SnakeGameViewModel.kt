package com.example.ula_app.android.ui.lockgame.snakegame.refactor

import androidx.lifecycle.ViewModel
import com.example.ula_app.android.ui.lockgame.snakegame.refactor.model.Coordinate
import com.example.ula_app.android.ui.lockgame.snakegame.refactor.model.GameStatus
import com.example.ula_app.android.ui.lockgame.snakegame.refactor.model.SnakeDirection
import com.example.ula_app.android.ui.lockgame.snakegame.refactor.model.ViewState
import com.example.ula_app.android.ui.lockgame.snakegame.refactor.model.boardSize
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SnakeGameViewModel: ViewModel() {

    private val _viewState = MutableStateFlow(ViewState())
    val viewState = _viewState.asStateFlow()

    // 1. make sure the snake can move and change direction
    // 2. isGameOver
    // 3. eat food and increase score
    fun autoTick() {

        when(viewState.value.gameStatus) {
            GameStatus.Idle -> {

            }

            GameStatus.Running -> {

                val viewState = viewState.value
                val snakeHead = viewState.snakeStateList.first()

                val newPosition: Coordinate = when(viewState.snakeDirection) {
                    SnakeDirection.Left -> Coordinate(snakeHead.x - 1, snakeHead.y)
                    SnakeDirection.Right -> Coordinate(snakeHead.x + 1, snakeHead.y)
                    SnakeDirection.Up -> Coordinate(snakeHead.x, snakeHead.y  - 1)
                    SnakeDirection.Down -> Coordinate(snakeHead.x, snakeHead.y + 1)
                }

                // need to check if the game is over everytime you move the snake
                // (or your snake has new position
                if(isGameOver(newPosition)) {
                    gameOver()
                    return
                }

                val foodCoordinate = viewState.foodState

                if(foodCoordinate.x == newPosition.x && foodCoordinate.y == newPosition.y) {
                    // 1. increase the snake length by 1, and add the food position as the snake's head
                    // 2. increase the score by 1
                    // 3. randomize a new food
                    _viewState.update { currentState ->
                        currentState.copy(
                            foodState = Coordinate.randomCoordinate(currentState.snakeStateList),
                            snakeStateList = listOf(foodCoordinate) + currentState.snakeStateList,
                            score = currentState.score + 1
                        )
                    }
                } else {
                    // update snake head to the new position and concatenate the snake body to the new head
                    _viewState.update { currentState ->
                        currentState.copy(
                            snakeStateList = listOf(newPosition) + currentState.snakeStateList.take(currentState.snakeStateList.size - 1)
                        )
                    }
                }
            }

            GameStatus.GameOver -> {

            }
        }
    }

    // to start button
    // start the game
    fun startGame() {
        _viewState.update {currentState ->
            currentState.copy(
                gameStatus = GameStatus.Running
            )
        }
    }

    fun gameOver(){
        _viewState.update { currentState ->
            currentState.copy(
                gameStatus = GameStatus.GameOver
            )
        }
    }

    fun isIdle(): Boolean {
        return viewState.value.gameStatus == GameStatus.Idle
    }

    fun isRunning(): Boolean {
        return viewState.value.gameStatus == GameStatus.Running
    }

    // restart game when the user hit the restart button
    // reset snake, food, score and game status
    fun restartGame() {
        _viewState.update { currentState ->
            currentState.copy(
                foodState = Coordinate.randomCoordinate(listOf(Coordinate(16, 2), Coordinate(16, 1))),
                snakeStateList = listOf(Coordinate(16, 2), Coordinate(16, 1)),
                snakeDirection = SnakeDirection.Down,
                gameStatus = GameStatus.Running,
                score = 0
            )
        }
    }

    // to controller buttons
    // to change the snake direction when the user hit the direction controllers
    fun changeSnakeDirection(newDirection: SnakeDirection) {

        val currentSnakeDirection = viewState.value.snakeDirection

        // If the snake is moving up or down, the only directions that it can change is right or left
        // Vice versa
        when(currentSnakeDirection) {
            SnakeDirection.Up, SnakeDirection.Down -> {
                when(newDirection) {
                    SnakeDirection.Left -> {
                        _viewState.update { currentState ->
                            currentState.copy(
                                snakeDirection = SnakeDirection.Left
                            )
                        }
                    }

                    SnakeDirection.Right -> {
                        _viewState.update { currentState ->
                            currentState.copy(
                                snakeDirection = SnakeDirection.Right
                            )
                        }
                    }

                    else -> {

                    }
                }
            }

            SnakeDirection.Left, SnakeDirection.Right -> {
                when(newDirection) {
                    SnakeDirection.Down -> {
                        _viewState.update { currentState ->
                            currentState.copy(
                                snakeDirection = SnakeDirection.Down
                            )
                        }
                    }

                    SnakeDirection.Up -> {
                        _viewState.update { currentState ->
                            currentState.copy(
                                snakeDirection = SnakeDirection.Up
                            )
                        }
                    }

                    else -> {

                    }
                }
            }

        }
    }



    fun isSnakeHittingTheBorder(newPosition: Coordinate): Boolean {

        val snakeDirection = viewState.value.snakeDirection

        val hasReachedLeftEnd = newPosition.x == -1 && snakeDirection == SnakeDirection.Left
        val hasReachedTopEnd = newPosition.y == -1 && snakeDirection == SnakeDirection.Up
        val hasReachedRightEnd = newPosition.x == boardSize && snakeDirection == SnakeDirection.Right
        val hasReachedBottomEnd = newPosition.y == boardSize && snakeDirection == SnakeDirection.Down

        return hasReachedLeftEnd || hasReachedTopEnd || hasReachedRightEnd || hasReachedBottomEnd
    }

    fun isSnakeHittingItself(newPosition: Coordinate): Boolean {
        return viewState.value.snakeStateList.contains(newPosition)
    }


    // called by the autoTick() function to obtain the new position of the snake
    fun isGameOver(newPosition: Coordinate): Boolean {
        return isSnakeHittingTheBorder(newPosition)
                || isSnakeHittingItself(newPosition)
                || viewState.value.gameStatus == GameStatus.GameOver
    }
}