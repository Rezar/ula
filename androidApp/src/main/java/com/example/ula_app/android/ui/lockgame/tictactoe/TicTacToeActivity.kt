package com.example.ula_app.android.ui.lockgame.tictactoe

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

private const val TAG = "TicTacToeActivity"

class TicTacToeActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("${TAG}", "TicTacToe Activity is created.")
        setContent {
            TicTacToe()
        }
    }

}