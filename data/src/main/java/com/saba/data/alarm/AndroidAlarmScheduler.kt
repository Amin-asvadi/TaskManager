package com.saba.data.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.saba.base_android.uiles.Constant.ALARM_TRIGGER
import com.saba.base_android.uiles.reminderToLocalDateTime
import com.saba.data.model.local.TaskEntity
import dagger.hilt.android.qualifiers.ApplicationContext
import java.time.LocalDateTime
import java.time.ZoneId
import javax.inject.Inject

class AndroidAlarmScheduler @Inject constructor(
    @ApplicationContext val context: Context
) : AlarmScheduler {

    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    override fun schedule(item: TaskEntity) {
        val reminderTime = item.reminderTime ?: return

        val dateTime = reminderTime.reminderToLocalDateTime() ?: LocalDateTime.now()

        val epochMillis = dateTime.atZone(ZoneId.systemDefault()).toEpochSecond() * 1000

        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra(ALARM_TRIGGER, item.title)
        }

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            epochMillis,
            PendingIntent.getBroadcast(
                context,
                item.id,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }

    override fun cancel(item: TaskEntity) {
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                item.id,
                Intent(context, AlarmReceiver::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }
}