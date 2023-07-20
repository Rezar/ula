package com.example.ula_app.android.ui

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.ula_app.android.data.BottomNavigationBarItem
import com.example.ula_app.android.data.DataSource
import com.example.ula_app.android.ui.game.DebugScreen
import com.example.ula_app.android.ui.game.HelpScreen
import com.example.ula_app.android.ui.game.HomeScreen
import com.example.ula_app.android.ui.game.SettingScreen
import com.example.ula_app.android.ui.game.StatsScreen
import com.example.ula_app.android.ui.theme.AppTheme
import com.example.ula_app.android.ui.theme.WelcomeTheme
import com.example.ula_app.android.ui.viewmodel.DebugViewModel
import com.example.ula_app.android.ui.viewmodel.GoalViewModel
import com.example.ula_app.android.ui.viewmodel.HomeViewModel
import com.example.ula_app.android.ui.viewmodel.StepViewModel
import com.example.ula_app.android.ui.viewmodel.UserPreferencesViewModel
import com.example.ula_app.android.ui.welcome.WelcomePage1
import com.example.ula_app.android.ui.welcome.WelcomePage2
import com.example.ula_app.android.ui.welcome.WelcomePage3
import com.example.ula_app.android.util.DateTimeUtil

// Screens in Game section.
enum class GameScreen() {
    Home,
    Stats,
    StatsStepDetail,
    Help,
    Setting,
    Debug

}

// Screens in Welcome section.
enum class WelcomeScreen() {
    Page1,
    Page2,
    Page3
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun Game(
        userPreferencesViewModel: UserPreferencesViewModel,
        stepViewModel: StepViewModel = viewModel(),
        goalViewModel: GoalViewModel = viewModel(),
        homeViewModel: HomeViewModel = viewModel(),
        debugViewModel: DebugViewModel = viewModel(),
        navController: NavHostController = rememberNavController()
) {
    // ui state
    val goalUiState by goalViewModel.uiState.collectAsState()
    val userPreferencesUiState by userPreferencesViewModel.userPreferencesState.collectAsState()

    Log.i("GameScreen", "${goalUiState.firstTime}")

    // Collection of game screens.
    val gameScreens = listOf(
        GameScreen.Home,
        GameScreen.Stats,
        GameScreen.Help,
        GameScreen.Setting,
        GameScreen.Debug
    )

    // Whether show bottom or not.
    val showBottomBar = navController.currentBackStackEntryAsState().value?.destination?.route in gameScreens.map { it.name }

    Scaffold(
        bottomBar = {
            // Only show bottom navigation bar in Home, Stats, Help, Setting, Debug screens.
            if (showBottomBar) {
                BottomNavigationBar(
                    items = DataSource.bottomNavigationBarItem,
                    navController = navController,
                    onItemClicked = {
                        navController.navigate(it.route)
                    }
                )
            }
        }
    ) { innerPadding -> // padding == height of bottomBar
        // Big nintando switch
        NavHost(
            navController = navController,
            // Show Welcome.Page1 as start destination if it's the first time, else show GameScreen.Home
            startDestination = if (userPreferencesUiState.firstTime) WelcomeScreen.Page1.name else GameScreen.Home.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            // Welcome page1
            composable(route = WelcomeScreen.Page1.name) {
                WelcomeTheme {
                    WelcomePage1(
                        onNextButtonClicked = {
                            navController.navigate(WelcomeScreen.Page2.name)
                        }
                    )
                }
            }
            // Welcome page2
            composable(route = WelcomeScreen.Page2.name) {
                WelcomeTheme {
                    WelcomePage2(
                        onPreviousButtonClicked = {
                            navController.navigate(WelcomeScreen.Page1.name)
                        },
                        onNextButtonClicked = {
                            navController.navigate(WelcomeScreen.Page3.name)
                        }
                    )
                }
            }
            // Welcome page3
            composable(route = WelcomeScreen.Page3.name) {
                WelcomeTheme {
                    WelcomePage3(
                        onPreviousButtonClicked = {
                            navController.navigate(WelcomeScreen.Page2.name)
                        },
                        onNextButtonClicked = {
    //                        goalViewModel.setSteps(it)
                            userPreferencesViewModel.setGoal(it)
                            userPreferencesViewModel.setFirstTime(false)
                            userPreferencesViewModel.setFirstDateTime(DateTimeUtil.getCurrentDateTime())

                            navController.navigate(GameScreen.Home.name)
                        }
                    )
                }
            }
            // Home
            composable(route = GameScreen.Home.name) {
                HomeScreen(
                    homeViewModel = homeViewModel,
                    goalViewModel = goalViewModel
                )
            }
            // Stats
            composable(route = GameScreen.Stats.name) {
                AppTheme {
                    StatsScreen(stepViewModel, goalViewModel)
                }
            }
            // Help
            composable(route = GameScreen.Help.name) {
                AppTheme {
                    HelpScreen()
                }

            }
            // Setting
            composable(route = GameScreen.Setting.name) {
                AppTheme {
                    SettingScreen(userPreferencesViewModel)
                }
            }
            // Debug
            composable(route = GameScreen.Debug.name) {
                DebugScreen(homeViewModel, debugViewModel)
            }
        }
    }
}

// Bottom Navigation Bar.
@Composable
fun BottomNavigationBar(
    // Items of navigation bar.
    items: List<BottomNavigationBarItem>,
    navController: NavController,
    // Callback function used for navbar item onClick function.
    onItemClicked: (BottomNavigationBarItem) -> Unit
) {
    val backStackEntry = navController.currentBackStackEntryAsState()
    BottomNavigation(
        backgroundColor = Color.White
    ) {
        items.forEach{ item ->
            val selected = item.route == backStackEntry.value?.destination?.route
            BottomNavigationItem(
                selected = selected,
                onClick = {
                    onItemClicked(item)
                },
                selectedContentColor = Color.White,
                unselectedContentColor = Color.Gray,
                icon = {
                    Column(
                        horizontalAlignment = CenterHorizontally
                    ) {
                        Icon(
                            painter = painterResource(id = item.icon),
                            contentDescription = item.name,
                            modifier = Modifier.scale(item.scale)
                        )
                        if (selected) {
                            Text(
                                text = item.name,
                                textAlign = TextAlign.Center,
                            )
                        }
                    }
                }
            )
        }
    }
}