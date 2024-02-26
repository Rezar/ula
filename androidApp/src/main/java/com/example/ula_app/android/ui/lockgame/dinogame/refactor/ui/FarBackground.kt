package com.example.ula_app.android.ui.lockgame.dinogame.refactor.ui

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import com.example.ula_app.android.R
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ula_app.android.ui.lockgame.dinogame.refactor.DinoGameViewModel
import com.example.ula_app.android.ui.lockgame.dinogame.refactor.model.GameStatus

@Composable
fun FarBackground(
    viewModel: DinoGameViewModel
) {

    val uiState by viewModel.viewState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(0xE1F3F4F5))
    ) {

        Box(
            modifier = Modifier.fillMaxSize()
        ){
            Text(
                text = "Current: " + uiState.score.toString() + " Best: " + uiState.bestScore.toString(),
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = (-10).dp, y = 10.dp)
            )

            when (uiState.gameStatus) {
                GameStatus.GameOver -> {
                    GameOver(viewModel)
                } else -> {

                }
            }
        }

    }

}

@Composable
fun GameOver(
    viewModel: DinoGameViewModel
) {

    Box(
        modifier = Modifier.fillMaxSize()
    ){
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

@Composable
fun Buttons(
    viewModel: DinoGameViewModel
) {

    val context = LocalContext.current

    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ){

        Image(
            painter = painterResource(id = R.drawable.dino_restart),
            contentScale = ContentScale.Fit,
            contentDescription = null,
            modifier = Modifier
                .size(40.dp, 40.dp)
                .clickable(true) {
                    viewModel.restartGame()
                }
        )

        Spacer(
            modifier = Modifier
                .wrapContentWidth()
                .width(25.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.dino_exit),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp, 40.dp)
                .clickable(true) {
                    val activity = context as Activity
                    activity.finish()
                }
        )


    }

}


@Preview
@Composable
fun test1() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "uiState.score.toString()",
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = -10.dp, y = 10.dp)
        )

    }

}