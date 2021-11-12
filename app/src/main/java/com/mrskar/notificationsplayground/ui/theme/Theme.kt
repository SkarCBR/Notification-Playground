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
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.tooling.preview.Preview
import com.mrskar.notificationsplayground.composables.NotificationsTestComponent

private val DarkColorPalette = darkColors(
    primary = DarkGrey,
    primaryVariant = Grey,
    onPrimary = White,
    secondary = BrandBlue,
    secondaryVariant = BrandBlue,
    onSecondary = White,
    error = RedTomato,
    onError = White,
    onBackground = White,
    background = Black,
    surface = DarkGrey,
    onSurface = White,
)

private val LightColorPalette = lightColors(
    primary = White,
    primaryVariant = White,
    onPrimary = BrandBlack,
    secondary = BrandBlue,
    secondaryVariant = BrandBlue,
    onSecondary = White,
    error = RedTomato,
    onError = White,
    onBackground = BrandBlack,
    background = Pearl,
    surface = White,
    onSurface = BrandBlack,
)

@Composable
fun NotificationsPlaygroundTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
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
            NotificationsTestComponent { }
        }
    }
}