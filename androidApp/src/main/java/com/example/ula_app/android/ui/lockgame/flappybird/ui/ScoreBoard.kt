package com.example.ula_app.android.ui.lockgame.flappybird.ui

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ula_app.android.R
import com.example.ula_app.android.ui.lockgame.flappybird.model.GameStatus
import com.example.ula_app.android.ui.lockgame.flappybird.viewmodel.FlappyBirdViewModel

@Composable
fun ScoreBoard(
    viewModel: FlappyBirdViewModel
) {
    val uiState by viewModel.viewState.collectAsState()

    when (uiState.gameStatus) {
        GameStatus.Ide, GameStatus.Running -> {
            RealTimeScoreBoard(viewModel = viewModel)
        }
        GameStatus.GameOver -> {
            GameOverSummary(viewModel = viewModel)
        }
    }
}

@Composable
fun RealTimeScoreBoard(
    viewModel: FlappyBirdViewModel = viewModel()
) {
    val uiState by viewModel.viewState.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = uiState.score.toString(),
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = Dp(-250f)),
            color = Color.Black,
            fontSize = 60.sp,
            style = MaterialTheme.typography.body1
        )
    }
}

@Composable
fun GameOverSummary(
    viewModel: FlappyBirdViewModel = viewModel()
) {

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .wrapContentSize()
                .offset(y = 0.dp)

        ) {
            Summary(viewModel = viewModel)

            Spacer(
                modifier = Modifier
                    .wrapContentWidth()
                    .height(40.dp)
            )

            // Restart && Exit
            Buttons(
                viewModel = viewModel
            )
        }
    }
}

@Composable
fun Summary(
    viewModel: FlappyBirdViewModel
) {
    val uiState by viewModel.viewState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        // Score board background
        Image(
            painter = painterResource(id = R.drawable.score_board_bg),
            contentScale = ContentScale.FillBounds,
            contentDescription = null,
            modifier = Modifier
                .size(180.dp, 240.dp)
                .align(Alignment.Center)
        )

        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .wrapContentSize()
        ) {
            LabelScoreField(label = R.drawable.score_bg, score = uiState.score)

            Spacer(
                modifier = Modifier
                    .wrapContentWidth()
                    .height(3.dp)
            )

            LabelScoreField(label = R.drawable.best_score_bg, score = uiState.bestScore)
        }
    }
}

@Composable
fun LabelScoreField(
    label: Int,
    score: Int
) {
    Column(
        modifier = Modifier.wrapContentSize()
    ) {
        // Score info image
        Image(
            painter = painterResource(id = label),
            contentScale = ContentScale.Fit,
            contentDescription = null,
            modifier = Modifier
                // .wrapContentSize()
                .size(80.dp, 25.dp)
                .align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier
            .wrapContentWidth()
            .height(3.dp))

        // Score
        Text(
            text = score.toString(),
            modifier = Modifier.align(Alignment.CenterHorizontally),
            color = Color.Black,
            fontSize = 40.sp,
            style = MaterialTheme.typography.body1
        )
    }
}

@Composable
fun Buttons(
    viewModel: FlappyBirdViewModel
) {
    val context = LocalContext.current

    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        // Restart button
        Image(
            painter = painterResource(id = R.drawable.restart_button),
            contentScale = ContentScale.Fit,
            contentDescription = null,
            modifier = Modifier
                .size(120.dp, 40.dp)
                .clickable(true) {
                    viewModel.restartGame()
                }
        )

        Spacer(modifier = Modifier
            .width(8.dp)
            .wrapContentHeight()
        )

        // Exit button
        Image(
            painter = painterResource(id = R.drawable.exit_button),
            contentScale = ContentScale.Fit,
            contentDescription = null,
            modifier = Modifier
                .size(120.dp, 40.dp)
                .clickable(true) {
                    val activity = context as Activity
                    activity.finish()
                }
        )
    }
}

@Preview
@Composable
fun PreviewScoreBoard() {
    ScoreBoard(viewModel = viewModel())
}

@Preview
@Composable
fun PreviewGameOverSummary() {
    GameOverSummary()
}