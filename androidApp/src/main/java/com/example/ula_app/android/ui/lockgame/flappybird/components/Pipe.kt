package com.example.ula_app.android.ui.lockgame.flappybird.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.ula_app.android.R
import com.example.ula_app.android.ui.lockgame.flappybird.Default

@Composable
fun Pipe(
    pipeHeight: Dp = Default.pipe.height
) {
    ConstraintLayout {
        val (pipeCover, pipePillar1, pipePillar2) = createRefs()

        Image(
            painter = painterResource(id = R.drawable.pipe_cover),
            contentDescription = null,
            modifier = Modifier.constrainAs(pipeCover) {
                top.linkTo(parent.top)
                bottom.linkTo(pipePillar1.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )
        Image(
            painter = painterResource(id = R.drawable.pipe_pillar),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.height(pipeHeight).constrainAs(pipePillar1) {
                top.linkTo(pipeCover.bottom)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )
    }
}

@Preview
@Composable
fun prevPipe() {
    Pipe()
}