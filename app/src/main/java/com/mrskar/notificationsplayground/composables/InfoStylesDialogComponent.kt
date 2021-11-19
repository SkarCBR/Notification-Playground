package com.mrskar.notificationsplayground.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.mrskar.notificationsplayground.R
import com.mrskar.notificationsplayground.ui.theme.NotificationsPlaygroundTheme

@Composable
@OptIn(ExperimentalMaterialApi::class, ExperimentalUnitApi::class)
fun InfoStylesDialogComponent(openDialog: MutableState<Boolean>) {
    Column {
        if (openDialog.value) {
            AlertDialog(
                onDismissRequest = {
                    openDialog.value = false
                },
                title = {
                    Text(
                        text = stringResource(id = R.string.notification_styles),
                        fontWeight = FontWeight.Bold,
                        fontSize = TextUnit(20f, TextUnitType.Sp)
                    )
                },
                text = {
                    InfoStylesDialogContent()
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

@Composable
private fun InfoStylesDialogContent() {
    Column {
        Row(Modifier.padding(4.dp)) {
            Text(
                text = "Big Text: ",
                modifier = Modifier
                    .fillMaxWidth(0.25f),
                fontWeight = FontWeight.Bold
            )
            Text(
                text = stringResource(id = R.string.notification_style_bigtext),
            )
        }
        Row(Modifier.padding(4.dp)) {
            Text(
                text = "Big Picture: ",
                modifier = Modifier
                    .fillMaxWidth(0.25f),
                fontWeight = FontWeight.Bold
            )
            Text(
                text = stringResource(id = R.string.notification_style_bigpicture),
            )
        }
        Row(Modifier.padding(4.dp)) {
            Text(
                text = "Inbox: ",
                modifier = Modifier
                    .fillMaxWidth(0.25f),
                fontWeight = FontWeight.Bold
            )
            Text(
                text = stringResource(id = R.string.notification_style_inbox),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    NotificationsPlaygroundTheme(darkTheme = false) {
        InfoStylesDialogContent()
    }
}