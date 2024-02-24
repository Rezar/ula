package com.example.ula_app.android.ui.lockgame.dinogame.refactor.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ula_app.android.R
import com.example.ula_app.android.ui.lockgame.dinogame.refactor.DinoGameViewModel
import com.example.ula_app.android.ui.lockgame.dinogame.refactor.model.CactusId
import com.example.ula_app.android.ui.lockgame.dinogame.refactor.model.CactusState

@Composable
fun Cactus(
    viewModel: DinoGameViewModel = viewModel()
) {

    val uiState by viewModel.viewState.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        uiState.cactusStateList.forEach {cactusState ->
            CactusDisplay(
                cactusId = cactusState.cactusId,
                xOffset = Dp(cactusState.xOffset)
            )

        }
    }

    if(uiState.safeZone.initiated()) {
        uiState.cactusStateList.forEach { cactusState ->
            if(cactusState.isPassTheThreshold()) {
                viewModel.resetCactus()
            }
        }
    }

}


@Composable
fun CactusDisplay(
    cactusId: CactusId = CactusId.NORMAL,
    xOffset: Dp = 0.dp
) {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .offset(x = xOffset),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Cactus
            Image(
                painter = painterResource(id = cactusId.id),
                contentScale = ContentScale.FillBounds,
                contentDescription = null,
                modifier = Modifier.size(Dp(cactusId.width), Dp(cactusId.height))
            )
        }
    }

}