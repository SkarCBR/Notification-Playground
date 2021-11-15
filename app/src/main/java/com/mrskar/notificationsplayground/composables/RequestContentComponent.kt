package com.mrskar.notificationsplayground.composables

import android.net.Uri
import android.webkit.URLUtil
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter

@Composable
fun RequestContentComponent(onImageSelected: (Uri?) -> Unit) {
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val isValidUrl = remember { mutableStateOf(false) }
    var message by remember { mutableStateOf("Provide Image URL") }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        isValidUrl.value = URLUtil.isValidUrl(uri.toString())
        imageUri = uri
        message = uri.toString()
        onImageSelected(uri)
    }

    Column {
        Row(
            Modifier.fillMaxWidth()
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.Top)
                    .fillMaxWidth(0.25f),
                text = "Image:"
            )
            Column(modifier = Modifier.fillMaxWidth(1f)) {
                TextField(
                    modifier = Modifier.fillMaxWidth(1f),
                    value = message,
                    onValueChange = {
                        if (URLUtil.isValidUrl(it)) {
                            imageUri = Uri.parse(it)
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
                Text(
                    text = "or",
                    modifier = Modifier.align(CenterHorizontally)
                )
                Button(
                    onClick = { launcher.launch("image/*") },
                    modifier = Modifier.align(CenterHorizontally)
                ) {
                    Text(text = "Pick image")
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        imageUri?.let {
            Image(
                painter = rememberImagePainter(it),
                contentDescription = null,
                modifier = Modifier.aspectRatio(2.3f),
                contentScale = ContentScale.Crop
            )
        }
    }
}