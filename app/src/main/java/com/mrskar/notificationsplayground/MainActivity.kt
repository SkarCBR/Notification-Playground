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
        val section = intent.getStringExtra(ARG_SECTION) ?: ""
        navigateToResult(section, intent.getBooleanExtra(ARG_IS_NOTIFICATION, false))
        notificationManager = CustomNotificationManagerImpl(this)
        val sharedPreferences = getSharedPreferences(
            getString(R.string.sharedpreferences_file), MODE_PRIVATE
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel()
        }
        setContent {
            val icon = remember { mutableStateOf(Icons.Filled.FavoriteBorder) }
            val (enableDarkMode, setDarkMode) = remember {
                mutableStateOf(sharedPreferences.getBoolean(KEY_DARKMODE, false))
            }

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
                                        onClick = { openNotificationsSettings() }
                                    ) {
                                        Icon(Icons.Filled.Settings, null)
                                    }
                                }
                                IconButton(
                                    onClick = {
                                        setDarkMode(!enableDarkMode)
                                        sharedPreferences.edit()
                                            .putBoolean(KEY_DARKMODE, !enableDarkMode)
                                            .apply()
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
                    NotificationsTestComponent(
                        sharedPreferences.getInt(KEY_DELETE_COUNT, 0)
                    ) { notificationManager.sendNotification(it) }
                }
            }
        }
    }

    private fun navigateToResult(section: String?, comingFromNotification: Boolean) {
        when {
            section == "result" -> {
                startActivity(
                    ResultActivity.buildIntent(this, comingFromNotification, section)
                )
            }
            section == "special" -> {
                startActivity(
                    SpecialResultActivity.buildIntent(this, comingFromNotification, section).apply {
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    }
                )
            }
        }
    }

    private fun openNotificationsSettings() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
                putExtra(Settings.EXTRA_APP_PACKAGE, packageName)
            }
            startActivity(intent)
        }
    }

    companion object {
        fun buildIntent(
            context: Context,
            comingFromNotification: Boolean,
            section: String?
        ): Intent {
            return Intent(context, MainActivity::class.java).apply {
                putExtra(ARG_IS_NOTIFICATION, comingFromNotification)
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
    val (enableDarkMode, setDarkMode) = remember { mutableStateOf(false) }
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
            NotificationsTestComponent(deleteCount = 0) { }
        }
    }
}

const val ARG_URL = "arg_url"
const val ARG_SECTION = "arg_section"
const val ARG_IS_NOTIFICATION = "arg_is_notification"
const val KEY_DARKMODE = "darkmode"