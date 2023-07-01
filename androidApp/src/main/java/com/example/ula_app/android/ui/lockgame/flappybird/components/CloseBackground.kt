package com.example.ula_app.android.ui.lockgame.flappybird.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.ula_app.android.R
import com.example.ula_app.android.ui.lockgame.flappybird.Default

@Composable
fun CloseBackground() {
    Column(
        modifier = Modifier
                .fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.background_road),
            contentScale = ContentScale.FillBounds,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(Default.background.roadWeight)
        )

        Image(
            painter = painterResource(id = R.drawable.background_earth),
            contentScale = ContentScale.FillBounds,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(Default.background.earthWeight)
        )
    }
}

@Preview
@Composable
fun prevCloseBackground() {
    CloseBackground()
}