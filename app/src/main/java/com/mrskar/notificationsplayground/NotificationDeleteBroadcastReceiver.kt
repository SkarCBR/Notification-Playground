package com.mrskar.notificationsplayground

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.activity.ComponentActivity

class NotificationDeleteBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val sharedPreferences = context.getSharedPreferences(
            context.getString(R.string.sharedpreferences_file), ComponentActivity.MODE_PRIVATE
        )
        val deleteCount = sharedPreferences.getInt(KEY_DELETE_COUNT,0)
        sharedPreferences.edit().putInt(KEY_DELETE_COUNT,deleteCount + 1).apply()
    }
}

const val KEY_DELETE_COUNT = "delete_count"