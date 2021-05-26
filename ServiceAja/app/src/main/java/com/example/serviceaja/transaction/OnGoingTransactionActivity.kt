package com.example.serviceaja.transaction

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.serviceaja.R
import com.example.serviceaja.recyclerview.RVTransactionServiceProductPreview
import kotlinx.android.synthetic.main.activity_on_going_transaction.*

class OnGoingTransactionActivity : AppCompatActivity() {

    private var layoutManager : RecyclerView.LayoutManager? = null
    private var adapter : RecyclerView.Adapter<RVTransactionServiceProductPreview.ViewHolder>? = null
    var notif : NotificationCompat.Builder? = null
    var notifManager : NotificationManager? = null
    val notifChannel = 2
    //Simulasi jumlah unread notif
    var notifCount = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_going_transaction)

        detailTransaksi_toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        layoutManager = LinearLayoutManager (this)
        detailTransaksi_rvItem.layoutManager = layoutManager
        adapter = RVTransactionServiceProductPreview()
        detailTransaksi_rvItem.adapter = adapter

        notifManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannels()

        doneBtn.setOnClickListener {
            var channel_id = "DoneTrans"
            //membuat notification
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                notif = NotificationCompat
                        .Builder(this, channel_id)
                        .setContentTitle("Pesanan Selesai")
                        .setContentText("Stay Healthy and enjoy driving")
                        .setSmallIcon(R.drawable.mitsubishi_logo)
                        .setNumber(notifCount) //untuk menampilkan jumlah notif jika > 1
                        .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL) //menentukan besar kecilnya bagde icon

                notifManager?.notify(notifChannel, notif?.build())

                notifCount+=1

                finish()
            }

        }
    }

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //make sound
            val notifSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val att = AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build()

            val channel = NotificationChannel("DoneTrans", "Done",
                    NotificationManager.IMPORTANCE_DEFAULT)
            channel.setSound(notifSound, att)
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(100,200,300,400,500, 400, 300, 200, 100)

            notifManager?.createNotificationChannel(channel)
        }
    }
}