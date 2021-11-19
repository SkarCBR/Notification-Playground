package com.mrskar.notificationsplayground.composables

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter

@Composable
fun RequestContentComponent(onImageSelected: (Uri?) -> Unit) {
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
        onImageSelected(uri)
    }

    Column {
        Row(
            Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .fillMaxWidth(0.25f),
                text = "Image:"
            )
            Column(modifier = Modifier.fillMaxWidth(1f)) {
                Button(
                    onClick = { launcher.launch("image/*") },
                    modifier = Modifier.align(CenterHorizontally)
                ) {
                    Text(text = "Pick image")
                }
            }
        }

        imageUri?.let {
            val painter = rememberImagePainter(it)
            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier.aspectRatio(2.3f),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    RequestContentComponent(onImageSelected = {})
}