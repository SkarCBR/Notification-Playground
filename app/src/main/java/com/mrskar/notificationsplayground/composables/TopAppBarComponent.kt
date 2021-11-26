package com.mrskar.notificationsplayground.composables

import android.os.Build
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@Composable
fun TopAppBarComponent(
    onDarkModeChange: () -> Unit,
    onConfigSelected: () -> Unit,
) {
    val icon = remember { mutableStateOf(Icons.Filled.FavoriteBorder) }
    TopAppBar(
        title = { Text("Notifications Playground") },
        backgroundColor = MaterialTheme.colors.secondary,
        actions = {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                IconButton(
                    onClick = onConfigSelected
                ) {
                    Icon(Icons.Filled.Settings, null)
                }
            }
            IconButton(
                onClick = {
                    onDarkModeChange()
                    if (icon.value == Icons.Filled.FavoriteBorder) {
                        icon.value = Icons.Filled.Favorite
                    } else {
                        icon.value = Icons.Filled.FavoriteBorder
                    }
                }
            ) {
                Icon(icon.value, null)
            }
        }
    )
}