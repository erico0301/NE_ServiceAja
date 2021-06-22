package com.example.serviceaja.chatreview

import android.app.NotificationChannel
import android.app.NotificationChannelGroup
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import com.example.serviceaja.ActivityAddContact
import com.example.serviceaja.R
import com.example.serviceaja.checkout.ConfirmPaymentActivity
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.activity_on_going_transaction.*

class ChatActivity : AppCompatActivity() {

    var notifyChannel1 = 1
    var notificationManager: NotificationManager? = null
    var pesanUser = arrayOf("BMW", "Mitsubisi", "Peugeot")



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        chat_toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        createNotificationGroup()
        createNotificationChannel()
        var channel_id = ""
        var group_id = ""

        btnSend.setOnClickListener{
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O){
                group_id = "Channel 1"
                channel_id = notificationManager!!.getNotificationChannel(pesanUser[0] + "_" + group_id).id

                var notification = NotificationCompat.Builder(this, channel_id)
                        .setContentTitle(pesanUser[0])
                        .setContentText("Pesan Baru")
                        .setGroup(group_id)
                        .setSmallIcon(R.mipmap.ic_launcher)
                notificationManager!!.notify(notifyChannel1, notification.build())
            }
        }

        //Add Contact
        btnAddContact.setOnClickListener{
            var addContact = Intent(this, ActivityAddContact::class.java)
            startActivity(addContact)
        }
    }


    private fun createNotificationGroup(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val list = mutableListOf<NotificationChannelGroup>()

            list.add(NotificationChannelGroup("Channel 1", "Channel 1"))
            list.add(NotificationChannelGroup("Channel 2", "Channel 2"))
            notificationManager!!.createNotificationChannelGroups(list)
        }
    }

    private fun createNotificationChannel(){
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O){
            val notificationSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val att = AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_NOTIFICATION).build()
            for (s in pesanUser){
                val Channel1 = NotificationChannel(s + "_Channel 1", s, NotificationManager.IMPORTANCE_HIGH)
                Channel1.group = "Channel 1"
                Channel1.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 100)
                Channel1.setSound(notificationSound, att)
                Channel1.setLightColor(Color.RED)
                Channel1.enableLights(true)
                Channel1.enableVibration(true)

                val Channel2 = NotificationChannel(s + "_Channel 2", s, NotificationManager.IMPORTANCE_HIGH)
                Channel2.group = "Channel 2"
                Channel1.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 100)
                Channel1.setSound(notificationSound, att)
                Channel1.setLightColor(Color.RED)
                Channel1.enableLights(true)
                Channel1.enableVibration(true)

                if (notificationManager != null){
                    notificationManager!!.createNotificationChannel(Channel1)
                    notificationManager!!.createNotificationChannel(Channel2)
                }
            }
        }
    }

}