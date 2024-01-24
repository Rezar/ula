package com.example.ula_app.android.ui.lockgame.dinogame

import android.app.Activity
import android.util.Log
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ula_app.android.R
import com.example.ula_app.android.ui.lockgame.dinogame.model.CactusState
import com.example.ula_app.android.ui.lockgame.dinogame.model.CloudState
import com.example.ula_app.android.ui.lockgame.dinogame.model.DinoState
import com.example.ula_app.android.ui.lockgame.dinogame.model.EarthState
import com.example.ula_app.android.ui.lockgame.dinogame.model.GameState

const val EARTH_Y_POSITION = 480f
private const val EARTH_GROUND_STROKE_WIDTH = 10f
private const val CLOUDS_SPEED = 1 // pixels per frame
private const val MAX_CLOUDS = 3
const val EARTH_OFFSET = 200
const val EARTH_SPEED = 5

var deviceWidthInPixels = 1920
var distanceBetweenCactus = 100

var showBounds = mutableStateOf(false)

@Preview
@Composable
fun DinoGameScenePreview() {
    DinoGameTheme {
        DinoGameScene(GameState())
    }
}

@Composable
fun DinoGameScene(gameState: GameState) {
    val cloudsState by remember { mutableStateOf(CloudState(maxClouds = MAX_CLOUDS, speed = CLOUDS_SPEED)) }
    val earthState by remember { mutableStateOf(EarthState(maxBlocks = 2, speed = EARTH_SPEED)) }
    val cactusState by remember { mutableStateOf(CactusState(cactusSpeed = EARTH_SPEED)) }
    val dinoState by remember { mutableStateOf(DinoState()) }
    val currentScore by gameState.currentScore.observeAsState()
    val highScore by gameState.highScore.observeAsState()

    val earthColor = MaterialTheme.colors.earthColor
    val cloudsColor = MaterialTheme.colors.cloudColor
    val dinoColor = MaterialTheme.colors.dinoColor
    val cactusColor = MaterialTheme.colors.cactusColor

    val activity = LocalContext.current as? Activity

    if (!gameState.isGameOver)
    {
        // Game Loop
        gameState.increaseScore()
        cloudsState.moveForward()
        earthState.moveForward()
        cactusState.moveForward()
        dinoState.move()

        // Collision Check
        cactusState.cactusList.forEach {
            if (dinoState.getBounds()
                    .deflate(DOUBT_FACTOR)
                    .overlaps(
                        it.getBounds()
                            .deflate(DOUBT_FACTOR)
                    )
            ) {
                gameState.isGameOver = true
                return@forEach
            }
        }
    }

    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
            .clickable(
                onClick = {
                    if (!gameState.isGameOver)
                        dinoState.jump()
                    else {
                        cactusState.initCactus()
                        dinoState.init()
                        gameState.replay()
                    }
                }
            )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = {
                    activity?.finish()
                },
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.White, contentColor = Color.Black)
            ) {
                Text(
                    text = "Back",
                    style = MaterialTheme.typography.caption
                )
            }
        }
//        ShowBoundsSwitchView()
        HighScoreTextViews(requireNotNull(currentScore), requireNotNull(highScore))
        Canvas(modifier = Modifier.weight(1f)) {
            EarthView(earthState, color = earthColor)
            CloudsView(cloudsState, color = cloudsColor)
            DinoView(dinoState, color = dinoColor)
            CactusView(cactusState, color = cactusColor)
        }
    }
    GameOverTextView(
        gameState.isGameOver,
        modifier = Modifier
            .padding(top = 150.dp)
            .fillMaxWidth()
    )

}

fun DrawScope.DinoView(dinoState: DinoState, color: Color) {
    withTransform({
        translate(
            left = dinoState.xPos,
            top = dinoState.yPos - dinoState.path.getBounds().height
        )
    }) {
        Log.w("Dino", "$dinoState.keyframe")
        drawPath(
            path = dinoState.path,
            color = color,
            style = Fill
        )
        drawBoundingBox(color = Color.Green, rect = dinoState.path.getBounds())
    }
}

fun DrawScope.CloudsView(cloudState: CloudState, color: Color)
{
    cloudState.cloudsList.forEach { cloud ->
        withTransform({
            translate(
                left = cloud.xPos.toFloat(),
                top = cloud.yPos.toFloat()
            )
        })
        {
            drawPath(
                path = cloudState.cloudsList.first().path,
                color = color,
                style = Stroke(2f)
            )

            drawBoundingBox(color = Color.Blue, rect = cloud.path.getBounds())
        }
    }
}

