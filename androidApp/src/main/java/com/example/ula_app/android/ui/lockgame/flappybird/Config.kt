package com.example.ula_app.android.ui.lockgame.flappybird

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Bird(
    val width: Dp,
    val height: Dp
)

data class Background(
    val sceneWeight: Float,
    val roadWeight: Float,
    val earthWeight: Float
)

data class Config(
    val bird: Bird,
    val background: Background
)

val Default: Config = Config(
    bird = Bird(
        width = 87.dp,
        height = 61.dp
    ),
    background = Background(
        sceneWeight = 8f,
        roadWeight = 1f,
        earthWeight = 2f
    )
)