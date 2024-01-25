package com.example.ula_app.android.ui.lockgame.snakegame.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.mukeshsolanki.snake.data.model.State
import com.example.ula_app.android.ui.lockgame.snakegame.domain.game.GameEngine
import com.example.ula_app.android.ui.lockgame.snakegame.presentation.theme.DarkGreen
import com.example.ula_app.android.ui.lockgame.snakegame.presentation.theme.border2dp
import com.example.ula_app.android.ui.lockgame.snakegame.presentation.theme.corner4dp
import com.example.ula_app.android.ui.lockgame.snakegame.presentation.theme.padding16dp

@Composable
fun Board(state: State) {
    BoxWithConstraints(Modifier.padding(padding16dp)) {
        val tileSize = maxWidth / GameEngine.BOARD_SIZE
        Box(
            Modifier
                .size(maxWidth)
                .border(border2dp, Color.DarkGray)
        )
        Box(
            Modifier
                .offset(x = tileSize * state.food.first, y = tileSize * state.food.second)
                .size(tileSize)
                .background(
                    Color.DarkGray, CircleShape
                )
        )
        state.snake.forEach {
            Box(
                modifier = Modifier
                    .offset(x = tileSize * it.first, y = tileSize * it.second)
                    .size(tileSize)
                    .background(
                        Color.DarkGray, RoundedCornerShape(corner4dp)
                    )
            )
        }
    }
}