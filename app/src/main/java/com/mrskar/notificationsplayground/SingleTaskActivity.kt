package com.mrskar.notificationsplayground

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mrskar.notificationsplayground.composables.SingleTaskComponent
import com.mrskar.notificationsplayground.ui.theme.NotificationsPlaygroundTheme

class SingleTaskActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val section = intent.getStringExtra(ARG_SECTION) ?: ""
        val url = intent.getStringExtra(ARG_URL) ?: ""
        val comingFromNotification = intent.getBooleanExtra(ARG_IS_NOTIFICATION, false)
        val isDarkMode = getSharedPreferences(
            getString(R.string.sharedpreferences_file), MODE_PRIVATE
        ).getBoolean(KEY_DARKMODE, false)
        setContent {
            val scaffoldState = rememberScaffoldState()
            NotificationsPlaygroundTheme(darkTheme = isDarkMode) {
                Scaffold(modifier = Modifier.fillMaxSize(),
                    scaffoldState = scaffoldState,
                    topBar = {
                        TopAppBar(
                            title = { Text("Single Task Activity") },
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
                    SingleTaskComponent(
                        url = url,
                        sectionToNavigate = section,
                        onBackButtonSelected = { goToApp() },
                        onSectionSelected = { goToResult(it) }
                    )
                    LaunchedEffect(comingFromNotification) {
                        if (comingFromNotification) {
                            val result = scaffoldState.snackbarHostState.showSnackbar(
                                message = "Notification selected with section: '$section'",
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

    private fun goToApp() {
        startActivity(
            MainActivity.buildIntent(this, false, null)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        )
        finish()
    }

    private fun goToResult(section: String) {
        startActivity(
            MainActivity.buildIntent(this, false, section)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        )
        finish()
    }

    companion object {
        fun buildIntent(
            context: Context,
            comingFromNotification: Boolean,
            section: String,
            url: String
        ): Intent {
            return Intent(context, SingleTaskActivity::class.java).apply {
                putExtra(ARG_IS_NOTIFICATION, comingFromNotification)
                putExtra(ARG_SECTION, section)
                putExtra(ARG_URL, url)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    NotificationsPlaygroundTheme(darkTheme = false) {
        Scaffold(modifier = Modifier.fillMaxSize(),
            scaffoldState = rememberScaffoldState(),
            topBar = {
                TopAppBar(
                    title = { Text("Special Detail") },
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
            SingleTaskComponent(
                url = "",
                sectionToNavigate = "result",
                onBackButtonSelected = { },
                onSectionSelected = { }
            )
        }
        BackHandler(enabled = true, onBack = { })
    }
}