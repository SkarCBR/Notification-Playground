# Notification-Playground
This app is just intended to play a bit with some notifications configurations and check how they look and behave.<br>
I might keep improving the app with more configurations, we'll see...<br>

Feel free to download this code and modify it to your needs :)

<b>Release</b><br>
You can download last release from https://github.com/SkarCBR/Notification-Playground/releases

You can choose from 3 different Notification Types:
- <b>Standard:</b> Explicit Intent to Result Activity. Back button goes to parent Activity
- <b>Special:</b> Explicit Intent to Special Activity (single task). Back goes where user was. 
- <b>Deeplink:</b> Implicit Intent with Deeplink. system will show a chooser with apps that have that Deeplink on manifest.

And 3 different Notification Styles:
- <b>Big Text:</b> Large-format notifications that include a lot of text.
- <b>Big Picture:</b> Large-format notifications that include a large image attachment.
- <b>Inbox:</b> Large-format notifications that include a list of (up to 5) strings.

There are 2 Notification Channels preconfigured:
- <b>High:</b> With vibration and LED light with color
- <b>Low:</b> Without vibration


Related Links:
- Notification on Android: https://developer.android.com/guide/topics/ui/notifiers/notifications 
- Activity Actions available: https://developer.android.com/reference/android/content/Intent#standard-activity-actions
- Intent filters: https://developer.android.com/guide/components/intents-filters 
- Managing task in Android: https://developer.android.com/guide/components/activities/tasks-and-back-stack#ManagingTasks 
- Notifications Styles: https://developer.android.com/training/notify-user/expanded 
