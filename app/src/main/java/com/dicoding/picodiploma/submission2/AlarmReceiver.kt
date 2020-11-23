package com.dicoding.picodiploma.submission2

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.dicoding.picodiploma.submission2.AlarmHelper.ALARM_MESSAGE
import com.dicoding.picodiploma.submission2.AlarmHelper.ALARM_TITTLE
import com.dicoding.picodiploma.submission2.activity.MainActivity

class AlarmReceiver : BroadcastReceiver() {

    companion object {
        // Siapkan 2 id untuk 2 macam alarm, onetime dan repeating
        private const val ALARM_ID_REPEATING = 101

    }

    override fun onReceive(context: Context, intent: Intent) {
        val title = intent?.getStringExtra(ALARM_TITTLE)
        val message = intent?.getStringExtra(ALARM_MESSAGE)

        val notificationIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, 0)

        context?.let {
            AlarmHelper.showNotification(
                    it,
                    title ?: "Tittle",
                    message ?: "Message",
                    ALARM_ID_REPEATING,
                    pendingIntent
            )
        }
    }


}