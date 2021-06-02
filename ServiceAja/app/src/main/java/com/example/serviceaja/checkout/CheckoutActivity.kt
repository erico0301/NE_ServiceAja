package com.example.serviceaja.checkout

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.serviceaja.AlarmManager.AlarmReceiver
import com.example.serviceaja.AlarmManager.EXTRA_PESAN
import com.example.serviceaja.R
import com.example.serviceaja.recyclerview.RecyclerViewCheckoutProductServiceDetails
import kotlinx.android.synthetic.main.activity_checkout.*
import java.util.*

class CheckoutActivity : AppCompatActivity() {

    private var layoutManager : RecyclerView.LayoutManager? = null
    private var adapter : RecyclerView.Adapter<RecyclerViewCheckoutProductServiceDetails.ViewHolder>? = null

    var notifyChannel2 = 2
    var notificationManager: NotificationManager? = null
    var prosesTransaksi = arrayOf("Menunggu Pembayaran", "Pembayaran Terkonfirmasi", "Menunggu Konfirmasi Penjual", "Pesanan diproses", "Pesanan dalam pengiriman", "Pesanan Sampai", "Pesanan Selesai")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        layoutManager = LinearLayoutManager (this)
        recyclerViewServiceProductDetails.layoutManager = layoutManager
        adapter = RecyclerViewCheckoutProductServiceDetails()
        recyclerViewServiceProductDetails.adapter = adapter

        val adapter = ArrayAdapter.createFromResource(this, R.array.lokasispinner,
            android.R.layout.simple_spinner_dropdown_item)
        lokasiSpinner.adapter = adapter

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        createNotificationGroup()
        createNotificationChannel()
        var channel_id = ""
        var group_id = ""



        var mAlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val requestCode = 101
        var mPendingIntent: PendingIntent? = null
        var sendIntent: Intent? = null


        paymentBtn.setOnClickListener {
            if (mPendingIntent != null) {
                mAlarmManager.cancel(mPendingIntent)
                mPendingIntent?.cancel()
            }
            var setAlarmTime = Calendar.getInstance()
            setAlarmTime.add(Calendar.SECOND, 10)

            sendIntent = Intent(this, AlarmReceiver::class.java)
            sendIntent?.putExtra(EXTRA_PESAN, "Buruan Bayar Belajaan anda ... !!!")
            mPendingIntent = PendingIntent.getBroadcast(this, requestCode, sendIntent, 0)
            mAlarmManager.set(AlarmManager.RTC_WAKEUP, setAlarmTime.timeInMillis, mPendingIntent)
            Toast.makeText(
                    this,
                    "Alarm Manger Telah Dibuat",
                    Toast.LENGTH_SHORT
            ).show()

            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O){
                group_id = "Channel 2"
                channel_id = notificationManager!!.getNotificationChannel(prosesTransaksi[0] + "_" + group_id).id

                var notification = NotificationCompat.Builder(this, channel_id)
                        .setContentTitle(prosesTransaksi[0])
                        .setContentText("Silakan Bayar Belanjaan anda")
                        .setGroup(group_id)
                        .setSmallIcon(R.mipmap.ic_launcher)
                notificationManager!!.notify(notifyChannel2, notification.build())
            }


            var confirmPaymentIntent = Intent(this, ConfirmPaymentActivity::class.java)
            startActivity(confirmPaymentIntent)


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
            for (s in prosesTransaksi){
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