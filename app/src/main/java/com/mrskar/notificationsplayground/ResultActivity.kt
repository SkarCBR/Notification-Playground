package com.mrskar.notificationsplayground

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Button
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.mrskar.notificationsplayground.ui.theme.NotificationsPlaygroundTheme

class ResultActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val isDarkMode = getSharedPreferences(
            getString(R.string.sharedpreferences_file), MODE_PRIVATE
        ).getBoolean(KEY_DARKMODE, false)
        val comingFromNotification = intent.getBooleanExtra(ARG_IS_NOTIFICATION, false)
        setContent {
            val (snackbarVisibleState, setSnackbarState) =
                remember { mutableStateOf(comingFromNotification) }
            NotificationsPlaygroundTheme {
                Text(text = "This is an Announcement!")

                if (snackbarVisibleState) {
                    Snackbar(
                        action = {
                            Button(onClick = { setSnackbarState(!snackbarVisibleState) }
                            ) {
                                Text("Dismiss")
                            }
                        }
                    ) {
                        Text(text = "Notification selected!")
                    }
                }
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