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
import android.os.Bundle
import android.provider.MediaStore
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import com.mrskar.notificationsplayground.composables.NotificationsTestComponent
import com.mrskar.notificationsplayground.models.NotificationData
import com.mrskar.notificationsplayground.models.NotificationStyles
import com.mrskar.notificationsplayground.models.NotificationTypes
import com.mrskar.notificationsplayground.ui.theme.BrandBlue
import com.mrskar.notificationsplayground.ui.theme.NotificationsPlaygroundTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {

    private lateinit var notificationManagerCompat: NotificationManager

    private val trackingId = Random.nextInt(1000)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        notificationManagerCompat = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val argument = intent.getStringExtra(ARG_SECTION) ?: ""
        navigateToResult(argument)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel()
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
                    NotificationsTestComponent { sendNotification(it) }
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

    private fun sendNotification(notificationData: NotificationData) {
        val notification = createNotificationWithData(notificationData)
        notificationManagerCompat.notify(
            trackingId,
            notification
        )
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun createNotificationWithData(data: NotificationData): Notification {
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
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
        val resultIntent = ResultActivity.buildIntent(this, true)
            .putExtra(ARG_SECTION, getSectionFromUrl(url))

        return TaskStackBuilder.create(this).run {
            addNextIntentWithParentStack(resultIntent)
            getPendingIntent(trackingId, PendingIntent.FLAG_UPDATE_CURRENT)
        } ?: PendingIntent.getActivity(
            this,
            trackingId,
            resultIntent,
            PendingIntent.FLAG_CANCEL_CURRENT
        )
    }

    private fun createPendingIntentSingleTask(url: String): PendingIntent {
        val notifyIntent = SpecialResultActivity.buildIntent(this, true).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra(ARG_SECTION, getSectionFromUrl(url))
        }
        return PendingIntent.getActivity(
            this, trackingId, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    private fun createPendingIntentActionView(url: String): PendingIntent {
        return PendingIntent.getActivity(
            this,
            trackingId,
            Intent(Intent.ACTION_VIEW, Uri.parse(url))
                .putExtra(ARG_SECTION, getSectionFromUrl(url)),
            PendingIntent.FLAG_CANCEL_CURRENT
        )
    }

    private fun getSectionFromUrl(url: String): String {
        val sectionData = url.substringAfter(".com/")
        return sectionData.substringBefore("/")
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
                MediaStore.Images.Media.getBitmap(contentResolver, uri)
            } else {
                val source = ImageDecoder.createSource(contentResolver, uri)
                ImageDecoder.decodeBitmap(source)
            }
        } else {
            null
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O &&
            notificationManagerCompat.getNotificationChannel(CHANNEL_ID) == null
        ) {
            notificationManagerCompat.createNotificationChannel(
                NotificationChannel(
                    CHANNEL_ID,
                    "Despamers Channel",
                    NotificationManager.IMPORTANCE_HIGH
                )
            )
            val groupId = "despamers_01"
            // The user-visible name of the group.
            val groupName = "Despamers Group"
            notificationManagerCompat.createNotificationChannelGroup(
                NotificationChannelGroup(groupId, groupName)
            )
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

@Preview(showBackground = true)
@Composable
private fun DefaultPreview() {
    val enableDarkMode = remember { mutableStateOf(false) }
    var changeThemeIcon = Icons.Filled.FavoriteBorder
    NotificationsPlaygroundTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = { Text("Notifications Playground") },
                    backgroundColor = BrandBlue,
                    actions = {
                        IconButton(
                            onClick = {
                                enableDarkMode.value = !enableDarkMode.value
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
private const val CHANNEL_ID = "TestNotificationsPlayground"
private const val GROUP_KEY = "com.mrskar.notificationsplayground"