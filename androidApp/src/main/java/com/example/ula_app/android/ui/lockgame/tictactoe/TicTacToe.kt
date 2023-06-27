package com.example.ula_app.android.ui.lockgame.tictactoe

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ula_app.android.ui.lockgame.tictactoe.components.BaseBoard
import com.example.ula_app.android.ui.lockgame.tictactoe.components.Circle
import com.example.ula_app.android.ui.lockgame.tictactoe.components.Cross
import com.example.ula_app.android.ui.lockgame.tictactoe.components.DiagonalWinLine1
import com.example.ula_app.android.ui.lockgame.tictactoe.components.DiagonalWinLine2
import com.example.ula_app.android.ui.lockgame.tictactoe.components.HorizontalWinLine1
import com.example.ula_app.android.ui.lockgame.tictactoe.components.HorizontalWinLine2
import com.example.ula_app.android.ui.lockgame.tictactoe.components.HorizontalWinLine3
import com.example.ula_app.android.ui.lockgame.tictactoe.components.VerticalWinLine1
import com.example.ula_app.android.ui.lockgame.tictactoe.components.VerticalWinLine2
import com.example.ula_app.android.ui.lockgame.tictactoe.components.VerticalWinLine3

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun TicTacToe(
    ticTacToeViewModel: TicTacToeViewModel = viewModel()
) {

    val ticTacToeUiState by ticTacToeViewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Player 'O' : ${ticTacToeUiState.circleWinCount}", fontSize = 16.sp)
            Text(text = "Draw : ${ticTacToeUiState.drawCount}", fontSize = 16.sp)
            Text(text = "Player 'X' : ${ticTacToeUiState.crossWinCount}", fontSize = 16.sp)
        }
        Text(
            text = "Tic Tac Toe",
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Cursive,
            color = Color.Blue
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .shadow(
                    elevation = 10.dp,
                    shape = RoundedCornerShape(20.dp)
                )
                .clip(RoundedCornerShape(20.dp))
                .background(Default.board.backgroundColor),
            contentAlignment = Alignment.Center
        ) {
            BaseBoard()
            LazyVerticalGrid(
                modifier = Modifier
                    .size(Default.board.size)
                    .aspectRatio(1f),
                columns = GridCells.Fixed(3)
            ) {
                ticTacToeViewModel.boardState.forEach { (cellNo, cellValue) ->
                    item {
                        Column(
                            modifier = Modifier
                                .size(Default.cell.size)
                                .aspectRatio(1f)
                                .clickable(
                                    interactionSource = MutableInteractionSource(),
                                    indication = null
                                ) {
                                    ticTacToeViewModel.clickCell(cellNo)
                                },
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            AnimatedVisibility(
                                visible = ticTacToeViewModel.boardState[cellNo] != CellValue.None,
                                enter = scaleIn(tween(1000))
                            ) {
                                if (cellValue == CellValue.Circle) {
                                    Circle()
                                } else if (cellValue == CellValue.Cross) {
                                    Cross()
                                }
                            }
                        }
                    }
                }
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = ticTacToeUiState.hintText,
                fontSize = 24.sp,
                fontStyle = FontStyle.Italic
            )
            Button(
                onClick = {

                },
                shape = RoundedCornerShape(5.dp),
                elevation = ButtonDefaults.elevation(5.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Blue,
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Play Again",
                    fontSize = 16.sp
                )
            }

        }
    }
}

@Composable
fun DrawVictoryLine(
    state: TicTacToeState
) {
    when (state.gameResult) {
        GameResult.Vertical1 -> VerticalWinLine1()
        GameResult.Vertical2 -> VerticalWinLine2()
        GameResult.Vertical3 -> VerticalWinLine3()
        GameResult.Horizontal1 -> HorizontalWinLine1()
        GameResult.Horizontal2 -> HorizontalWinLine2()
        GameResult.Horizontal3 -> HorizontalWinLine3()
        GameResult.Diagonal1 -> DiagonalWinLine1()
        GameResult.Diagonal2 -> DiagonalWinLine2()
        else -> {}
    }
}

@Preview
@Composable
fun P() {
    TicTacToe()
}