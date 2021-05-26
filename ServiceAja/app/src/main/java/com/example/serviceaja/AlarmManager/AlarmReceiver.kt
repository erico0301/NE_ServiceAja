package com.example.serviceaja.AlarmManager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.serviceaja.R

const val EXTRA_PESAN: String = "EXTRA_PESAN"
class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val Notifyid = 30103
        val Channel_id = "my_channel_01"
        val name = "ON/OFF"
        val Importance = NotificationManager.IMPORTANCE_HIGH
        val nNotifyChannel = NotificationChannel(Channel_id, name, Importance)

        val mBuilder = NotificationCompat.Builder(context!!, Channel_id)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentText(intent?.getStringExtra(EXTRA_PESAN))
                .setContentTitle("Payment")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        var mNotificationManager = context
                .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        for (s in mNotificationManager.notificationChannels ){
            mNotificationManager.deleteNotificationChannel(s.id)
        }
        mNotificationManager.createNotificationChannel(nNotifyChannel)
        mNotificationManager.notify(Notifyid,mBuilder.build())
    }
}