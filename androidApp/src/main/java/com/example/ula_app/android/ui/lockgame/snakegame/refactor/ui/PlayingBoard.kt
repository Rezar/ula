package com.example.ula_app.android.ui.lockgame.snakegame.refactor.ui

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ula_app.android.R
import com.example.ula_app.android.ui.lockgame.dinogame.ui.Buttons
import com.example.ula_app.android.ui.lockgame.snakegame.refactor.SnakeGameViewModel
import com.example.ula_app.android.ui.lockgame.snakegame.refactor.model.GameStatus
import com.example.ula_app.android.ui.lockgame.snakegame.refactor.model.boardSize


@Composable
fun PlayingBoard(
    viewModel: SnakeGameViewModel
) {

    val uiState by viewModel.viewState.collectAsState()

    BoxWithConstraints(
        Modifier.padding(16.dp)
    ) {
        val tileSize = maxWidth / boardSize

        // board border
        Box(
            Modifier
                .size(maxWidth)
                .border(2.dp, Color.DarkGray)
        )


        when(uiState.gameStatus) {
            GameStatus.Idle -> {
                // start game
                Button(
                    modifier = Modifier
                        .width(248.dp)
                        .align(alignment = Alignment.Center),
                    onClick = {
                         viewModel.startGame()
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.DarkGray,
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "Start Game",
                        style = MaterialTheme.typography.body1
                    )

                }
            }

            GameStatus.Running -> {
                // food and snake
                // food
                Box(
                    Modifier
//                        .align(alignment = Alignment.Center)
                        .offset(
                            x = tileSize * uiState.foodState.x,
                            y = tileSize * uiState.foodState.y
                        )
                        .size(tileSize)
                        .background(Color.DarkGray, CircleShape)
                )

                // snake
                uiState.snakeStateList.forEach{
                    Box(
                        modifier = Modifier
//                            .align(alignment = Alignment.Center)
                            .offset(x = tileSize * it.x, y = tileSize * it.y)
                            .size(tileSize)
                            .background(Color.DarkGray, RoundedCornerShape(4.dp))
                    )
                }

            }
            GameStatus.GameOver ->  {
                // restart game and exit
                Column(
                    modifier = Modifier
                        .align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Text(
                        text = "Game Over",
                        color = Color.Black,
                        fontSize = 40.sp,
                        style = MaterialTheme.typography.body1
                    )

                    Buttons(viewModel = viewModel)
                }
            }
        }
    }
}

@Composable
fun Buttons(
    viewModel: SnakeGameViewModel
) {
    val context = LocalContext.current

    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ){

        Button(
            modifier = Modifier.width(124.dp),
            onClick = {
                viewModel.restartGame()
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.DarkGray,
                contentColor = Color.White
            )
        ) {
            Text(
                text = "Restart",
                style = MaterialTheme.typography.body1
            )

        }

        Spacer(
            modifier = Modifier
                .wrapContentWidth()
                .width(50.dp)
        )

        Button(
            modifier = Modifier.width(124.dp),
            onClick = {
                val activity = context as Activity
                activity.finish()
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.DarkGray,
                contentColor = Color.White
            )
        ) {
            Text(
                text = "Exit",
                style = MaterialTheme.typography.body1
            )
        }
    }
}