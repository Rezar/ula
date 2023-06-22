package com.example.ula_app.android.component

import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun IconButton(
    enabled: Boolean = true,
    backgroundColor: Color = Color.Transparent,
    Icon: @Composable () -> Unit,
    onClick: () -> Unit = {}
) {
    Button(
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor
        ),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp,
            pressedElevation = 0.dp,
            disabledElevation = 0.dp,
            hoveredElevation = 0.dp,
            focusedElevation = 0.dp,
        ),
        onClick = { onClick() }
    ) {
        Icon()
    }
}