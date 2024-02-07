package com.example.ula_app.android.ui.game

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.example.ula_app.android.ULAApplication
import com.example.ula_app.android.ui.lockgame.tictactoe.TicTacToeActivity
import com.example.ula_app.android.data.DataSource
import com.example.ula_app.android.ui.viewmodel.UserPreferencesViewModel
import com.mutualmobile.composesensors.rememberStepCounterSensorState
import com.example.ula_app.android.ui.lockgame.dinogame.DinoGameActivity
import com.example.ula_app.android.ui.lockgame.snakegame.presentation.activity.SnakeGameActivity
import com.example.ula_app.android.ui.lockgame.flappybird.refactored.FlappyBirdActivity
import com.example.ula_app.android.ui.viewmodel.AndroidHomeViewModel
import com.example.ula_app.presentation.UiMonsterMovie

private const val TAG = "HomeScreen"

@Composable
fun HomeScreen() {
    val context = LocalContext.current
    val homeViewModel = ULAApplication.getInstance<AndroidHomeViewModel>()
    val userPreferencesViewModel = ULAApplication.getInstance<UserPreferencesViewModel>()


    val homeUiState by homeViewModel.uiState.collectAsState()
    val userPreUiState by userPreferencesViewModel.userPreferencesState.collectAsState()

    val stepSensor = rememberStepCounterSensorState()

    /*
    * This exoPlayer is to play and update the video every time the user opens the home tab
    * */
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build()
    }
    exoPlayer.apply {
        setMediaItem(
            MediaItem.fromUri(
                Uri.parse(DataSource.androidResourcePath + UiMonsterMovie.allMonsterMovie.get(homeUiState.id)?.uri!!)
            )
        )
        playWhenReady = true  // automatically play the file after successfully loading media and resources
        prepare()

        // 1. get UiMonsterMovie class from androidMain
        // 2. get allMonsterMovie (which is a map) in UiMonsterMovie class
        // 3. search the movie by id
        // 4. get commonMonsterMovie in allMonsterMovie
        // 5. get the loop field inside the monsterMovie class in commonMonsterMovie
        if (UiMonsterMovie
                .allMonsterMovie.get(homeUiState.id)?.commonMonsterMovie?.monsterMovie?.loop!!) {
            repeatMode = Player.REPEAT_MODE_ONE
        } else {
            repeatMode = Player.REPEAT_MODE_OFF
        }

    }

    /*
    * It's a side-effect that will call the functions inside the block every time we access the home tab.
    *
    * TODO: After building the sensor and making it as a state, change the key to the "currentStep" instead of "unit"
    * */
    LaunchedEffect(key1 = stepSensor.stepCount, block = {

        // The currentStep should be write as a function call that use the sensor to get current step
        /*
        * make the currentStep as a state so that if the currentStep changes the home tab
        * will re-render and the bodyStatus will be checked through LaunchEffect.
        * */
        // TODO: Hard code to use daily goal, need more flexible method.
        homeViewModel.setAge(
            userPreUiState.firstDateTime.epochSeconds,
            stepSensor.stepCount.toInt(),
            userPreUiState.dailyGoal,
            userPreUiState.maxThreshold,
            userPreUiState.minThreshold
        )

//        if( currentMonsterMovie?.age == "Child") {
//            homeViewModel.setChildBodyStatus(5000, goalUiState.steps)
//        } else if (currentMonsterMovie?.age == "Adult") {
//            homeViewModel.setAdultBodyStatus(5000, goalUiState.steps)
//        }

        // show message when the movie is locked
        homeViewModel
            .toastMessage
            .collect { message ->
                Toast.makeText(
                    context,
                    message,
                    Toast.LENGTH_LONG,
                ).show()
            }
    })

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.clickable {
                // This is to update the video file that is going to play next
                homeViewModel.clickToUpdateMovies()
//                homeViewModel.printGoal(goalUiState.steps)
            }
        ) {
            DisposableEffect(key1 = Unit) { onDispose { exoPlayer.release() } }

            AndroidView(
                factory = {
                    PlayerView(context).apply {
                        player = exoPlayer
                        useController = false
                        /*layoutParams =
                            FrameLayout.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT
                            )*/
                    }
                }
            )
        }
        Text(
            text = homeUiState.id
        )

        if(homeUiState.openDialog) {
            AlertDialog(
                onDismissRequest  = {homeViewModel.setOpenDialog(false)},
                title = {
                    Text(
                        text = "Want to play a little game? ",
                        style = MaterialTheme.typography.body1
                    )
                },

                buttons = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(
                            onClick = {
                                homeViewModel.setOpenDialog(false)
                                context.startActivity(Intent(context, TicTacToeActivity::class.java))
                            },
                            colors = ButtonDefaults.outlinedButtonColors(backgroundColor = Color.White, contentColor = Color.Black),
                        ) {
                            Text(
                                text = "Tic Tac Toe",
                                style = MaterialTheme.typography.caption
                            )
                        }
                        Button(
                            onClick = {
                                homeViewModel.setOpenDialog(false)
                                context.startActivity(Intent(context, DinoGameActivity::class.java))
                            },
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White, contentColor = Color.Black)
                        ) {
                            Text("Dino Game",
                                style = MaterialTheme.typography.caption)
                        }
                        Button(
                            onClick = {
                                homeViewModel.setOpenDialog(false)
                                context.startActivity(Intent(context, SnakeGameActivity::class.java))
                            },
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White, contentColor = Color.Black)
                        ) {
                            Text("Snake Game",
                                style = MaterialTheme.typography.caption)
                        }
                        Button(
                            onClick = {
                                homeViewModel.setOpenDialog(false)
                                context.startActivity(Intent(context, FlappyBirdActivity::class.java))
                            },
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White, contentColor = Color.Black)
                        ) {
                            Text("Flappy Bird",
                                style = MaterialTheme.typography.caption)
                        }
                        Button(
                            onClick = {
                                homeViewModel.setOpenDialog(false)

                            },
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color.White, contentColor = Color.Black)
                        ) {
                            Text("Nah, I am good...",
                                style = MaterialTheme.typography.caption)
                        }
                    }
                }



            )
        }


/*        AlertDialog(
            onDismissRequest  = {homeViewModel.setOpenDialog(false)},
            title = {
                Text(text = "Want to play a little game? ")
            },
            confirmButton = {
                Button(
                    onClick = {
                        homeViewModel.setOpenDialog(false)
                        context.startActivity(Intent(context, TicTacToeActivity::class.java))
                    }
                ) {
                    Text("Tic Tac Toe")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        homeViewModel.setOpenDialog(false)
                    }
                ) {
                        Text("Nah, I am good...")
                }
            }
        )*/
    }


            /*Button(
            onClick = {
                // This is to update the video file that is going to play next
                // --------------------------------Should rewrite here after we build the sensor---------------------------------------------------------------------
                homeViewModel.setAge(goalUiState.firstDateTime, 5000, goalUiState.steps)
                homeViewModel.clickToUpdateMovies()
                homeViewModel.printGoal(goalUiState.steps)
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color.LightGray
            )
        ) {
            Text(
                text = "Tap Me"
            )
        }*/




}

