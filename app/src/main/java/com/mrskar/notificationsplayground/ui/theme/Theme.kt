package com.mrskar.notificationsplayground.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

private val LightThemeColors = lightColors(
    primary = BrandBlue,
    onPrimary = White,
    primaryVariant = LightGrey,
    secondary = BrandBlue,
    onSecondary = White,
    secondaryVariant = LightGrey,
    error = RedTomato,
    onError = White,
    background = Pearl,
    onBackground = BrandBlack,
    surface = Pearl,
    onSurface = BrandBlack,
)
private val DarkThemeColors = darkColors(
    primary = Color(0xFFbbc3ff),
    onPrimary = Color(0xFF00159e),
    primaryVariant = Color(0xFF0022db),
    secondary = Color(0xFFbbc3ff),
    onSecondary = Color(0xFF00159e),
    secondaryVariant = Color(0xFF0022db),
    error = Color(0xFFffb4a9),
    onError = Color(0xFF680003),
    background = BrandBlack,
    onBackground = LightGrey,
    surface = BrandBlack,
    onSurface = LightGrey,
)

@Composable
fun NotificationsPlaygroundTheme(
    darkTheme: Boolean = false,
    content: @Composable() () -> Unit
) {
    val colors = if (darkTheme) {
        DarkThemeColors
    } else {
        LightThemeColors
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}

@Preview
@Composable
private fun DefaultPreview() {
}