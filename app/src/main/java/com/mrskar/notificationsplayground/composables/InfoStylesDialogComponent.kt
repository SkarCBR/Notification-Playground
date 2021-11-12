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

@Composable
@OptIn(ExperimentalMaterialApi::class)
fun InfoStylesDialogComponent(openDialog: MutableState<Boolean>) {
    Column {
        if (openDialog.value) {
            AlertDialog(
                onDismissRequest = {
                    openDialog.value = false
                },
                title = {
                    Text(text = stringResource(id = R.string.notification_styles))
                },
                text = {
                    Column {
                        Text(
                            stringResource(id = R.string.notification_style_bigtext),
                            Modifier.padding(top = 4.dp, bottom = 4.dp)
                        )
                        Text(
                            stringResource(id = R.string.notification_style_bigpicture),
                            Modifier.padding(top = 4.dp, bottom = 4.dp)
                        )
                        Text(
                            stringResource(id = R.string.notification_style_inbox),
                            Modifier.padding(top = 4.dp, bottom = 4.dp)
                        )
                        Text(
                            stringResource(id = R.string.notification_style_custom),
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
    InfoStylesDialogComponent(openDialog = remember { mutableStateOf(true) })
}