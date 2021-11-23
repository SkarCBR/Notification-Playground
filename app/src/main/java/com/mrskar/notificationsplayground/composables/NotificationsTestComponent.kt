package com.mrskar.notificationsplayground.composables

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mrskar.notificationsplayground.models.NotificationData
import com.mrskar.notificationsplayground.models.NotificationStyles
import com.mrskar.notificationsplayground.models.NotificationTypes
import com.mrskar.notificationsplayground.ui.theme.NotificationsPlaygroundTheme

@Composable
fun NotificationsTestComponent(
    deleteCount: Int,
    onButtonClick: (data: NotificationData) -> Unit
) {
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

    Column(
        Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
            .fillMaxHeight()
    ) {
        Card(elevation = 4.dp) {
            Column(modifier = Modifier.padding(8.dp)) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Notification Type: ", modifier = Modifier.align(CenterVertically))
                    DropdownPushType(onItemSelected = {
                        notificationData.type.value = it
                    })
                }
                Row(Modifier.fillMaxWidth()) {
                    Text(text = "Notification Style: ", modifier = Modifier.align(CenterVertically))
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
                        modifier = Modifier.align(CenterVertically)
                    )
                    Checkbox(
                        checked = notificationData.expanded.value,
                        onCheckedChange = {
                            notificationData.expanded.value = !notificationData.expanded.value
                        }
                    )
                }
                Text(text = "Deleted Notifications: $deleteCount")
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        NotificationDataComponent(notificationData)
        ExtendedFloatingActionButton(
            modifier = Modifier
                .align(End)
                .padding(top = 16.dp, bottom = 16.dp),
            icon = { Icon(Icons.Filled.Notifications, "") },
            text = { Text("Send Notification") },
            onClick = { onButtonClick(notificationData) },
            elevation = FloatingActionButtonDefaults.elevation(8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    NotificationsPlaygroundTheme {
        NotificationsTestComponent(deleteCount = 0) {}
    }
}
