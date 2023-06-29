package com.example.ula_app.android.ui.game

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
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
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.example.ula_app.android.data.DataSource
import com.example.ula_app.android.ui.viewmodel.GoalViewModel
import com.example.ula_app.android.ui.viewmodel.HomeViewModel

private const val TAG = "HomeScreen"

@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = viewModel(),
    goalViewModel: GoalViewModel = viewModel()
) {

    val homeUiState by homeViewModel.uiState.collectAsState()
    val goalUiState by goalViewModel.uiState.collectAsState()

    /*
    * This exoPlayer is to play and update the video every time the user opens the home tab
    * */
    val context = LocalContext.current
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build()
    }
    exoPlayer.apply {
        setMediaItem(
            MediaItem.fromUri(
                DataSource.monsterMovies.get(homeUiState.id)?.uri!!
            )
        )
        playWhenReady = true  // automatically play the file after successfully loading media and resources
        prepare()
        if (DataSource.monsterMovies.get(homeUiState.id)?.loop!!) {
            repeatMode = Player.REPEAT_MODE_ONE
        } else {
            repeatMode = Player.REPEAT_MODE_OFF
        }

    }

    /*
    * It's a side-effect that will call the functions inside the block every time we access the home tab
    *
    * */
    LaunchedEffect(key1 = Unit, block = {
//        val currentMonsterMovie = DataSource.monsterMovies.get(homeUiState.id)

        // The currentStep should be write as a function call that use the sensor to get current step
        // Should rewrite here after we build the sensor---------------------------------------------------------------------
        homeViewModel.setAge(goalUiState.firstDateTime, 5000, goalUiState.steps)

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
                /*
                * make the currentStep as a state so that if the currentStep changes the home tab
                * will re-render and the bodyStatus will be checked through LaunchEffect.
                *
                * TODO: Link the currentStep to the sensor and make it as a state.
                * */
                homeViewModel.clickToUpdateMovies()
                homeViewModel.printGoal(goalUiState.steps)
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

/*        Text(text = "This is $TAG", fontSize = 18.sp)

        Button(
            onClick = {
                // This is to update the video file that is going to play next
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
}

