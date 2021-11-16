package com.mrskar.notificationsplayground

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.SnackbarResult
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mrskar.notificationsplayground.ui.theme.NotificationsPlaygroundTheme

class ResultActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val isDarkMode = getSharedPreferences(
            getString(R.string.sharedpreferences_file), MODE_PRIVATE
        ).getBoolean(KEY_DARKMODE, false)
        val comingFromNotification = intent.getBooleanExtra(ARG_IS_NOTIFICATION, false)
        setContent {
            val scaffoldState = rememberScaffoldState()
            NotificationsPlaygroundTheme(darkTheme = isDarkMode) {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    scaffoldState = scaffoldState,
                    topBar = {
                        TopAppBar(
                            title = { Text("Result Detail") },
                            backgroundColor = MaterialTheme.colors.secondary,
                            actions = { },
                            navigationIcon = {
                                IconButton(
                                    onClick = { onBackPressed() }
                                ) {
                                    Icon(Icons.Outlined.ArrowBack, null)
                                }
                            }
                        )
                    }
                ) {
                    Text(
                        modifier = Modifier.padding(16.dp),
                        text = "This is an Announcement!",
                        style = TextStyle(fontWeight = FontWeight.Bold)
                    )

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
                BackHandler(enabled = true, onBack = { finish() })
            }
        }
    }

    companion object {
        fun buildIntent(
            context: Context,
            comingFromNotification: Boolean,
            section: String?
        ): Intent {
            return Intent(context, ResultActivity::class.java).apply {
                putExtra(ARG_IS_NOTIFICATION, comingFromNotification)
                putExtra(ARG_SECTION, section)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    val scaffoldState = rememberScaffoldState()
    NotificationsPlaygroundTheme(darkTheme = false) {
        Scaffold(modifier = Modifier.fillMaxSize(),
            scaffoldState = scaffoldState,
            topBar = {
                TopAppBar(
                    title = { Text("Result Detail") },
                    backgroundColor = MaterialTheme.colors.secondary,
                    actions = { },
                    navigationIcon = {
                        IconButton(
                            onClick = { }
                        ) {
                            Icon(Icons.Outlined.ArrowBack, null)
                        }
                    }
                )
            }
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = "This is an Announcement!",
                    style = TextStyle(fontWeight = FontWeight.Bold)
                )
            }
        }
        BackHandler(enabled = true, onBack = { })
    }
}