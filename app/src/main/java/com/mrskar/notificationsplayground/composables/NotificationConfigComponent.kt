package com.mrskar.notificationsplayground.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mrskar.notificationsplayground.models.NotificationData
import com.mrskar.notificationsplayground.models.NotificationStyles

@Composable
fun NotificationConfigComponent(
    deleteCount: Int,
    notificationData: NotificationData
) {
    Card(elevation = 4.dp) {
        Column(modifier = Modifier.padding(8.dp)) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Notification Type: ",
                    modifier = Modifier.align(Alignment.CenterVertically))
                DropdownPushType(onItemSelected = {
                    notificationData.type.value = it
                })
            }
            Row(Modifier.fillMaxWidth()) {
                Text(
                    text = "Notification Style: ",
                    modifier = Modifier.align(Alignment.CenterVertically))
                DropdownPushStyle(onItemSelected = {
                    notificationData.style.value = it
                    if (it == NotificationStyles.INBOX) {
                        notificationData.multiMessage.apply {
                            add(mutableStateOf("First message in a multi message notification"))
                            add(
                                mutableStateOf(
                                    "This is a long message that will probably get truncated"
                                )
                            )
                            add(mutableStateOf("This is a short message"))
                        }
                    } else {
                        notificationData.multiMessage.clear()
                    }
                })
            }
            Row(Modifier.fillMaxWidth()) {
                Text(
                    text = if (notificationData.expanded.value) {
                        "High Priority Channel"
                    } else {
                        "Low Priority Channel"
                    },
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                Checkbox(
                    checked = notificationData.expanded.value,
                    onCheckedChange = {
                        notificationData.expanded.value = !notificationData.expanded.value
                    }
                )
            }
            Text(text = "Total Notifications Deleted: $deleteCount")
        }
    }
}