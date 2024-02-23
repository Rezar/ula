package com.example.ula_app.android.ui.lockgame.dinogame.refactor.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.ula_app.android.ui.lockgame.dinogame.refactor.DinoGameViewModel
import com.example.ula_app.android.R

@Composable
fun Dino(
    viewModel: DinoGameViewModel
) {
    val uiState by viewModel.viewState.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_dinopixel_2),
            contentScale = ContentScale.FillBounds,
            contentDescription = null,
            modifier = Modifier.size
        )
    }

}

