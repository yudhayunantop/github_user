package com.dicoding.picodiploma.submission2

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import java.util.*

object AlarmHelper {
    const val ALARM_CHANNEL_ID = "channel_reminder"
    const val ALARM_CHANNEL_NAME = "Reminder Channel"
    const val ALARM_TITTLE = "alarm_tittle"
    const val ALARM_MESSAGE = "alarm_message"

    // Function : for show notification
    fun showNotification(
            context: Context,
            title: String,
            message: String,
            notificationId: Int,
            intent: PendingIntent
    ) {
        val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        // Create NotificationCompat Builder
        val notification = NotificationCompat.Builder(context, ALARM_CHANNEL_ID).apply {
            setSmallIcon(R.drawable.alarm)
            setLargeIcon(
                    BitmapFactory.decodeResource(
                            context.resources,
                            R.drawable.alarm
                    )
            )
            setContentTitle(title)
            setContentText(message)
            setContentIntent(intent)
            color = ContextCompat.getColor(context, android.R.color.transparent)
            setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            setSound(alarmSound)
            setAutoCancel(true)
        }

        // Add channel when SDK > 26 (Oreo)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channel = NotificationChannel(
                    ALARM_CHANNEL_ID,
                    ALARM_CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                enableVibration(true)
                vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)
            }

            notification.setChannelId(ALARM_CHANNEL_ID)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(notificationId, notification.build())
    }

    // create alarm
    fun createAlarm(
            context: Context,
            tittle: String,
            message: String,
            requestCode: Int,
            time: Calendar
    ) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra(ALARM_TITTLE, tittle)
            putExtra(ALARM_MESSAGE, message)
        }
        val pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0)

        alarmManager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                time.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                pendingIntent
        )
    }

    // cancel alarm
    fun cancelAlarm(context: Context, requestCode: Int) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0).also {
            it.cancel()
        }

        alarmManager.cancel(pendingIntent)
    }


}