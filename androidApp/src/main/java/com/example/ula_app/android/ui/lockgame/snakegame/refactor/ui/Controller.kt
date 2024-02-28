package com.example.ula_app.android.ui.lockgame.snakegame.refactor.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.ula_app.android.ui.lockgame.snakegame.refactor.SnakeGameViewModel
import com.example.ula_app.android.ui.lockgame.snakegame.refactor.model.SnakeDirection

@Composable
fun Controller(
    viewModel: SnakeGameViewModel
) {

    val buttonSize = Modifier.size(64.dp)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(24.dp)
    ) {
        AppIconButton(
            icon = Icons.Default.KeyboardArrowUp,
            onClick = { viewModel.changeSnakeDirection(SnakeDirection.Up) }
        )

        Row {
            AppIconButton(
                icon = Icons.Default.KeyboardArrowLeft,
                onClick = { viewModel.changeSnakeDirection(SnakeDirection.Left)}
            )

            Spacer(modifier = buttonSize)

            AppIconButton(
                icon = Icons.Default.KeyboardArrowRight,
                onClick = { viewModel.changeSnakeDirection(SnakeDirection.Right)}
            )
        }
        AppIconButton(
            icon = Icons.Default.KeyboardArrowDown,
            onClick = { viewModel.changeSnakeDirection(SnakeDirection.Down)}
        )
    }

}


@Composable
fun AppIconButton(icon: ImageVector, onClick: () -> Unit) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .size(64.dp)
            .background(
                color = Color.DarkGray,
                shape = RoundedCornerShape(4.dp)
            ),
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.White
        )
    }
}