package com.example.ula_app.android.ui.lockgame.flappybird.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.ula_app.android.R
import com.example.ula_app.android.ui.lockgame.flappybird.Default

@Composable
fun Background() {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
    ) {
        val (scene, road, earth) = createRefs()

        Image(
            painter = painterResource(id = R.drawable.background),
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
            painter = painterResource(id = R.drawable.foreground_road),
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
            painter = painterResource(id = R.drawable.foreground_earth),
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

//@Preview
//@Composable
//fun prev() {
//    Background()
//}