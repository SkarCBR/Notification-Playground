package com.mrskar.notificationsplayground.composables

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mrskar.notificationsplayground.models.NotificationData
import com.mrskar.notificationsplayground.models.NotificationStyles

@Composable
fun NotificationDataComponent(
    notificationData: NotificationData
) {
    Card(elevation = 4.dp) {
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
                    maxLines = 4
                )
            } else {
                MultiLineMessageComponent(
                    title = "Text ",
                    multiMessage = notificationData.multiMessage,
                    maxLines = 2
                )
            }
            BigTextMessageComponent(
                title = "Deeplink: ",
                message = notificationData.url,
                maxLines = 1
            )
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
            Row(
                Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
            ) {
                Text(text = "$notificationData")
            }
        }
    }
}