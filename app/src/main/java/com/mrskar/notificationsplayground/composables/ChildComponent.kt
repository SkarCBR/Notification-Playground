package com.mrskar.notificationsplayground.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarResult
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mrskar.notificationsplayground.R

@Composable
fun ChildComponent(
    comingFromNotification: Boolean,
    scaffoldState: ScaffoldState,
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(4.dp)) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp, bottom = 4.dp),
            elevation = 4.dp
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(16.dp),
                    text = "Welcome to a standard Result screen, " +
                        "use your imagination to fill it up...",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold
                    )
                )
                Image(
                    painter = painterResource(id = R.drawable.result_image),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                )
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(16.dp),
                    text = "This activity was open using an Implicit Intent with flag " +
                        "FLAG_UPDATE_CURRENT\n\n" +
                        "But adding it to Back Stack with its Parent (declared on App Manifest)"
                )
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(16.dp),
                    text = "Back goes to parent MainActivity (finish this one)",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }

        LaunchedEffect(comingFromNotification) {
            if (comingFromNotification) {
                val result = scaffoldState.snackbarHostState.showSnackbar(
                    message = "Notification selected!",
                    actionLabel = "Dismiss",
                    duration = SnackbarDuration.Short
                )
                when (result) {
                    SnackbarResult.Dismissed -> {
                    }
                    SnackbarResult.ActionPerformed -> {
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    ChildComponent(true, rememberScaffoldState())
}