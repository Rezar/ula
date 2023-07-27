package com.example.ula_app.android.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

private val LightColorPalette = lightColors(
    primary = Color.Black,
    secondary = Color.DarkGray
)

private val WelcomeTypography = Typography(
    h1 = TextStyle(
        fontFamily = DJBFirstGradeTeacherFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 36.sp,
        color = Color.Red
    ),
    body1 = TextStyle(
        fontFamily = DJBFirstGradeTeacherFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp
    ),
    caption = TextStyle(
        fontFamily = DJBFirstGradeTeacherFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
)

private val AppTypography = Typography(
    h1 = TextStyle(
        fontFamily = DJBFirstGradeTeacherFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 36.sp
    ),
    h4 = TextStyle(
        fontFamily = DJBFirstGradeTeacherFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp
    ),
    body1 = TextStyle(
        fontFamily = DJBFirstGradeTeacherFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp
    ),
    caption = TextStyle(
        fontFamily = DJBFirstGradeTeacherFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
)

@Composable
fun WelcomeTheme(content: @Composable() () -> Unit) {
    MaterialTheme(
        colors = LightColorPalette,
        typography = WelcomeTypography,
        shapes = Shapes,
        content = content
    )
}

@Composable
fun AppTheme(content: @Composable() () -> Unit) {
    MaterialTheme(
        colors = LightColorPalette,
        typography = AppTypography,
        shapes = Shapes,
        content = content
    )
}