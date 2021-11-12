package com.mrskar.notificationsplayground.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mrskar.notificationsplayground.ui.theme.NotificationsPlaygroundTheme

@Composable
fun BigTextMessageComponent(
    title: String,
    message: MutableState<String>,
    maxLines: Int,
) {
    Row(
        Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Text(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .fillMaxWidth(0.25f),
            text = title)
        TextField(
            modifier = Modifier.fillMaxWidth(1f),
            value = message.value,
            onValueChange = { message.value = it },
            maxLines = maxLines,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    val text = remember { mutableStateOf("This is the message") }
    NotificationsPlaygroundTheme {
        BigTextMessageComponent(
            title = "Title",
            message = text,
            maxLines = 4
        )
    }
}