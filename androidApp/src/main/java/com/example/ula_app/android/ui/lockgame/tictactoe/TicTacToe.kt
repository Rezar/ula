package com.example.ula_app.android.ui.lockgame.tictactoe

import android.app.Activity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ula_app.android.ui.component.IconButton
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

    // UiState
    val ticTacToeUiState by ticTacToeViewModel.uiState.collectAsState()
    val activity = (LocalContext.current as? Activity)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {

        Button(
            onClick = {
                activity?.finish()
            },
            colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.White, contentColor = Color.Black),
        ) {
            Text(
                text = "Back",
                style = MaterialTheme.typography.caption
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // How many times O has won.
            Text(text = "Player 'O' : ${ticTacToeUiState.circleWinCount}", fontSize = 16.sp, color = Color.Black)
            // How many times of draw.
            Text(text = "Draw : ${ticTacToeUiState.drawCount}", fontSize = 16.sp, color = Color.Black)
            // How many times X has won.
            Text(text = "Player 'X' : ${ticTacToeUiState.crossWinCount}", fontSize = 16.sp, color = Color.Black)
        }
        // Title of the game.
        Text(
            text = "Tic Tac Toe",
            color = Color.Black,
            style = MaterialTheme.typography.h2
        )
        /*
        * TicTacToe Board.
        *
        * The board has 4 levels of UIs.
        * Level 1: A blank rounded square:
        *       -> the background the the TicTacToe board.
        * Level 2: Cross lines:
        *       -> the cross lines divide the blank square into 9 even square areas.
        *       e.g.
        *       ----------          -------------
        *       |        |          | 1 | 2 | 3 |
        *       -        -          -------------
        *       |        |    ->    | 4 | 5 | 6 |
        *       -        -          -------------
        *       |        |          | 7 | 8 | 9 |
        *       ----------          -------------
        * Level 3: Labels: Circle, Cross, None
        * Level 4: Win lines. e.g. Win by 1,2,3; Win by 1,5,9
        * */
        // Level 1
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
            // Level 2
            BaseBoard()
            // Level 3
            LazyVerticalGrid(
                modifier = Modifier
                    .width(Default.board.size)
                    .aspectRatio(1f)
                    .padding(Default.board.padding),
                columns = GridCells.Fixed(3)
            ) {
                ticTacToeViewModel.boardState.forEach { (cellNo, cellValue) ->
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
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
                            // Level 3
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
            // Level 4
            Column(
                modifier = Modifier
                    .width(Default.board.size)
                    .padding(Default.board.padding)
                    .aspectRatio(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                AnimatedVisibility(
                    visible = ticTacToeViewModel.checkWin(),
                    enter = fadeIn(tween(2000))
                ) {
                    WinLines(state = ticTacToeUiState)
                }
            }
        }
        // Show hint text and play again(reset the board).
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Hint Text.
            Text(
                text = ticTacToeUiState.hintText,
                style = MaterialTheme.typography.h4,
                color = Color.Black
            )
            // Play again button.
            Button(
                onClick = {
                    ticTacToeViewModel.resetBoard()
                },
                shape = RoundedCornerShape(5.dp),
                elevation = ButtonDefaults.elevation(5.dp),
                colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.White, contentColor = Color.Black),
            ) {
                Text(
                    text = "Play Again",
                    style = MaterialTheme.typography.caption
                )
            }

        }
    }
}
/*
* Draw a win line based on only one win scenario.
* */
@Composable
fun WinLines(
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

//@Preview
//@Composable
//fun Prev() {
//    TicTacToe()
//}