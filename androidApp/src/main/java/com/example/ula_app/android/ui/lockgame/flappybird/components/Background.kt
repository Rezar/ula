package com.example.ula_app.android.ui.lockgame.flappybird.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.ula_app.android.R

@Composable
fun Background() {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val (scene, road, earth) = createRefs()

        Image(
            painter = painterResource(id = R.drawable.background_scene),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.constrainAs(scene) {
                top.linkTo(parent.top)
                bottom.linkTo(road.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            }
        )

        Image(
            painter = painterResource(id = R.drawable.background_road),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.constrainAs(road) {
                top.linkTo(scene.bottom)
                bottom.linkTo(earth.top)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            }
        )
        Image(
            painter = painterResource(id = R.drawable.background_earth),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.constrainAs(earth) {
                top.linkTo(road.bottom)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                width = Dimension.fillToConstraints
            }
        )
    }
}

@Preview
@Composable
fun prevBackground() {
    Background()
}