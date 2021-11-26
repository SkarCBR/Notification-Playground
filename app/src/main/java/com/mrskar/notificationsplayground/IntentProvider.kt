package com.mrskar.notificationsplayground

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.app.TaskStackBuilder
import com.mrskar.notificationsplayground.models.NotificationTypes

interface IntentProvider {
    fun getPendingIntent(type: NotificationTypes, url: String, trackingId: Int): PendingIntent
    fun getDeletePendingIntent(url: String): PendingIntent
}

class IntentProviderImpl(
    private val context: Context,
) : IntentProvider {

    override fun getPendingIntent(
        type: NotificationTypes,
        url: String,
        trackingId: Int
    ): PendingIntent {
        return when (type) {
            NotificationTypes.STANDARD -> {
                createPendingIntentWithBackStack(url, trackingId)
            }
            NotificationTypes.SINGLE_TASK -> {
                createPendingIntentSingleTask(url, trackingId)
            }
            NotificationTypes.DEEPLINK -> {
                createPendingIntentActionView(url, trackingId)
            }
        }
    }

    override fun getDeletePendingIntent(url: String): PendingIntent {
        return PendingIntent.getBroadcast(
            context.applicationContext,
            888,
            Intent(context, NotificationDeleteBroadcastReceiver::class.java),
            PendingIntent.FLAG_IMMUTABLE
        )
    }

    private fun createPendingIntentWithBackStack(url: String, trackingId: Int): PendingIntent {
        val resultIntent = ChildActivity
            .buildIntent(context, true, getSectionFromUrl(url), url)

        return TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(resultIntent)
            PendingIntent.getActivity(
                context,
                trackingId,
                resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        }
    }

    private fun createPendingIntentSingleTask(url: String, trackingId: Int): PendingIntent {
        val notifyIntent = SingleTaskActivity
            .buildIntent(context, true, getSectionFromUrl(url), url)
            .apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
        return PendingIntent.getActivity(
            context, trackingId, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    private fun createPendingIntentActionView(url: String, trackingId: Int): PendingIntent {
        return PendingIntent.getActivity(
            context,
            trackingId,
            Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
                putExtra(ARG_URL, url)
                putExtra(ARG_SECTION, getSectionFromUrl(url))
                putExtra(ARG_IS_NOTIFICATION, true)
            },
            PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    private fun getSectionFromUrl(url: String): String {
        return if (url.contains(".com/")) {
            url.substringAfter(".com/")
        } else {
            url.substringAfter("://")
        }
    }
}