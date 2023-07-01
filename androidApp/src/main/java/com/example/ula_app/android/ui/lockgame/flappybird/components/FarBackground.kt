package com.example.ula_app.android.ui.lockgame.flappybird.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.ula_app.android.R

@Composable
fun FarBackground() {
    Column {
        Image(
            painter = painterResource(id = R.drawable.background_scene),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview
@Composable
fun prevBackground() {
    FarBackground()
}