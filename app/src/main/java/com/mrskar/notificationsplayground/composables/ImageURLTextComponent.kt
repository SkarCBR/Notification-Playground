package com.mrskar.notificationsplayground.composables

import android.net.Uri
import android.webkit.URLUtil
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ImageURLTextComponent(
    onImageSelected: (Uri?) -> Unit
) {
    var message by remember { mutableStateOf("Image Url") }
    val isValidUrl = remember { mutableStateOf(false) }
    TextField(
        modifier = Modifier.fillMaxWidth(1f),
        value = message,
        onValueChange = {
            if (URLUtil.isValidUrl(it)) {
                onImageSelected(Uri.parse(it))
                isValidUrl.value = true
            } else {
                isValidUrl.value = false
            }
            message = it
        },
        isError = !isValidUrl.value,
        trailingIcon = {
            if (isValidUrl.value) {
                Icon(Icons.Outlined.ThumbUp, null)
            } else {
                Icon(Icons.Outlined.Warning, null)
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    ImageURLTextComponent(onImageSelected = {})
}