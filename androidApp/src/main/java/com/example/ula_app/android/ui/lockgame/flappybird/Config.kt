package com.example.ula_app.android.ui.lockgame.flappybird

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Bird(
    val width: Dp,
    val height: Dp,
    val initialSpeedX: Float
)

data class Background(
    val sceneWeight: Float,
    val roadWeight: Float,
    val earthWeight: Float
)

data class Pipe(
    val height: Dp,
    val offset: Dp
)

data class Config(
    val bird: Bird,
    val background: Background,
    val pipe: Pipe
)

val Default: Config = Config(
    bird = Bird(
        width = 87.dp,
        height = 61.dp,
        initialSpeedX = 0f
    ),
    background = Background(
        sceneWeight = 8f,
        roadWeight = 1f,
        earthWeight = 2f
    ),
    pipe = Pipe(
        height = 50.dp,
        offset = 0.dp
    )
)