package com.example.serviceaja.LoginRegister

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.provider.Telephony
import android.telephony.SmsMessage
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.RemoteViews
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import com.example.serviceaja.*
import com.example.serviceaja.classes.User
import kotlinx.android.synthetic.main.activity_verification_code.*

class VerificationCodeActivity : AppCompatActivity() {
    private lateinit var notificationManager: NotificationManager
    private lateinit var user: User
    val NOTIFICATION_ID = "NOTIFICATION_WELCOME"

    val handler = object: Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            val kodeOTP = msg.obj as String
            verifikasi_kodeOtomatis1.text = kodeOTP.substring(0, 1)
            verifikasi_kodeOtomatis2.text = kodeOTP.substring(1, 2)
            verifikasi_kodeOtomatis3.text = kodeOTP.substring(2, 3)
            verifikasi_kodeOtomatis4.text = kodeOTP.substring(3)
            verifikasi_progressBar.visibility = View.GONE
            val animation = AnimationUtils.loadAnimation(this@VerificationCodeActivity, R.anim.animation_otp_appear)
            animation.duration = 500L
            verifikasi_kodeOtomatis1.startAnimation(animation)
            verifikasi_kodeOtomatis1.visibility = View.VISIBLE
            animation.startOffset = 500L
            verifikasi_kodeOtomatis2.startAnimation(animation)
            verifikasi_kodeOtomatis2.visibility = View.VISIBLE
            animation.startOffset = 1000L
            verifikasi_kodeOtomatis3.startAnimation(animation)
            verifikasi_kodeOtomatis3.visibility = View.VISIBLE
            animation.startOffset = 1500L
            verifikasi_kodeOtomatis4.startAnimation(animation)
            verifikasi_kodeOtomatis4.visibility = View.VISIBLE

