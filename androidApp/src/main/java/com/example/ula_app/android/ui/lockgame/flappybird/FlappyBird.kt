package com.example.ula_app.android.ui.lockgame.flappybird

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.ula_app.android.ui.lockgame.flappybird.components.Background
import com.example.ula_app.android.ui.lockgame.flappybird.components.Bird

@Composable
fun FlappyBird() {

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Background()
        Bird()
    }
}

@Preview
@Composable
fun prev() {
    FlappyBird()
}