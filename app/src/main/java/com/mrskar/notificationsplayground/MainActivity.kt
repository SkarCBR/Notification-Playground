package com.mrskar.notificationsplayground

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mrskar.notificationsplayground.composables.NotificationsTestComponent
import com.mrskar.notificationsplayground.ui.theme.NotificationsPlaygroundTheme

class MainActivity : ComponentActivity() {

    private lateinit var notificationManager: CustomNotificationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val argument = intent.getStringExtra(ARG_SECTION) ?: ""
        navigateToResult(argument)
        notificationManager = CustomNotificationManagerImpl(this)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel()
        }
        setContent {
            val icon = mutableStateOf(Icons.Filled.FavoriteBorder)
            val (enableDarkMode, setDarkMode) = remember { mutableStateOf(false) }

            NotificationsPlaygroundTheme(darkTheme = enableDarkMode) {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    scaffoldState = rememberScaffoldState(),
                    topBar = {
                        TopAppBar(
                            title = { Text("Notifications Playground") },
                            backgroundColor = MaterialTheme.colors.secondary,
                            actions = {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    IconButton(
                                        onClick = { openNotificationsConfig() }
                                    ) {
                                        Icon(Icons.Filled.Settings, null)
                                    }
                                }
                                IconButton(
                                    onClick = {
                                        setDarkMode(!enableDarkMode)
                                        if (icon.value == Icons.Filled.FavoriteBorder) {
                                            icon.value = Icons.Filled.Favorite
                                        } else {
                                            icon.value = Icons.Filled.FavoriteBorder
                                        }
                                    }
                                ) {
                                    Icon(icon.value, null)
                                }
                            }
                        )
                    }
                ) {
                    NotificationsTestComponent { notificationManager.sendNotification(it) }
                }
            }
        }
    }

    private fun navigateToResult(section: String?) {
        when {
            section == "result" -> {
                startActivity(Intent(this, ResultActivity::class.java))
            }
            section == "special" -> {
                startActivity(Intent(this, SpecialResultActivity::class.java))
            }
        }
    }

    private fun openNotificationsConfig() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val intent = Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS).apply {
                putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
                putExtra(Settings.EXTRA_CHANNEL_ID, CHANNEL_ID)
            }
            startActivity(intent)
        }
    }

    companion object {
        fun buildIntent(context: Context, section: String?): Intent {
            return Intent(context, MainActivity::class.java).apply {
                if (!section.isNullOrEmpty()) {
                    putExtra(ARG_SECTION, section)
                }
            }
        }
    }
}

@Preview
@Composable
private fun DefaultPreview() {
    val (enableDarkMode, setDarkMode) = remember { mutableStateOf(true) }
    var changeThemeIcon = Icons.Filled.FavoriteBorder
    NotificationsPlaygroundTheme(darkTheme = enableDarkMode) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = { Text("Notifications Playground") },
                    backgroundColor = MaterialTheme.colors.secondary,
                    actions = {
                        IconButton(
                            onClick = {
                                setDarkMode(!enableDarkMode)
                                changeThemeIcon = Icons.Filled.Favorite
                            }
                        ) {
                            Icon(changeThemeIcon, null)
                        }
                    }
                )
            }
        ) {
            NotificationsTestComponent { }
        }
    }
}

const val ARG_SECTION = "arg_section"
const val ARG_IS_NOTIFICATION = "arg_is_notification"
const val ARG_NOTIFICATION_ORIGIN = "arg_notification_origin"