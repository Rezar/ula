package com.example.ula_app.android.ui.welcome

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Padding(
    val start: Dp,
    val end: Dp,
    val top: Dp,
    val bottom: Dp
)

data class Button(
    val width: Dp,
    val height: Dp
)

data class Config(
    val padding: Padding,
    val button: Button
)

val Default: Config = Config(
    padding = Padding(
        start = 20.dp,
        end = 20.dp,
        top = 50.dp,
        bottom = 0.dp
    ),
    button = Button(
        width = 130.dp,
        height = 90.dp
    )
)