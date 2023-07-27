package com.example.ula_app.android.ui.lockgame.snakegame.presentation.activity

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.lifecycleScope
import com.mukeshsolanki.snake.data.model.HighScore
import com.example.ula_app.android.ui.lockgame.snakegame.domain.base.BaseActivity
import com.example.ula_app.android.ui.lockgame.snakegame.domain.game.GameEngine
import com.example.ula_app.android.ui.lockgame.snakegame.presentation.screen.EndScreen
import com.example.ula_app.android.ui.lockgame.snakegame.presentation.screen.GameScreen
import kotlinx.coroutines.CoroutineScope

class GameActivity : BaseActivity() {
    private val isPlaying = mutableStateOf(true)
    private var score = mutableStateOf(0)
    private lateinit var scope: CoroutineScope
    private lateinit var playerName: String
    private lateinit var highScores: List<HighScore>
    private var gameEngine = GameEngine(
        scope = lifecycleScope,
        onGameEnded = {
            if (isPlaying.value) {
                isPlaying.value = false
            }
        },
        onFoodEaten = { score.value++ }
    )

    @Composable
    override fun Content() {
        scope = rememberCoroutineScope()
        playerName = "Player"
        highScores = emptyList<HighScore>()
        Column {
            if (isPlaying.value) {
                GameScreen(gameEngine, score.value)
            } else {
                EndScreen(score.value) {
                    score.value = 0
                    gameEngine.reset()
                    isPlaying.value = true
                }
            }
        }
    }
}