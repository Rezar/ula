package com.example.ula_app.android.ui.lockgame.dinogame.refactor.model

data class DinoState(
    var xOffset: Float = 0f,
    var yOffset: Float = 0f,
    var width: Float = WIDTH,
    var height: Float = HEIGHT
) {

    companion object {
        // width of the bird
        const val WIDTH: Float = 11f
        // height of the bird
        const val  HEIGHT: Float = 12f
        // lifting distance per tap
        const val LIFT_Y_SPAN: Float = 18f
    }

    fun lift(safeZone: SafeZone): DinoState = copy(

    )

}