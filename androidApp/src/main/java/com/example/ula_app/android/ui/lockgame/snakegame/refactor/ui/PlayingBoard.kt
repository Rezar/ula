package com.example.ula_app.android.ui.lockgame.snakegame.refactor.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.ula_app.android.ui.lockgame.snakegame.refactor.SnakeGameViewModel
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


        // food
        Box(
            Modifier
                .offset(x = tileSize * uiState.foodState.x, y = tileSize * uiState.foodState.y)
                .size(tileSize)
                .background(Color.DarkGray, CircleShape)
        )

        // snake
        uiState.snakeStateList.forEach{
            Box(
                modifier = Modifier
                    .offset(x = tileSize * it.x, y = tileSize * it.y)
                    .size(tileSize)
                    .background(Color.DarkGray, RoundedCornerShape(4.dp))
            )
        }
    }
}