package com.example.ula_app.android.ui.lockgame.flappybird.model

data class PipeState(
    var direction: PipeDirection = randomPipeDirection(),
    var pillarHeight: Float = randomPillarHeight(),
    var xOffset: Float = 0f,
    var threshold: Float = 0f,
    var counted: Boolean = false
) {
    companion object {
        // Width of the top pipe.
        const val TOP_WIDTH: Float = 60f
        // Height of the top pipe.
        const val TOP_HEIGHT: Float = 30f
        // Width if the pipe pillar.
        const val PILLAR_WIDTH: Float = 50f

        private fun randomPipeDirection(): PipeDirection {
            return if (Math.random() <= 0.5) PipeDirection.Up else PipeDirection.Down
        }

        private fun randomPillarHeight(): Float {
            val upperBound = 500f
            val lowerBound = 150f

            return lowerBound + Math.random().toFloat() * (upperBound - lowerBound)
        }
    }

    private val X_SPAN: Float = 5f

    fun isPassTheThreshold(): Boolean {
        return xOffset <= threshold
    }

    fun move(): PipeState = copy(xOffset = xOffset - X_SPAN)

    fun reset(targetOffset: Float): PipeState = copy(
        direction = randomPipeDirection(),
        pillarHeight = randomPillarHeight(),
        xOffset = targetOffset,
        counted = false
    )

    fun count() = copy(
        counted = true
    )

    fun pipeEdge(safeZone: SafeZone): ObjectEdge {
        // calculate top, bottom, left, right bound of this pipe.
        when (direction) {
            PipeDirection.Up -> {
                val pipeTopBound = safeZone.height - pillarHeight - TOP_HEIGHT
                val pipeBottomBound = safeZone.height
                val pipeLeftBound = (safeZone.width - PILLAR_WIDTH) * 0.5f + xOffset
                val pipeRightBound = (safeZone.width + PILLAR_WIDTH) * 0.5f + xOffset

                return ObjectEdge(
                    top = pipeTopBound,
                    bottom = pipeBottomBound,
                    left = pipeLeftBound,
                    right = pipeRightBound
                )
            }
            PipeDirection.Down -> {
                val pipeTopBound = 0f
                val pipeBottomBound = pillarHeight + TOP_HEIGHT
                val pipeLeftBound = (safeZone.width - PILLAR_WIDTH) * 0.5f + xOffset
                val pipeRightBound = (safeZone.width + PILLAR_WIDTH) * 0.5f + xOffset

                return ObjectEdge(
                    top = pipeTopBound,
                    bottom = pipeBottomBound,
                    left = pipeLeftBound,
                    right = pipeRightBound
                )
            }
        }
    }
}

enum class PipeDirection {
    Up,
    Down
}