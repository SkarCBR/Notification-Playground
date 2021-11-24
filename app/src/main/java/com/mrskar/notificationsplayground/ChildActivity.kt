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
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mrskar.notificationsplayground.composables.ChildComponent
import com.mrskar.notificationsplayground.ui.theme.NotificationsPlaygroundTheme

class ChildActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
                            title = { Text("Child Activity") },
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
                    ChildComponent(
                        comingFromNotification = intent.getBooleanExtra(ARG_IS_NOTIFICATION, false),
                        scaffoldState = scaffoldState
                    )
                }
                BackHandler(enabled = true, onBack = { finish() })
            }
        }
    }

    companion object {
        fun buildIntent(
            context: Context,
            comingFromNotification: Boolean,
            section: String,
            url: String
        ): Intent {
            return Intent(context, ChildActivity::class.java).apply {
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
            ChildComponent(comingFromNotification = false, scaffoldState = scaffoldState)
        }
        BackHandler(enabled = true, onBack = { })
    }
}