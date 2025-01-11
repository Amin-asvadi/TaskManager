package com.saba.data.local
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.net.toUri
import com.saba.common_ui_resources.R
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
object NotificationModule {

    @Singleton
    @Provides
    fun provideNotificationBuilder(
        @ApplicationContext context: Context
    ): NotificationCompat.Builder {
        val channelId = "phone_call_notification_channel"
        return  NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.sunny_24)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVibrate(longArrayOf(0, 500, 200, 500))  // ویبره مشابه زنگ خوردن
            .setDefaults(NotificationCompat.DEFAULT_LIGHTS or NotificationCompat.DEFAULT_SOUND)
            .setAutoCancel(true)
            .setUsesChronometer(true)
            .setSound(null)
            .setVibrate(longArrayOf(0, 500, 200, 500))
            .setCategory(Notification.CATEGORY_ALARM)
            .setOngoing(true)
            .setTimeoutAfter(30000)
    }

    @Singleton
    @Provides
    fun provideNotificationManager(
        @ApplicationContext context: Context
    ): NotificationManager {

        val channelId = "phone_call_notification_channel"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(         channelId,"Main Channel", importance)
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
        return notificationManager
    }
}
