package com.mrskar.notificationsplayground.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.tooling.preview.Preview
import com.mrskar.notificationsplayground.ui.theme.NotificationsPlaygroundTheme

@Composable
fun MultiLineMessageComponent(
    title: String,
    multiMessage: SnapshotStateList<MutableState<String>>,
    maxLines: Int,
) {
    if (multiMessage.isNotEmpty()) {
        Column {
            BigTextMessageComponent(
                title = "$title 1: ",
                message = multiMessage[0],
                maxLines = maxLines
            )
            BigTextMessageComponent(
                title = "$title 2: ",
                message = multiMessage[1],
                maxLines = maxLines
            )
            BigTextMessageComponent(
                title = "$title 3: ",
                message = multiMessage[2],
                maxLines = maxLines
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    val text = remember { mutableStateListOf(
        mutableStateOf("This is the message 1"),
        mutableStateOf("This is the message 2"),
        mutableStateOf("This is the message 3")
    ) }
    NotificationsPlaygroundTheme {
        MultiLineMessageComponent(
            title = "Title",
            multiMessage = text,
            maxLines = 4
        )
    }
}