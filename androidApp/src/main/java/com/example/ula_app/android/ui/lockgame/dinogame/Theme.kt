package com.example.ula_app.android.ui.lockgame.dinogame

import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Typography
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.ula_app.android.ui.theme.DJBFirstGradeTeacherFamily
import com.example.ula_app.android.ui.theme.Shapes

val lightThemeColors = lightColors(
    primary = Color(0xFF855446),
    primaryVariant = Color(0xFF9C684B),
    secondary = Color(0xFF03DAC5),
    secondaryVariant = Color(0xFF0AC9F0),
    background = Color.White,
    surface = Color.White,
    error = Color(0xFFB00020),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black,
    onError = Color.White
)

val darkThemeColors = darkColors(
    primary = Color(0xFF1F1F1F),
    primaryVariant = Color(0xFF3E2723),
    secondary = Color(0xFF03DAC5),
    background = Color(0xFF121212),
    surface = Color.Black,
    error = Color(0xFFCF6679),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White,
    onError = Color.Black
)

val Colors.earthColor: Color
    get() = if (isLight) Color(0xFF535353) else Color(0xFFACACAC)

val Colors.cloudColor: Color
    get() = if (isLight) Color(0xFFDBDBDB) else Color(0xFFACACAC)

val Colors.dinoColor: Color
    get() = if (isLight) Color(0xFF535353) else Color(0xFFACACAC)

val Colors.cactusColor: Color
    get() = if (isLight) Color(0xFF535353) else Color(0xFFACACAC)

val Colors.gameOverColor: Color
    get() = if (isLight) Color(0xFF000000) else Color(0xFFFFFFFF)

val Colors.currentScoreColor: Color
    get() = if (isLight) Color(0xFF535353) else Color(0xFFACACAC)

val Colors.highScoreColor: Color
    get() = if (isLight) Color(0xFF757575) else Color(0xFF909191)

private val AppTypography = Typography(
    h1 = TextStyle(
        fontFamily = DJBFirstGradeTeacherFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 36.sp
    ),
    h2 = TextStyle(
        fontFamily = DJBFirstGradeTeacherFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 28.sp
    ),
    h3 = TextStyle(
        fontFamily = DJBFirstGradeTeacherFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp
    ),
    h4 = TextStyle(
        fontFamily = DJBFirstGradeTeacherFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp
    ),
    body1 = TextStyle(
        fontFamily = DJBFirstGradeTeacherFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp
    ),
    caption = TextStyle(
        fontFamily = DJBFirstGradeTeacherFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
)

@Composable
fun DinoGameTheme(content: @Composable() () -> Unit) {
    MaterialTheme(
        colors = lightThemeColors,
        typography = AppTypography,
        shapes = Shapes,
        content = content
    )
}

