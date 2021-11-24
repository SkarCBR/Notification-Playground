package com.mrskar.notificationsplayground

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExtendedFloatingActionButton
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.BottomCenter
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mrskar.notificationsplayground.composables.NotificationsTestComponent
import com.mrskar.notificationsplayground.composables.TopAppBarComponent
import com.mrskar.notificationsplayground.models.NotificationData
import com.mrskar.notificationsplayground.models.NotificationStyles
import com.mrskar.notificationsplayground.models.NotificationTypes
import com.mrskar.notificationsplayground.ui.theme.NotificationsPlaygroundTheme

class MainActivity : ComponentActivity() {

    private lateinit var notificationManager: CustomNotificationManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigateToResult(
            intent.getStringExtra(ARG_SECTION) ?: "",
            intent.getStringExtra(ARG_URL) ?: "",
            intent.getBooleanExtra(ARG_IS_NOTIFICATION, false)
        )
        notificationManager = CustomNotificationManagerImpl(this)
        val sharedPreferences = getSharedPreferences(
            getString(R.string.sharedpreferences_file), MODE_PRIVATE
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel()
        }
        setContent {
            val (enableDarkMode, setDarkMode) = remember {
                mutableStateOf(sharedPreferences.getBoolean(KEY_DARKMODE, false))
            }
            val notificationData = remember {
                NotificationData(
                    mutableStateOf("Notification title"),
                    mutableStateOf("Notification message"),
                    mutableStateListOf<MutableState<String>>(),
                    mutableStateOf(NotificationStyles.BIG_TEXT),
                    mutableStateOf(NotificationTypes.STANDARD),
                    mutableStateOf("https://despamers.com/"),
                    mutableStateOf(Uri.EMPTY),
                    mutableStateOf(true)
                )
            }

            NotificationsPlaygroundTheme(darkTheme = enableDarkMode) {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    scaffoldState = rememberScaffoldState(),
                    topBar = {
                        TopAppBarComponent(
                            onDarkModeChange = {
                                setDarkMode(!enableDarkMode)
                                sharedPreferences.edit()
                                    .putBoolean(KEY_DARKMODE, !enableDarkMode)
                                    .apply()
                            },
                            onConfigSelected = { openNotificationsSettings() }
                        )
                    }
                ) {
                    Box {
                        NotificationsTestComponent(
                            notificationData,
                            sharedPreferences.getInt(KEY_DELETE_COUNT, 0)
                        )
                        ExtendedFloatingActionButton(
                            modifier = Modifier
                                .align(BottomCenter)
                                .padding(top = 16.dp, bottom = 16.dp),
                            icon = { Icon(Icons.Filled.Notifications, "") },
                            text = { Text("Send Notification") },
                            onClick = { notificationManager.sendNotification(notificationData) },
                            elevation = FloatingActionButtonDefaults.elevation(8.dp)
                        )
                    }
                }
            }
        }
    }

    private fun navigateToResult(
        section: String,
        url: String,
        comingFromNotification: Boolean
    ) {
        when {
            section == "result" -> {
                startActivity(
                    ChildActivity.buildIntent(this, comingFromNotification, section, url)
                )
            }
            section == "special" -> {
                startActivity(
                    SingleTaskActivity.buildIntent(this, comingFromNotification, section, url)
                        .apply {
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
    val enableDarkMode by remember { mutableStateOf(false) }
    val notificationData = remember {
        NotificationData(
            mutableStateOf("Test title"),
            mutableStateOf("Test message"),
            mutableStateListOf<MutableState<String>>(),
            mutableStateOf(NotificationStyles.BIG_TEXT),
            mutableStateOf(NotificationTypes.STANDARD),
            mutableStateOf("https://despamers.com/"),
            mutableStateOf(Uri.EMPTY),
            mutableStateOf(true)
        )
    }
    NotificationsPlaygroundTheme(darkTheme = enableDarkMode) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBarComponent({ }, { })
            }
        ) {
            NotificationsTestComponent(notificationData = notificationData, deleteCount = 0)
        }
    }
}

const val ARG_URL = "arg_url"
const val ARG_SECTION = "arg_section"
const val ARG_IS_NOTIFICATION = "arg_is_notification"
const val KEY_DARKMODE = "darkmode"