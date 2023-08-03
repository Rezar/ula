package com.example.ula_app.android.ui.lockgame.snakegame.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val colorScheme = darkColorScheme(
    primary = DarkGreen,
    secondary = DarkGreen,
    tertiary = DarkGreen,
    background = Color.White,
    onPrimary = Color.White,
    onBackground = DarkGreen
)

@Composable
fun SnakeTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}