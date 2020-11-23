package com.dicoding.picodiploma.submission2

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.dicoding.picodiploma.submission2.AlarmHelper.ALARM_MESSAGE
import com.dicoding.picodiploma.submission2.AlarmHelper.ALARM_TITTLE
import com.dicoding.picodiploma.submission2.activity.MainActivity

class AlarmReceiver : BroadcastReceiver() {

    companion object {
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