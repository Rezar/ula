package com.example.ula_app.android.ui.lockgame.snakegame.refactor

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.example.ula_app.android.ui.lockgame.snakegame.refactor.ui.Controller
import com.example.ula_app.android.ui.lockgame.snakegame.refactor.ui.PlayingBoard

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SnakeGame(
    viewModel: SnakeGameViewModel
) {
    val activity = LocalContext.current as Activity

    val uiState by viewModel.viewState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
    ) {

        // Back button and score display
        Box(
            modifier = Modifier.fillMaxWidth()
        ){

            IconButton(
                onClick = { activity.finish() },
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
            }

            Text(
                text = "Your Score: ${uiState.score.toString()}",
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .padding(16.dp, 0.dp),
                style = MaterialTheme.typography.caption

            )
        }

        // Playing board
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            PlayingBoard(viewModel = viewModel)
        }

        // Controller
        Box(
            modifier = Modifier.fillMaxWidth()
        ) {
            Controller(viewModel = viewModel)
        }

    }
}