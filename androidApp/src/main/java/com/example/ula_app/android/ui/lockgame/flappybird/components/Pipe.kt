package com.example.ula_app.android.ui.lockgame.flappybird.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.ula_app.android.R

@Composable
fun Pipe(
    offset: Dp = 50.dp
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
            modifier = Modifier.constrainAs(pipePillar1) {
                top.linkTo(pipeCover.bottom)
                bottom.linkTo(pipePillar2.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )
        Image(
            painter = painterResource(id = R.drawable.pipe_pillar),
            contentDescription = null,
            modifier = Modifier.constrainAs(pipePillar2) {
                top.linkTo(pipePillar1.bottom)
                bottom.linkTo(parent.bottom, goneMargin = offset)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )
    }
}

@Preview
@Composable
fun prev() {
    Pipe()
}