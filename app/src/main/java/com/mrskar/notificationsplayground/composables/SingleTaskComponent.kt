package com.mrskar.notificationsplayground.composables

import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mrskar.notificationsplayground.R

@Composable
fun SingleTaskComponent(
    url: String,
    sectionToNavigate: String,
    onBackButtonSelected: () -> Unit,
    onSectionSelected: (section: String) -> Unit,
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(4.dp)) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp, bottom = 4.dp)
                .align(CenterHorizontally),
            elevation = 4.dp
        ) {
            Column(horizontalAlignment = CenterHorizontally) {
                Image(
                    painter = painterResource(id = R.drawable.special_offer),
                    contentDescription = null,
                    modifier = Modifier
                        .align(CenterHorizontally)
                )
                Text(
                    modifier = Modifier
                        .align(CenterHorizontally)
                        .padding(16.dp),
                    text = "Hurry up, there is a special offer!",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    modifier = Modifier
                        .align(CenterHorizontally)
                        .padding(16.dp),
                    text = "Since this Activity was not added to Back Stack or Recents, when user " +
                        "go back or close this screen it will be redirected to where it was when " +
                        "notification was selected",
                )
            }
        }
        Column(modifier = Modifier.align(CenterHorizontally)) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp, bottom = 4.dp)
                    .align(CenterHorizontally),
                elevation = 4.dp
            ) {
                Column(horizontalAlignment = CenterHorizontally) {
                    Text(
                        text = "Open/Go to App main with no arguments.\n" +
                            "Explicit Intent to MainActivity.",
                        modifier = Modifier.padding(4.dp)
                    )
                    Button(
                        onClick = { onBackButtonSelected() },
                        modifier = Modifier.padding(4.dp)
                    ) {
                        Text(text = "Go to App")
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
                            text = "This button will navigate to the App Section provided with " +
                                "this Intent arguments:\n" +
                                " -comingFromNotification = false\n" +
                                " -section = $sectionToNavigate\n" +
                                " -url = $url",
                            modifier = Modifier
                                .padding(8.dp)
                                .wrapContentSize()
                        )
                        Button(
                            onClick = { onSectionSelected(sectionToNavigate) },
                            modifier = Modifier.padding(8.dp)
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
    SingleTaskComponent(
        url = "",
        sectionToNavigate = "result",
        onBackButtonSelected = { /*TODO*/ },
        onSectionSelected = {}
    )
}