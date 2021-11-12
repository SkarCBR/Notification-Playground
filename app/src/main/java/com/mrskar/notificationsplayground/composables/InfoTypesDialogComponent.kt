package com.mrskar.notificationsplayground.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mrskar.notificationsplayground.R
import com.mrskar.notificationsplayground.ui.theme.NotificationsPlaygroundTheme

@Composable
@OptIn(ExperimentalMaterialApi::class)
fun InfoTypesDialogComponent(openDialog: MutableState<Boolean>) {
    Column {
        if (openDialog.value) {
            AlertDialog(
                onDismissRequest = {
                    openDialog.value = false
                },
                title = {
                    Text(text = stringResource(id = R.string.notification_types))
                },
                text = {
                    Column {
                        Text(
                            stringResource(id = R.string.notification_type_standard_text),
                            Modifier.padding(top = 4.dp, bottom = 4.dp)
                        )
                        Text(
                            stringResource(id = R.string.notification_type_special_text),
                            Modifier.padding(top = 4.dp, bottom = 4.dp)
                        )
                        Text(
                            stringResource(id = R.string.notification_type_deeplink_text),
                            Modifier.padding(top = 4.dp, bottom = 4.dp)
                        )
                    }
                },
                confirmButton = {
                    Button(
                        onClick = {
                            openDialog.value = false
                        }
                    ) {
                        Text(stringResource(id = R.string.got_it))
                    }
                },
            )
        }
    }
}

@Preview
@Composable
private fun DefaultPreview() {
    NotificationsPlaygroundTheme(darkTheme = false) {
        InfoTypesDialogComponent(openDialog = remember { mutableStateOf(true) })
    }
}