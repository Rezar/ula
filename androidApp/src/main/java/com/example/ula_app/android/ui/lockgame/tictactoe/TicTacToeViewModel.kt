package com.example.ula_app.android.ui.lockgame.tictactoe

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

private const val TAG = "TicTacToeViewModel"

class TicTacToeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(TicTacToeState())
    val uiState: StateFlow<TicTacToeState> = _uiState.asStateFlow()

    val boardState: MutableMap<Int, CellValue> = mutableMapOf(
        1 to CellValue.None,
        2 to CellValue.None,
        3 to CellValue.None,
        4 to CellValue.None,
        5 to CellValue.None,
        6 to CellValue.None,
        7 to CellValue.None,
        8 to CellValue.None,
        9 to CellValue.None
    )

    fun switchRole() {
        when (_uiState.value.currentRole) {
            // If current role is CIRCLE, then switch to CROSS.
            CellValue.Circle ->
                setCurrentRole(CellValue.Cross)
            // If current role is CROSS, then switch to CIRCLE.
            CellValue.Cross ->
                setCurrentRole(CellValue.Circle)

            else -> Log.e("${TAG}", "This is action is unsupported!")
        }
    }

    fun clickCell(cellNo: Int) {
        if (boardState[cellNo] != CellValue.None) {
            return
        }

        boardState[cellNo] = _uiState.value.currentRole

        checkVictory(CellValue.Circle)

        if (_uiState.value.gameResult != GameResult.None) {
            when (_uiState.value.currentRole) {
                CellValue.Circle -> {
                    setCircleWinCount(_uiState.value.circleWinCount + 1)
                }
                CellValue.Cross -> {
                    setCrossWinCount(_uiState.value.crossWinCount + 1)
                }
                else -> {

                }
            }
            return
        }

        if (checkBoardFull()) {
            setGameResult(GameResult.Draw)
            setDrawCount(_uiState.value.drawCount + 1)
            return
        }

        switchRole()
    }

    fun checkVictory(cellValue: CellValue) {
        when  {
            boardState[1] == cellValue && boardState[2] == cellValue && boardState[3] == cellValue -> {
                setGameResult(GameResult.Horizontal1)
            }
            boardState[4] == cellValue && boardState[5] == cellValue && boardState[6] == cellValue -> {
                setGameResult(GameResult.Horizontal2)
            }
            boardState[7] == cellValue && boardState[8] == cellValue && boardState[9] == cellValue -> {
                setGameResult(GameResult.Horizontal3)
            }
            boardState[1] == cellValue && boardState[4] == cellValue && boardState[7] == cellValue -> {
                setGameResult(GameResult.Vertical1)
            }
            boardState[2] == cellValue && boardState[5] == cellValue && boardState[8] == cellValue -> {
                setGameResult(GameResult.Vertical2)
            }
            boardState[3] == cellValue && boardState[6] == cellValue && boardState[9] == cellValue -> {
                setGameResult(GameResult.Vertical3)
            }
            boardState[1] == cellValue && boardState[5] == cellValue && boardState[9] == cellValue -> {
                setGameResult(GameResult.Diagonal1)
            }
            boardState[3] == cellValue && boardState[5] == cellValue && boardState[7] == cellValue -> {
                setGameResult(GameResult.Diagonal2)
            }
            else -> {}
        }
    }

    fun checkBoardFull(): Boolean {
        return !boardState.containsValue(CellValue.None)
    }

    fun setCircleWinCount(circleWinCount: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                circleWinCount = circleWinCount
            )
        }
    }

    fun setCrossWinCount(crossWinCount: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                crossWinCount = crossWinCount
            )
        }
    }

    fun setDrawCount(drawCount: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                drawCount = drawCount
            )
        }
    }

    fun setHintCount(hintText: String) {
        _uiState.update { currentState ->
            currentState.copy(
                hintText = hintText
            )
        }
    }

    fun setCurrentRole(currentRole: CellValue) {
        _uiState.update { currentState ->
            currentState.copy(
                currentRole = currentRole
            )
        }
    }

    fun setGameResult(gameResult: GameResult) {
        _uiState.update { currentState ->
            currentState.copy(
                gameResult = gameResult
            )
        }
    }

}