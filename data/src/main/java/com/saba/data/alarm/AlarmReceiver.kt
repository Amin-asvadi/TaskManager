package com.saba.data.alarm

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.saba.base_android.uiles.Constant.ALARM_TRIGGER
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
@AndroidEntryPoint
class AlarmReceiver: BroadcastReceiver() {
    @Inject
    lateinit var notificationBuilder: NotificationCompat.Builder

    @Inject
    lateinit var notificationManager: NotificationManager
    override fun onReceive(context: Context?, intent: Intent?) {

        val message = intent?.getStringExtra(ALARM_TRIGGER) ?: return
        println("Alarm triggered: $message")

        val notification = notificationBuilder
            .setContentTitle("Alarm Triggered")
            .setContentText(message)
            .build()
        notificationManager.notify(10, notification)
    }
}