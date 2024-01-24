package com.example.ula_app.android.ui.lockgame.dinogame

import android.os.Bundle
import android.util.DisplayMetrics
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ula_app.android.ui.lockgame.dinogame.model.GameState

class DinoGameActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var deviceMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(deviceMetrics)
        deviceWidthInPixels = deviceMetrics.widthPixels
        distanceBetweenCactus = (deviceWidthInPixels * 0.4f).toInt()

        setContent {
            DinoGameTheme {
                DinoGameScene(GameState())
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
    Spacer(modifier = Modifier.padding(start = 20.dp))
}

@Preview
@Composable
fun DefaultPreview() {
    DinoGameTheme {
        Greeting("Android")
    }
}