fun DrawScope.EarthView(earthState: EarthState, color: Color)
{
    // Ground Line
    drawLine(
        color = color,
        start = Offset(x = 0f, y = EARTH_Y_POSITION),
        end = Offset(x = deviceWidthInPixels.toFloat(), y = EARTH_Y_POSITION),
        strokeWidth = EARTH_GROUND_STROKE_WIDTH
    )
    earthState.blocksList.forEach { block ->
        drawLine(
            color = color,
            start = Offset(x = block.xPos, y = EARTH_Y_POSITION + 20),
            end = Offset(x = block.size, y = EARTH_Y_POSITION + 20),
            strokeWidth = EARTH_GROUND_STROKE_WIDTH / 5,
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(20f, 40f), 0f)
        )
        drawLine(
            color = color,
            start = Offset(x = block.xPos, y = EARTH_Y_POSITION + 30),
            end = Offset(x = block.size, y = EARTH_Y_POSITION + 30),
            strokeWidth = EARTH_GROUND_STROKE_WIDTH / 5,
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(15f, 50f), 40f)
        )
    }
}

fun DrawScope.CactusView(cactusState: CactusState, color: Color)
{
    cactusState.cactusList.forEach { cactus ->
        withTransform({
            scale(cactus.scale, cactus.scale)
            translate(
                left = cactus.xPos.toFloat(),
                top = cactus.getBounds().top * cactus.scale
            )
        })
        {
            drawPath(
                path = cactus.path,
                color = color,
                style = Fill
            )
            drawBoundingBox(color = Color.Red, rect = cactus.path.getBounds())
        }
    }
}

@Composable
fun HighScoreTextViews(currentScore: Int, highScore: Int)
{
    Spacer(modifier = Modifier.padding(top = 50.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 20.dp),
        horizontalArrangement = Arrangement.End
    ) {
        Text(
            text = "HI",
            color = MaterialTheme.colors.highScoreColor,
            style = MaterialTheme.typography.caption
        )
        Spacer(modifier = Modifier.padding(start = 10.dp))
        Text(
            text = "$highScore".padStart(5, '0'),
            color = MaterialTheme.colors.highScoreColor,
            style = MaterialTheme.typography.caption
        )
        Spacer(modifier = Modifier.padding(start = 10.dp))
        Text(
            text = "$currentScore".padStart(5, '0'),
            color = MaterialTheme.colors.currentScoreColor,
            style = MaterialTheme.typography.caption
        )
    }
}

@Composable
fun ShowBoundsSwitchView()
{
    Spacer(modifier = Modifier.padding(top = 20.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(end = 20.dp),
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = "Show Bounds", style = MaterialTheme.typography.caption)
        Spacer(modifier = Modifier.padding(start = 10.dp))
        Switch(checked = showBounds.value, onCheckedChange = {
            showBounds.value = it
        })
    }
}

@Composable
fun GameOverTextView(isGameOver: Boolean = true, modifier: Modifier = Modifier)
{
    Column(modifier = modifier) {
        Text(
            text = if (isGameOver) "GAME OVER" else "",
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            letterSpacing = 5.sp,
            style = MaterialTheme.typography.caption
        )
        if (isGameOver) {
            Icon(
                painter = painterResource(id = R.drawable.ic_replay),
                contentDescription = null, // decorative element
                modifier = Modifier
                    .size(40.dp)
                    .padding(top = 10.dp)
                    .align(alignment = Alignment.CenterHorizontally)
            )
        }
    }
}


fun DrawScope.drawBoundingBox(color: Color, rect: Rect, name: String? = null) {
    name?.let { Log.w("drawBounds", "$name $rect") }
    if (showBounds.value)
    {
        drawRect(color, rect.topLeft, rect.size, style = Stroke(3f))
        drawRect(
            color,
            rect.deflate(DOUBT_FACTOR).topLeft,
            rect.deflate(DOUBT_FACTOR).size,
            style = Stroke(
                width = 3f,
                pathEffect = PathEffect.dashPathEffect(floatArrayOf(2f, 4f), 0f)
            )
        )
    }
}

fun Rect.collided(other: Rect, doubtFactor: Float = 0f): Boolean {
    if (right >= (other.left + doubtFactor) && right <= (other.right - doubtFactor))
        return true
    if (right <= other.left || other.right <= left)
        return false
    if (bottom <= other.top || other.bottom <= top)
        return false
    return false
}