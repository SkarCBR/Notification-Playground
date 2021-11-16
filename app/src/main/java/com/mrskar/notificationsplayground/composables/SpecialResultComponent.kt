package com.mrskar.notificationsplayground.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SpecialResultComponent(
    sectionToNavigate: String,
    onBackButtonSelected: () -> Unit,
    onSectionSelected: (section: String) -> Unit,
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(4.dp)) {
        Text(
            modifier = Modifier
                .align(CenterHorizontally)
                .padding(16.dp),
            text = "This is a Special Announcement!",
            style = TextStyle(
                fontWeight = FontWeight.Bold
            )
        )
        Column(modifier = Modifier.align(CenterHorizontally)) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp, bottom = 4.dp)
                    .align(CenterHorizontally),
                elevation
                = 4.dp) {
                Column(horizontalAlignment = CenterHorizontally) {
                    Text(
                        text = "Back App Main with no arguments.",
                        modifier = Modifier.padding(4.dp)
                    )
                    Button(
                        onClick = { onBackButtonSelected() },
                        modifier = Modifier.padding(4.dp)
                    ) {
                        Text(text = "Back to App")
                    }
                }
            }
            if (sectionToNavigate.isNotEmpty()) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp, bottom = 4.dp)
                        .align(CenterHorizontally),
                    elevation = 4.dp) {
                    Column(horizontalAlignment = CenterHorizontally) {
                        Text(
                            text = "This button will navigate to the App Section provided.\n" +
                                "Intent arguments:\n" +
                                " -comingFromNotification = false\n" +
                                " -section = $sectionToNavigate",
                            modifier = Modifier
                                .padding(4.dp)
                                .wrapContentSize()
                        )
                        Button(
                            onClick = { onSectionSelected(sectionToNavigate) },
                            modifier = Modifier.padding(4.dp)
                        ) {
                            Text(text = "Go to ".plus(sectionToNavigate.replaceFirstChar { it.uppercase() }))
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    SpecialResultComponent(
        sectionToNavigate = "result",
        onBackButtonSelected = { /*TODO*/ },
        onSectionSelected = {}
    )
}