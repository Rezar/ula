package com.example.ula_app.android.ui.lockgame.snakegame.refactor.model

import androidx.compose.runtime.Composable

data class Coordinate(
    val x: Int,
    val y: Int
) {

    companion object {
        fun randomCoordinate(): Coordinate {

            // TODO: need to add if to make sure that the randomized food position does not overlap with the snake

            return Coordinate(7, 7)
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this == other) return true
        if (other !is Coordinate) return false

        return this.x == other.x && this.y == other.y
    }


}