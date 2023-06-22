package com.example.ula_app.android.ui.welcome

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.ula_app.android.R
import com.example.ula_app.android.component.IconButton

@Composable
fun WelcomePage2(
    onPreviousButtonClicked: () -> Unit,
    onNextButtonClicked: () -> Unit
) {
    // Read icon and images from drawable folder.
    val nextButtonIcon = painterResource(id = R.mipmap.next)
    val previousButtonIcon = painterResource(id = R.mipmap.back)
    val arrowIcon = painterResource(id = R.mipmap.arrows)
    val adultNormalMonster = painterResource(id = R.mipmap.ic_stickman_4)
    val adultFitMonster = painterResource(id = R.mipmap.stickman_draft_monster_5aug2014)
    val adultSleepingMonster = painterResource(id = R.mipmap.bed)
    val adultFatMonster = painterResource(id = R.mipmap.stickman_fat)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Ok?"
        )
        Row (
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(painter = adultNormalMonster, contentDescription = "Normal")
            Icon(painter = arrowIcon, contentDescription = "Arrow")
            Icon(painter = adultFitMonster, contentDescription = "Normal")
        }
        Row (
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(painter = adultSleepingMonster, contentDescription = "Sleeping")
            Icon(painter = arrowIcon, contentDescription = "Arrow")
            Icon(painter = adultFatMonster, contentDescription = "Fat")
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                Icon = {
                    Icon(
                        painter = previousButtonIcon,
                        contentDescription = "Previous"
                    )
                },
                onClick = {
                    onPreviousButtonClicked()
                    Log.i("WelcomePage_Page2", "Click Back on the Page 2")
                }
            )
            IconButton(
                Icon = {
                    Icon(
                        painter = nextButtonIcon,
                        contentDescription = "Next"
                    )
                },
                onClick = {
                    onNextButtonClicked()
                    Log.i("WelcomePage_Page2", "Click Next on the Page 2")
                }
            )
        }
    }
}