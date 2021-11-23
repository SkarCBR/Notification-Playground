package com.mrskar.notificationsplayground.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.darkColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.mrskar.notificationsplayground.composables.NotificationsTestComponent

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
    val (enableDarkMode, setDarkMode) = remember { mutableStateOf(true) }
    var changeThemeIcon = Icons.Filled.FavoriteBorder
    NotificationsPlaygroundTheme(enableDarkMode) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = { Text("Notifications Playground") },
                    backgroundColor = MaterialTheme.colors.secondary,
                    actions = {
                        IconButton(
                            onClick = {
                                setDarkMode(!enableDarkMode)
                                changeThemeIcon = Icons.Filled.Notifications
                            }
                        ) {
                            Icon(changeThemeIcon, null)
                        }
                    }
                )
            }
        ) {
            NotificationsTestComponent(deleteCount = 0) { }
        }
    }
}