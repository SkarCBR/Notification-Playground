package com.mrskar.notificationsplayground

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationChannelGroup
import android.app.NotificationManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.ComponentActivity
import androidx.compose.ui.graphics.toArgb
import androidx.core.app.NotificationCompat
import com.mrskar.notificationsplayground.models.NotificationData
import com.mrskar.notificationsplayground.models.NotificationStyles
import com.mrskar.notificationsplayground.ui.theme.BrandBlue
import kotlin.random.Random

interface CustomNotificationManager {
    fun sendNotification(notificationData: NotificationData)
    fun createNotificationChannel()
}

class CustomNotificationManagerImpl constructor(
    private val context: Context
) : CustomNotificationManager {
    private val manager: NotificationManager =
        context.getSystemService(ComponentActivity.NOTIFICATION_SERVICE) as NotificationManager

    private val intentProvider = IntentProviderImpl(context)

    override fun sendNotification(notificationData: NotificationData) {
        val trackingId = Random.nextInt(1000)
        val notification = createNotificationWithData(notificationData, trackingId)
        manager.notify(
            trackingId,
            notification
        )
    }

    override fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (manager.getNotificationChannel(CHANNEL_ID_HIGH) == null) {
                val channel = NotificationChannel(
                    CHANNEL_ID_HIGH,
                    "High Priority Channel",
                    NotificationManager.IMPORTANCE_HIGH
                ).apply {
                    description = "This is for high priority notifications"
                    lightColor = BrandBlue.toArgb()
                    enableLights(true)
                    enableVibration(true)
                    vibrationPattern = longArrayOf(0, 100, 1000, 200, 2000)
                }
                manager.createNotificationChannel(channel)
            }
            if (manager.getNotificationChannel(CHANNEL_ID_LOW) == null) {
                val channel = NotificationChannel(
                    CHANNEL_ID_LOW,
                    "Low Priority Channel",
                    NotificationManager.IMPORTANCE_LOW
                ).apply {
                    description = "This is for low priority notifications"
                    enableVibration(false)
                }
                manager.createNotificationChannel(channel)
            }
            val groupId = "despamers_01"
            // The user-visible name of the group.
            val groupName = "Despamers Group"
            manager.createNotificationChannelGroup(
                NotificationChannelGroup(groupId, groupName)
            )
        }
    }

    private fun createNotificationWithData(data: NotificationData, trackingId: Int): Notification {
        val channelId = if (data.priority.value) CHANNEL_ID_HIGH else CHANNEL_ID_LOW
        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.android_masters)
            .setContentTitle(data.title.value)
            .setAutoCancel(true)
            .setPriority(getPriority(data.priority.value))
            .setContentText(data.message.value)
            .setGroup(GROUP_KEY)
            .setContentIntent(
                intentProvider.getPendingIntent(
                    data.type.value,
                    data.url.value,
                    trackingId
                )
            )
            .setDeleteIntent(intentProvider.getDeletePendingIntent(data.url.value))

        setStyle(builder, data)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(channelId)
        }

        return builder.build()
    }

    private fun getPriority(expanded: Boolean): Int {
        return when (expanded) {
            true -> NotificationCompat.PRIORITY_HIGH
            false -> NotificationCompat.PRIORITY_LOW
        }
    }

    private fun setStyle(builder: NotificationCompat.Builder, data: NotificationData) {
        val style = when (data.style.value) {
            NotificationStyles.BIG_TEXT -> {
                NotificationCompat.BigTextStyle()
                    .bigText(data.message.value)
                    .setBigContentTitle(data.title.value)
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

const val CHANNEL_ID_HIGH = "TestNotificationsPlaygroundHigh"
const val CHANNEL_ID_LOW = "TestNotificationsPlaygroundLow"
private const val GROUP_KEY = "com.mrskar.notificationsplayground"