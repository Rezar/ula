package com.example.ula_app.android.ui.lockgame.flappybird.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.ula_app.android.ui.lockgame.flappybird.util.LogUtil
import com.example.ula_app.android.R

@Composable
fun FarBackground(modifier: Modifier) {
    LogUtil.printLog(message = "FarBackground()")

    Column {
        Image(
            painter = painterResource(id = R.drawable.background_scene),
            contentScale = ContentScale.FillBounds,
            contentDescription = null,
            modifier = modifier.fillMaxSize()
        )
    }
}

@Preview(widthDp = 411, heightDp = 660)
@Composable
fun previewBackground() {
    FarBackground(Modifier.fillMaxSize())
}