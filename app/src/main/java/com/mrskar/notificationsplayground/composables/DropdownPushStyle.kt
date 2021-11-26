package com.mrskar.notificationsplayground.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
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
import com.mrskar.notificationsplayground.models.NotificationStyles
import com.mrskar.notificationsplayground.ui.theme.NotificationsPlaygroundTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DropdownPushStyle(onItemSelected: (NotificationStyles) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    val openDialog = remember { mutableStateOf(false) }
    val items = listOf(
        NotificationStyles.BIG_TEXT,
        NotificationStyles.BIG_PICTURE,
        NotificationStyles.INBOX,
    )
    var selectedIndex by remember { mutableStateOf(0) }
    Row {
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            TextField(
                readOnly = true,
                value = items[selectedIndex].name.lowercase()
                    .replaceFirstChar { it.uppercase() }
                    .replace("_", " "),
                onValueChange = {},
                modifier = Modifier
                    .clickable(onClick = { expanded = true })
                    .fillMaxWidth(0.8f),
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded
                    )
                }
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                items.forEachIndexed { index, item ->
                    DropdownMenuItem(onClick = {
                        selectedIndex = index
                        expanded = false
                        onItemSelected(item)
                    }) {
                        Text(text = item.name.lowercase()
                            .replaceFirstChar { it.uppercase() }
                            .replace("_", " ")
                        )
                    }
                }
            }
        }
        IconButton(
            onClick = { openDialog.value = true },
            Modifier.align(Alignment.CenterVertically)
        ) {
            Icon(Icons.Outlined.Info, null)
        }
        InfoStylesDialogComponent(openDialog)
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    NotificationsPlaygroundTheme {
        DropdownPushStyle { /* no op */ }
    }
}