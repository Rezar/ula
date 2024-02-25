package com.example.ula_app.android.ui.lockgame.dinogame.refactor.model

data class DinoState(
    var xOffset: Float = 0f,
    var yOffset: Float = 0f,
    var width: Float = WIDTH,
    var height: Float = HEIGHT,

    // the current time unit index during the jump and fall
    var jumpIndex: Int = 0,
    // whether the dino is jumping/falling
    // or whether the jumpList's index is back to the index 0, where the dino
    // is ready for another jump
    val isJump: Boolean = false

) {

    companion object {
        // width of the bird
        const val WIDTH: Float = 11f
        // height of the bird
        const val  HEIGHT: Float = 12f
        // lifting distance per tap
        const val LIFT_Y_SPAN: Float = 18f

        const val leftMargin: Float =  11f

        // a list of jump/fall distance per time unit
        val jumpList: List<Float> = listOf(9f, 9f, -9f, -9f)
    }

    // param: the height per jump
    fun handleJump(): DinoState {

        // if the user does not tap, dino does nothing (running on the ground
        if(!isJump) {
            return copy()
        }

        if(jumpIndex == jumpList.size - 1) {
            return copy(
                yOffset = yOffset - jumpList[jumpIndex],
                jumpIndex = 0,
                isJump = false
            )
        }
        return copy(
            yOffset = yOffset - jumpList[jumpIndex],
            jumpIndex = jumpIndex + 1
        )
    }


    fun startJump(): DinoState = copy(
        isJump = true
    )

    fun dinoEdge(safeZone: SafeZone): ObjectEdge {
        val dinoTopBound = safeZone.height * 0.5f + yOffset - height * 0.5f
        val dinoBottomBound = safeZone.height * 0.5f + yOffset + height * 0.5f
        val dinoLeftBound = 11f
        val dinoRightBound = 11f + width

        return ObjectEdge(
            top = dinoTopBound,
            bottom = dinoBottomBound,
            left = dinoLeftBound,
            right = dinoRightBound
        )
    }

}