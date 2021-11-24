package com.mrskar.notificationsplayground.composables

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mrskar.notificationsplayground.models.NotificationData
import com.mrskar.notificationsplayground.models.NotificationStyles
import com.mrskar.notificationsplayground.models.NotificationTypes
import com.mrskar.notificationsplayground.ui.theme.LightGrey

@Composable
fun NotificationDataComponent(
    notificationData: NotificationData
) {
    Card(elevation = 4.dp) {
        Column {
            Column(modifier = Modifier.padding(8.dp)) {
                Text(text = "Notification Data: ")
                BigTextMessageComponent(
                    title = "Title: ",
                    message = notificationData.title,
                    maxLines = 1
                )
                if (notificationData.multiMessage.isEmpty()) {
                    BigTextMessageComponent(
                        title = "Message: ",
                        message = notificationData.message,
                        maxLines = 6
                    )
                } else {
                    MultiLineMessageComponent(
                        title = "Text ",
                        multiMessage = notificationData.multiMessage,
                        maxLines = 2
                    )
                }
                if (notificationData.type.value == NotificationTypes.DEEPLINK) {
                    if (notificationData.url.value.isEmpty()) {
                        notificationData.url.value = "https://despamers.com/"
                    }
                    BigTextMessageComponent(
                        title = "Deeplink: ",
                        message = notificationData.url,
                        maxLines = 1
                    )
                } else {
                    notificationData.url.value = ""
                }
                if (notificationData.style.value == NotificationStyles.BIG_PICTURE) {
                    Row(
                        Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                    ) {
                        RequestContentComponent(onImageSelected = {
                            if (it != null) {
                                notificationData.imageUri.value = it
                            }
                        })
                    }
                } else {
                    notificationData.imageUri.value = Uri.EMPTY
                }
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .background(LightGrey)
                    .height(4.dp)
            )
            Column(
                Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text(text = "$notificationData")
                Text(
                    text = "Intent arguments:\n" +
                        " - comingFromNotification = true\n" +
                        " - section = ${notificationData.url.value.substringAfter(".com/", "")}"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    val notificationData = remember {
        NotificationData(
            mutableStateOf("Test title"),
            mutableStateOf("Test message"),
            mutableStateListOf<MutableState<String>>(),
            mutableStateOf(NotificationStyles.BIG_TEXT),
            mutableStateOf(NotificationTypes.STANDARD),
            mutableStateOf("https://despamers.com/"),
            mutableStateOf(Uri.EMPTY),
            mutableStateOf(true)
        )
    }
    NotificationDataComponent(notificationData)
}