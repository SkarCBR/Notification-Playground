package com.mrskar.notificationsplayground.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.mrskar.notificationsplayground.ui.theme.NotificationsPlaygroundTheme

@Composable
fun SpecialResultComponent(
    sectionToNavigate: String,
    comingFromNotification: Boolean,
    onBackButtonSelected: () -> Unit,
    onSectionSelected: (section: String) -> Unit,
) {
    NotificationsPlaygroundTheme {
        Column(Modifier.fillMaxSize()) {
            val (snackbarVisibleState, setSnackbarState) =
                remember { mutableStateOf(comingFromNotification) }

            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = "This is a Special Announcement!"
            )
            Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                Button(
                    onClick = { onBackButtonSelected() }
                ) {
                    Text(text = "Back to App")
                }
                if (sectionToNavigate.isNotEmpty()) {
                    Button(
                        onClick = { onSectionSelected(sectionToNavigate) }
                    ) {
                        Text(text = "Go to ".plus(sectionToNavigate.replaceFirstChar { it.uppercase() }))
                    }
                }
            }
            if (snackbarVisibleState) {
                Snackbar(
                    action = {
                        Button(onClick = { setSnackbarState(!snackbarVisibleState) }
                        ) {
                            Text("Dismiss")
                        }
                    }
                ) {
                    Text(text = "Notification selected!")
                }
            }
        }

    }
}