package com.mrskar.notificationsplayground.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mrskar.notificationsplayground.models.NotificationStyles
import com.mrskar.notificationsplayground.ui.theme.NotificationsPlaygroundTheme

@Composable
fun DropdownPushStyle(onItemSelected: (NotificationStyles) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val openDialog = remember { mutableStateOf(false) }
    val defaultStyle = 0
    val items = listOf(
        NotificationStyles.BIG_TEXT,
        NotificationStyles.BIG_PICTURE,
        NotificationStyles.INBOX,
    )
    var selectedIndex by remember { mutableStateOf(0) }
    Box(
        modifier = Modifier
            .wrapContentSize(Alignment.TopStart)) {
        Row {
            Text(
                items[selectedIndex].name.lowercase()
                    .replaceFirstChar { it.uppercase() }
                    .replace("_", " "),
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .clickable(onClick = { expanded = true })
                    .padding(start = 8.dp)
            )
            IconButton(
                onClick = { openDialog.value = true },
                Modifier.padding(start = 8.dp)
            ) {
                Icon(Icons.Outlined.Info, null)
            }
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .wrapContentSize()
                .background(MaterialTheme.colors.background)
        ) {
            items.forEachIndexed { index, item ->
                DropdownMenuItem(onClick = {
                    selectedIndex = index
                    expanded = false
                    onItemSelected(item)
                }) {
                    val defaultSuffix = if (index == defaultStyle) {
                        " (Default)"
                    } else {
                        ""
                    }
                    Text(text = item.name.lowercase().lowercase()
                        .replaceFirstChar { it.uppercase() }
                        .replace("_", " ")
                        .plus(defaultSuffix)
                    )
                }
            }
        }
    }
    InfoStylesDialogComponent(openDialog)
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    NotificationsPlaygroundTheme {
        DropdownPushStyle { /* no op */ }
    }
}