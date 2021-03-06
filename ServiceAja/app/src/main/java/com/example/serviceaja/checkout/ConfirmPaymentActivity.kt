package com.example.serviceaja.checkout

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
import android.widget.ArrayAdapter
import androidx.core.app.NotificationCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.serviceaja.HomeActivity
import com.example.serviceaja.R
import com.example.serviceaja.recyclerview.RecyclerViewCheckoutProductServiceDetails
import kotlinx.android.synthetic.main.activity_checkout.recyclerViewServiceProductDetails
import kotlinx.android.synthetic.main.activity_confirm_payment.*

class ConfirmPaymentActivity : AppCompatActivity() {

    private var layoutManager : RecyclerView.LayoutManager? = null
    private var adapter : RecyclerView.Adapter<RecyclerViewCheckoutProductServiceDetails.ViewHolder>? = null

    var notifyChannel2 = 2
    var notificationManager: NotificationManager? = null
    var prosesTransaksi = arrayOf("Menunggu Pembayaran", "Pembayaran Terkonfirmasi", "Menunggu Konfirmasi Penjual", "Pesanan diproses", "Pesanan dalam pengiriman", "Pesanan Sampai", "Pesanan Selesai")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_payment)



        backBtn.setOnClickListener {
            onBackPressed()
        }

        layoutManager = LinearLayoutManager (this)
        recyclerViewServiceProductDetails.layoutManager = layoutManager
        adapter = RecyclerViewCheckoutProductServiceDetails()
        recyclerViewServiceProductDetails.adapter = adapter

        val adapter = ArrayAdapter.createFromResource(this, R.array.metodePembayaran,
            android.R.layout.simple_spinner_dropdown_item)
        paymentMethodSpinner.adapter = adapter


        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        createNotificationGroup()
        createNotificationChannel()
        var channel_id = ""
        var group_id = ""

        payBtn.setOnClickListener {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O){
                group_id = "Channel 2"
                channel_id = notificationManager!!.getNotificationChannel(prosesTransaksi[2] + "_" + group_id).id

                var notification = NotificationCompat.Builder(this, channel_id)
                        .setContentTitle(prosesTransaksi[2])
                        .setContentText("Silakan tunggu proses konfirmasi dari penjual")
                        .setGroup(group_id)
                        .setSmallIcon(R.mipmap.ic_launcher)
                notificationManager!!.notify(notifyChannel2, notification.build())
            }

            var homeIntent = Intent(this, HomeActivity::class.java)
            startActivity(homeIntent )
            finishAffinity()
            
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