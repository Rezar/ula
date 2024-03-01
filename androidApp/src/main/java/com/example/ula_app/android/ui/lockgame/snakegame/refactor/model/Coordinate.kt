package com.example.ula_app.android.ui.lockgame.snakegame.refactor.model

import androidx.compose.runtime.Composable
import kotlin.random.Random

data class Coordinate(
    val x: Int,
    val y: Int
) {

    companion object {
        fun randomCoordinate(snakeStateList: List<Coordinate>): Coordinate {

            val maxX = 31
            val maxY = 31
            var newX: Int
            var newY:Int
            var isOverlapping: Boolean

            do {
                newX = Random.nextInt(maxX)
                newY = Random.nextInt(maxY)

                isOverlapping = snakeStateList.any { it.x == newX && it.y == newY}
            } while (isOverlapping)

            return Coordinate(newX, newY)
        }
    }


}