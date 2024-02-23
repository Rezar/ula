package com.example.ula_app.android.ui.lockgame.dinogame.refactor.model

import com.example.ula_app.android.R

data class RoadState(
    var roadId: RoadId = RoadId.FIRST,
    var xOffset: Float = 0f,
    var threshold: Float = 0f
) {

    companion object {
        const val X_SPAN: Float = 5f
    }

    fun isPassTheThreshold(): Boolean {
        return xOffset <= threshold
    }

    fun move(): RoadState = copy(xOffset = xOffset - X_SPAN)

    fun reset(targetOffset: Float): RoadState = copy(xOffset = targetOffset)
}

enum class RoadId{
    FIRST(R.drawable.dino_road_one),
    SECOND(R.drawable.dino_road_two);

    var id: Int = 0

    constructor()

    constructor(
        id: Int
    ) {
        this.id = id
    }

    // custom method
    fun customToString(): String {
        return ""
    }
}