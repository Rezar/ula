package com.example.ula_app.android.ui.lockgame.snakegame.presentation.activity

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ula_app.android.ui.lockgame.snakegame.domain.base.BaseActivity
import com.example.ula_app.android.ui.lockgame.snakegame.domain.navigation.Screen
import com.example.ula_app.android.ui.lockgame.snakegame.presentation.screen.AboutScreen
import com.example.ula_app.android.ui.lockgame.snakegame.presentation.screen.HighScoreScreen
import com.example.ula_app.android.ui.lockgame.snakegame.presentation.screen.MenuScreen
import com.example.ula_app.android.ui.lockgame.snakegame.presentation.screen.SettingScreen

class SnakeGameActivity : BaseActivity() {
    private lateinit var navController: NavHostController

    @Composable
    override fun Content() {
        navController = rememberNavController()
        SetupNavigation()
    }

    @Composable
    private fun SetupNavigation() {
        NavHost(navController = navController, startDestination = Screen.Menu.route) {
            composable(Screen.Menu.route) { MenuScreen(navController) }
//            composable(Screen.HighScores.route) { HighScoreScreen(navController) }
//            composable(Screen.Settings.route) { SettingScreen(navController) }
//            composable(Screen.About.route) { AboutScreen(navController) }
        }
    }
}
