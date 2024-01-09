package com.example.ula_app.android.ui.lockgame.snakegame.presentation.screen

import android.app.Activity
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import com.example.ula_app.android.R
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.ula_app.android.ui.lockgame.snakegame.domain.extension.launchActivity
import com.example.ula_app.android.ui.lockgame.snakegame.domain.navigation.Screen
import com.example.ula_app.android.ui.lockgame.snakegame.presentation.activity.GameActivity
import com.example.ula_app.android.ui.lockgame.snakegame.presentation.component.AppButton
import com.example.ula_app.android.ui.lockgame.snakegame.presentation.component.DisplayLarge
import com.example.ula_app.android.ui.lockgame.snakegame.presentation.theme.border2dp
import com.example.ula_app.android.ui.lockgame.snakegame.presentation.theme.padding16dp
import com.example.ula_app.android.ui.lockgame.snakegame.presentation.theme.padding64dp
import com.example.ula_app.android.ui.lockgame.snakegame.presentation.theme.width248dp

@Composable
fun MenuScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding16dp)
            .border(width = border2dp, color = Color.DarkGray),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val context = LocalContext.current
        val activity = context as? Activity
        DisplayLarge(text = stringResource(id = R.string.app_name))
        AppButton(
            modifier = Modifier
                .width(width248dp)
                .padding(top = padding64dp),
            text = stringResource(R.string.new_game)
        ) { context.launchActivity<GameActivity>() }
        Spacer(modifier = Modifier.height(64.dp))
        AppButton(
            modifier = Modifier
                .width(width248dp),
            text = "Back",
            onClick = {
                activity?.finish()
            }
        )
//        AppButton(
//            modifier = Modifier.width(width248dp),
//            text = stringResource(id = R.string.high_score)
//        ) {
//            navController.navigate(Screen.HighScores.route)
//        }
//        AppButton(modifier = Modifier.width(width248dp), text = stringResource(R.string.settings)) {
//            navController.navigate(Screen.Settings.route)
//        }
//        AppButton(modifier = Modifier.width(width248dp), text = stringResource(R.string.about)) {
//            navController.navigate(Screen.About.route)
//        }
    }
}