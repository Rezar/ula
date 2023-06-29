package com.example.ula_app.android.ui.lockgame.flappybird.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.ula_app.android.R
import com.example.ula_app.android.ui.lockgame.flappybird.Default

@Composable
fun Bird() {
    Image(
        painter = painterResource(id = R.drawable.bird),
        contentDescription = null,
        modifier = Modifier
            .size(Default.bird.width, Default.bird.height)
    )
}