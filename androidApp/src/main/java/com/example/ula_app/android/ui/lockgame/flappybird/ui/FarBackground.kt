package com.example.ula_app.android.ui.lockgame.flappybird.ui

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
            contentScale = ContentScale.FillBounds,
            contentDescription = null,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview()
@Composable
fun previewBackground() {
    FarBackground()
}