package com.mrskar.notificationsplayground

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mrskar.notificationsplayground.composables.SpecialResultComponent
import com.mrskar.notificationsplayground.ui.theme.NotificationsPlaygroundTheme

class SpecialResultActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val argument = intent.getStringExtra(ARG_SECTION) ?: ""
        val comingFromNotification = intent.getBooleanExtra(ARG_IS_NOTIFICATION, false)
        val isDarkMode = getSharedPreferences(
            getString(R.string.sharedpreferences_file), MODE_PRIVATE
        ).getBoolean(KEY_DARKMODE, false)
        setContent {
            SpecialResultComponent(
                sectionToNavigate = argument,
                comingFromNotification = comingFromNotification,
                onBackButtonSelected = { goToApp() },
                onSectionSelected = { goToResult(it) }
            )
            BackHandler(enabled = true, onBack = { finish() })
        }
    }

    private fun goToApp() {
        startActivity(
            MainActivity.buildIntent(this, null)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        )
        finish()
    }

    private fun goToResult(section: String) {
        startActivity(
            MainActivity.buildIntent(this, section)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                .putExtras(intent)
        )
        finish()
    }

    companion object {
        fun buildIntent(context: Context, comingFromNotification: Boolean): Intent {
            return Intent(context, SpecialResultActivity::class.java).apply {
                putExtra(ARG_IS_NOTIFICATION, comingFromNotification)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    SpecialResultComponent(
        sectionToNavigate = "result",
        comingFromNotification = false,
        onBackButtonSelected = { },
        onSectionSelected = { }
    )
    BackHandler(enabled = true, onBack = { })
}