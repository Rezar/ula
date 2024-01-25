package com.example.ula_app.android.ui.lockgame.snakegame.presentation.component

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign

@Composable
fun DisplayLarge(
    modifier: Modifier = Modifier,
    text: String,
    textAlign: TextAlign = TextAlign.Start
) {
    Text(
        modifier = modifier,
        text = text,
        color = Color.DarkGray,
        style = MaterialTheme.typography.displayLarge,
        textAlign = textAlign
    )
}

@Composable
fun TitleLarge(
    modifier: Modifier = Modifier,
    text: String,
    textAlign: TextAlign = TextAlign.Start
) {
    Text(
        modifier = modifier,
        text = text,
        color = Color.DarkGray,
        style = MaterialTheme.typography.titleLarge,
        textAlign = textAlign
    )
}

@Composable
fun BodyLarge(modifier: Modifier = Modifier, text: String, textAlign: TextAlign = TextAlign.Start) {
    Text(
        modifier = modifier,
        text = text,
        color = Color.DarkGray,
        style = MaterialTheme.typography.bodyLarge,
        textAlign = textAlign
    )
}