package com.mrskar.notificationsplayground.composables

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.mrskar.notificationsplayground.ui.theme.NotificationsPlaygroundTheme

@Composable
fun NotificationsTestComponent(
    notificationData: NotificationData,
    deleteCount: Int,
) {
    Column(
        Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
            .fillMaxHeight()
    ) {
        NotificationConfigComponent(
            deleteCount = deleteCount,
            notificationData = notificationData
        )
        Spacer(
            modifier = Modifier.height(8.dp)
        )
        NotificationDataComponent(
            notificationData = notificationData
        )
        Spacer(
            modifier = Modifier.height(54.dp)
        )
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
    NotificationsPlaygroundTheme {
        NotificationsTestComponent(notificationData = notificationData, deleteCount = 0)
    }
}
