package com.mrskar.notificationsplayground

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class NotificationsTrackingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        trackNotification(intent)
        finish()
    }

    private fun trackNotification(intent: Intent) {
        val notificationID = intent.getStringExtra(ARG_SECTION) ?: ""
        Log.d("ARG_NOTIFICATION", notificationID)
    }

    companion object {
        fun buildIntent(context: Context, notificationID: String?): Intent {
            return Intent(context, NotificationsTrackingActivity::class.java).apply {
                if (!notificationID.isNullOrEmpty()) {
                    putExtra(ARG_SECTION, notificationID)
                }
            }
        }
    }
}
