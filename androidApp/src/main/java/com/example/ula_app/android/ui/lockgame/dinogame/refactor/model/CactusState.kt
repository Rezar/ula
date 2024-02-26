package com.example.ula_app.android.ui.lockgame.dinogame.refactor.model

import com.example.ula_app.android.R


data class CactusState(
    var cactusId: CactusId = randomizeACactus(),
    var xOffset: Float = 0f,
    var threshold: Float = 0f,
    var counted: Boolean = false

) {

    companion object{

        fun randomizeACactus(): CactusId {
            val rnds = (0..4).random()
            return if(rnds == 0) {
                CactusId.SMALL
            } else if(rnds == 1) {
                CactusId.TWOSMALL
            }else if(rnds == 2) {
                CactusId.THREESMALL
            }else if(rnds == 3) {
                CactusId.NORMAL
            }else{
                CactusId.LARGE
            }
        }

        const val X_SPAN: Float = 10f


    }



    fun isPassTheThreshold(): Boolean {
        return xOffset <= threshold
    }

    fun move(): CactusState = copy(xOffset = xOffset - X_SPAN) // X_SPAN is the unit distance moved within a unit time range

    fun reset(targetOffset: Float): CactusState =  copy(
        cactusId = randomizeACactus(),
        xOffset = targetOffset,  // need to randomize a target offset when you reset the cactus in viewModel
        counted = false
    )

    fun count() = copy(
        counted = true
    )

    // Define the position of the cactus
    fun cactusEdge(safeZone: SafeZone): ObjectEdge {
        val cactusTopBound = safeZone.height - cactusId.height
        val cactusBottomBound = safeZone.height
        val cactusLeftBound = (safeZone.width - cactusId.width) * 0.5f + xOffset
        val cactusRightBound = (safeZone.width + cactusId.width) * 0.5f + xOffset

        return ObjectEdge(
            top = cactusTopBound,
            bottom = cactusBottomBound,
            left = cactusLeftBound,
            right = cactusRightBound
        )
    }

}

enum class CactusId {
    SMALL(R.drawable.cactus_small, 22f, 40f),
    TWOSMALL(R.drawable.cactus_twosmall, 42f, 40f),
    THREESMALL(R.drawable.cactus_threesmall, 57f, 40f),
    NORMAL(R.drawable.cactus_normal, 32f, 45f),
    LARGE(R.drawable.cactus_large, 50f, 45f);

    var id: Int = 0
    var width: Float = 0f
    var height: Float = 0f

    constructor()

    constructor(
        id: Int,
        width: Float,
        height: Float
    ) {
        this.id = id
        this.width = width
        this.height = height
    }

    // custom method
    fun customToString(): String {
        return ""
    }


}