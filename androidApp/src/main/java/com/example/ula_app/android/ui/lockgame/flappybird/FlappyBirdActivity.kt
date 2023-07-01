package com.example.ula_app.android.ui.lockgame.flappybird

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

private const val TAG = "FlappyBirdActivity"

class FlappyBirdActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("${TAG}", "FlappyBird Activity is created.")

        setContent {
            FlappyBird()
        }
    }
}