            // Memanggil pembuatan notifikasi yang akan dikirimkan ke pengguna, setelah proses pembuatan notification channel dilakukan
            btnKonfirmasiKodeActivated()
        }
    }

    private val smsReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            Log.e("Message SMS", "Message received!")
            if (intent?.action.equals(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)) {
                verifikasi_progressBar.visibility = View.VISIBLE
                val pdus = (intent?.extras?.get("pdus") as Array<*>)
                var sms: SmsMessage
                var message: String
                val format = intent.extras?.getString("format")
                var kodeOTP = ""


                Thread( Runnable {
                    for (i in pdus) {
                        sms = SmsMessage.createFromPdu(i as ByteArray, format)
                        if (sms.originatingAddress.equals("08123456789") && sms.messageBody.contains("registrasi")) {
                            message = sms.messageBody
                            Log.e("Message", message)
                            var firstNum = '0'
                            for (i in message) {
                                if (i.toInt() in 48..57) {
                                    firstNum = i
                                    break
                                }
                            }
                            kodeOTP = message.substring(message.indexOf(firstNum), message.indexOf(firstNum) + 4)
                            Log.e("Kode OTP", kodeOTP)
                            break
                        }
                    }

                    Thread.sleep(5000)
                    val send = Message.obtain(handler)
                    send.obj = kodeOTP
                    send.sendToTarget()
                }).start()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verification_code)

        user = intent.getParcelableExtra(EXTRA_USER)!!

        // Notification Manager yang digunakan untuk membuat notification channel dan menciptakan notifikasi yang akan dikirimkan ke user setelah user
        // melewati tahap verifikasi pengguna
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        // Memanggil fungsi pembuatan notification channel yang ada dibagian bawah code
        createNotificationChannel()

        val welcomeIntent = Intent(this, WelcomeActivity::class.java)
        welcomeIntent.putExtra(EXTRA_USER, intent.getParcelableExtra<User>(EXTRA_USER))

        val filter = IntentFilter()
        filter.addAction(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)
        registerReceiver(smsReceiver, filter)

        if (!checkSelfPermission(android.Manifest.permission.RECEIVE_SMS).equals(PackageManager.PERMISSION_GRANTED)
                && !checkSelfPermission(android.Manifest.permission.READ_SMS).equals(PackageManager.PERMISSION_GRANTED)) {
            requestSMSPermission()
        }

        verifikasi_kodeOtomatis1.visibility = View.GONE
        verifikasi_kodeOtomatis2.visibility = View.GONE
        verifikasi_kodeOtomatis3.visibility = View.GONE
        verifikasi_kodeOtomatis4.visibility = View.GONE
        verifikasi_progressBar.visibility = View.GONE

        verifikasi_kode1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (verifikasi_kode1.text.toString().length == 1)
                    verifikasi_kode2.requestFocus()
            }

            override fun afterTextChanged(s: Editable?) {}
        })
        verifikasi_kode2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (verifikasi_kode2.text.toString().length == 1)
                    verifikasi_kode3.requestFocus()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        verifikasi_kode3.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (verifikasi_kode3.text.toString().length == 1)
                    verifikasi_kode4.requestFocus()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        konfirmasiKodeBtn.setBackgroundColor(getColor(R.color.gray))
    }
    // Fungsi yang digunakan untuk membuat notification channel
    private fun createNotificationChannel() {
        // Mengecek terlebih dahulu versi Android pada perangkat apakah merupakan perangkat bersistem Oreo atau lebih
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Membuat ringtone dan audio attribute untuk memasukkan nada suara pada saat notifikasi diterima
            val ringtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val audioAttributes = AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build()
            // Membangun notification channel
            val notificationChannel = NotificationChannel(NOTIFICATION_ID, "Welcome", NotificationManager.IMPORTANCE_HIGH).apply {
                enableLights(true)                                                      // Meng-enable LED yang menyala ketika layar dalam keadaan mati
                enableVibration(true)                                                // Meng-enable getaran yang akan terjadi ketika penerimaan notifikasi
                lightColor = Color.BLUE                                                        // Menetapkan warna LED yang digunakan
                vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 100)     // Menentukan pola getaran ketika notifikasi diterima
                setSound(ringtone, audioAttributes)                                             // Mengaplikasikan nada suara yang telah ditentukan sebelumnya pada channel
                setShowBadge(true)
            }
            notificationManager.createNotificationChannel(notificationChannel)                  // Membuat notification channel menggunakan notification manager
        }
    }

    // Fungsi yang digunakan untuk meng-enable button yang digunakan untuk berpindah aktivitas sekaligus mengirimkan notifikasi yang menandakan proses registrasi
    // telah berhasil dilakukan oleh user dan menciptakan notifikasi kustomisasi yang terdiri dari dua button yang digunakan untuk masuk ke aktivitas Edit Profil pengguna
    // dan aktivitas tambah daftar kendaraan pengguna
    private fun btnKonfirmasiKodeActivated() {
        // Mengubah warna button dan memberikan event onClickListener
        konfirmasiKodeBtn.setBackgroundColor(getColor(R.color.darkestBlue))
        konfirmasiKodeBtn.setOnClickListener {
            // Button digunakan untuk masuk ke aktivitas selanjutnya yaitu WelcomeActivity.kt
            val intent = Intent(this@VerificationCodeActivity, WelcomeActivity::class.java)
            intent.putExtra(EXTRA_USER, user)
            startActivity(intent)

            // Selain untuk masuk ke aktivitas selanjutnya, button juga dipakai untuk mengirimkan notifikasi
            // Baris kode dibawah ini untuk membuat intent masuk ke halaman home (homepage) dari aplikasi ketika notifikasi diklik (dimana saja), dan juga mengirimkan
            // extra yang berisi informasi pengguna yang baru saja mendaftar
            val notificationIntent = Intent(this, HomeActivity::class.java)
            notificationIntent.putExtra(EXTRA_USER, user)

            // Baris kode dibawah ini untuk membuat intent masuk ke halaman edit profil (EditProfilUser.kt) dari aplikasi ketika button "Edit Profile" yang terdapat pada
            // notifikasi (custom notification) di-click dan juga mengirimkan extra yang berisi informasi pengguna yang baru saja mendaftar
            val editProfilIntent = Intent(this, EditProfilUser::class.java)
            editProfilIntent.putExtra(EXTRA_USER, user)

            // Baris kode dibawah ini untuk membuat intent masuk ke halaman edit profil (KendaraanActivity.kt) dari aplikasi ketika button "Tambah Kendaraan" yang terdapat pada
            // notifikasi (custom notification) di-click dan juga mengirimkan extra yang berisi informasi pengguna yang baru saja mendaftar
            val tambahKendaraanIntent = Intent(this, KendaraanActivity::class.java)
            tambahKendaraanIntent.putExtra(EXTRA_USER, user)

            // Membuat pending intent untuk masing-masing intent yang telah dibuat pada baris kode diatas agar dapat dijalankan oleh notifikasi ketika event klik tertentu
            // dilakukan
            val taskStack = TaskStackBuilder.create(this)
                    .addNextIntentWithParentStack(editProfilIntent)
            taskStack.editIntentAt(0)?.putExtra(EXTRA_USER, user)
            val notificationPendingIntent = taskStack.getPendingIntent(101, PendingIntent.FLAG_UPDATE_CURRENT)

            val editProfilPendingIntent = PendingIntent.getActivity(this, 0, editProfilIntent, PendingIntent.FLAG_UPDATE_CURRENT)
            val tambahKendaraanPendingIntent = PendingIntent.getActivity(this, 0, tambahKendaraanIntent, PendingIntent.FLAG_UPDATE_CURRENT)

            // Mengenerasikan layout dari custom notification yang telah dibuat dengan menggunakan RemoteViews, sehingga dapat ditampilkan sebagai notifikasi
            val collapsedLayout = RemoteViews(packageName, R.layout.layout_notification_collapsed)
            val expandedLayout = RemoteViews(packageName, R.layout.layout_notification_expanded)

            // Mendaftarkan event onClick pada kedua button yang terdapat pada layout custom notification expanded agar dapat menjalankan pending intent yang telah
            // diinisialisasikan sebelumnya di baris kode di atas
            expandedLayout.setOnClickPendingIntent(R.id.notificationExpanded_btnEditProfil, editProfilPendingIntent)
            expandedLayout.setOnClickPendingIntent(R.id.notificationExpanded_btnTambahKendaraan, tambahKendaraanPendingIntent)

            // Membuat notifikasi dengan NotificationCompat.Builder()
            val notification = NotificationCompat.Builder(this, NOTIFICATION_ID)
                .setSmallIcon(R.drawable.ic_logo)                           // Menentukan small icon (ikon kecil yang terdapat pada ujung kiri atas dari layout notifikasi

                .setContentIntent(notificationPendingIntent)                // Mendaftarkan pending intent berisi TaskStack yang telah dibuat sebelumnya

                .setStyle(NotificationCompat.DecoratedCustomViewStyle())    // Mendaftarkan Custom View Style agar layout custom notification dapat dijalankan

                .setCustomContentView(collapsedLayout)                      // Menentukan layout custom notification yang digunakan pada saat notifikasi pop-up (muncul ketika
                                                                            // menerima notifikasi) serta pada tampilan notifikasi dalam bentuk kecil (tidak expanded)

                .setCustomBigContentView(expandedLayout)                    // Menentukan layout custom notification yang digunakan pada saat notifikasi dalam keadaan expanded
                                                                            // dengan menekan tombol arrow down kecil di samping penunjuk waktu penerimaan notifikasi
                .build()                                                    // Membangun notifikasi

            notificationManager.notify(0, notification)                 // Mengirimkan notifikasi ke perangkat
        }
    }

    private fun requestSMSPermission() {
        requestPermissions(arrayOf(android.Manifest.permission.RECEIVE_SMS, android.Manifest.permission.READ_SMS), 1)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            for (i in grantResults)
                if (i != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Harap beri izin membaca SMS agar proses registrasi dapat dilakukan", Toast.LENGTH_SHORT).show()
                    requestSMSPermission()
                }
        }
    }
}