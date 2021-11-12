package com.mrskar.notificationsplayground.models

import android.net.Uri
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList

data class NotificationData(
    val title: MutableState<String>,
    val message: MutableState<String>,
    val multiMessage: SnapshotStateList<MutableState<String>>,
    val style: MutableState<NotificationStyles>,
    val type: MutableState<NotificationTypes>,
    val url: MutableState<String>,
    val imageUri: MutableState<Uri>,
) {
    override fun toString(): String {
        return javaClass.simpleName.plus("(\n")
            .plus("   title = ${title.value},\n")
            .plus("   message = ${message.value},\n")
            .plus("   multiMessage nº = ${multiMessage.size},\n")
            .plus("   style = ${
                style.value.name
                    .lowercase()
                    .replaceFirstChar { it.uppercase() }
                    .replace("_", " ")
            },\n")
            .plus("   type = ${
                type.value.name
                    .lowercase()
                    .replaceFirstChar { it.uppercase() }
            },\n")
            .plus("   url = ${url.value},\n")
            .plus("   image = ${imageUri.value}\n)")
    }
}
