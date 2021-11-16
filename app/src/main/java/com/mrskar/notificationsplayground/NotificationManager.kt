package com.mrskar.notificationsplayground

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationChannelGroup
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import com.mrskar.notificationsplayground.models.NotificationData
import com.mrskar.notificationsplayground.models.NotificationStyles
import com.mrskar.notificationsplayground.models.NotificationTypes
import kotlin.random.Random

interface CustomNotificationManager {
    fun sendNotification(notificationData: NotificationData)
    fun createNotificationChannel()
}

class CustomNotificationManagerImpl constructor(
    private val context: Context
) : CustomNotificationManager {
    private var manager: NotificationManager =
        context.getSystemService(ComponentActivity.NOTIFICATION_SERVICE) as NotificationManager

    private val trackingId = Random.nextInt(1000)

    override fun sendNotification(notificationData: NotificationData) {
        val notification = createNotificationWithData(notificationData)
        manager.notify(
            trackingId,
            notification
        )
    }

    override fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O &&
            manager.getNotificationChannel(CHANNEL_ID) == null
        ) {
            manager.createNotificationChannel(
                NotificationChannel(
                    CHANNEL_ID,
                    "Despamers Channel",
                    NotificationManager.IMPORTANCE_HIGH
                )
            )
            val groupId = "despamers_01"
            // The user-visible name of the group.
            val groupName = "Despamers Group"
            manager.createNotificationChannelGroup(
                NotificationChannelGroup(groupId, groupName)
            )
        }
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun createNotificationWithData(data: NotificationData): Notification {
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.android_masters)
            .setContentTitle(data.title.value)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentText(data.message.value)
            .setContentIntent(getPendingIntent(data.type.value, data.url.value))
            .setGroup(GROUP_KEY)

        setStyle(builder, data)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(CHANNEL_ID)
        }

        return builder.build()
    }

    private fun getPendingIntent(type: NotificationTypes, url: String): PendingIntent {
        return when (type) {
            NotificationTypes.STANDARD -> {
                createPendingIntentWithBackStack(url)
            }
            NotificationTypes.SPECIAL -> {
                createPendingIntentSingleTask(url)
            }
            NotificationTypes.DEEPLINK -> {
                createPendingIntentActionView(url)
            }
        }
    }

    private fun createPendingIntentWithBackStack(url: String): PendingIntent {
        val resultIntent = ResultActivity
            .buildIntent(context, true, getSectionFromUrl(url))

        return TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(resultIntent)
            getPendingIntent(trackingId, PendingIntent.FLAG_UPDATE_CURRENT)
        } ?: PendingIntent.getActivity(
            context,
            trackingId,
            resultIntent,
            PendingIntent.FLAG_CANCEL_CURRENT
        )
    }

    private fun createPendingIntentSingleTask(url: String): PendingIntent {
        val notifyIntent = SpecialResultActivity
            .buildIntent(context, true, getSectionFromUrl(url))
            .apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
        return PendingIntent.getActivity(
            context, trackingId, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    private fun createPendingIntentActionView(url: String): PendingIntent {
        return PendingIntent.getActivity(
            context,
            trackingId,
            Intent(Intent.ACTION_VIEW, Uri.parse(url)).apply {
                putExtra(ARG_SECTION, getSectionFromUrl(url))
                putExtra(ARG_IS_NOTIFICATION, true)
            },
            PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    private fun getSectionFromUrl(url: String): String {
        return url.substringAfter(".com/")
    }

    private fun setStyle(builder: NotificationCompat.Builder, data: NotificationData) {
        val style = when (data.style.value) {
            NotificationStyles.BIG_TEXT -> {
                NotificationCompat.BigTextStyle()
                    .bigText(data.message.value)
                    .setBigContentTitle(data.message.value)
            }
            NotificationStyles.BIG_PICTURE -> {
                val bitmap = getBitmapPicture(data.imageUri.value)
                builder.setLargeIcon(bitmap)
                NotificationCompat.BigPictureStyle()
                    .bigPicture(bitmap)
                    .bigLargeIcon(null)
                    .setBigContentTitle(data.title.value)
                    .setSummaryText(data.message.value)
            }
            NotificationStyles.INBOX -> {
                NotificationCompat.InboxStyle()
                    .setBigContentTitle(data.title.value)
                    .addLine(data.multiMessage[0].value)
                    .addLine(data.multiMessage[1].value)
                    .addLine(data.multiMessage[2].value)
            }
        }
        builder.setStyle(style)
    }

    private fun getBitmapPicture(uri: Uri): Bitmap? {
        return if (uri != Uri.EMPTY) {
            if (Build.VERSION.SDK_INT < 28) {
                MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
            } else {
                val source = ImageDecoder.createSource(context.contentResolver, uri)
                ImageDecoder.decodeBitmap(source)
            }
        } else {
            null
        }
    }
}

const val CHANNEL_ID = "TestNotificationsPlayground"
private const val GROUP_KEY = "com.mrskar.notificationsplayground"