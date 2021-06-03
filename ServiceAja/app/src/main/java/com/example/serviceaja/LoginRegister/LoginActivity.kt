package com.example.serviceaja.LoginRegister

import DBClass.DBUser
import android.annotation.TargetApi
import android.app.Notification
import android.content.DialogInterface
import android.content.Intent
import android.media.AudioManager
import android.media.SoundPool
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import com.example.serviceaja.*
import com.example.serviceaja.classes.AccountSharedPref
import com.example.serviceaja.classes.DBHelper

import com.example.serviceaja.classes.User
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_search.*

class LoginActivity : AppCompatActivity() {
    private lateinit var users: ArrayList<User>
    var sp : SoundPool? = null
    var soundID : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val db = DBHelper(this)

        var tempUser = User("admin", "admin@gmail.com", "085270047772", "admin12345")
        db.addUser(tempUser)

        users = db.getAllUsers()

        for (i in users)
            Log.e("Data", "${i.noTelp}, ${i.nama}, ${i.email}, ${i.password}")

        // Object Handler yang digunakan untuk menerima pesan yang akan ditampilkan ketika terjadi kesalahan input field Login (ada field yang kosong)
        val handlerThread = object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message) {
                // Pesan diterima melalui parameter msg (yang merupakan objek dari class Message) yang memiliki atribut obj dan juga arg1 dan arg2
                val errorMessage = msg.obj as String  // Pesan yang diterima
                // arg1 = 1 berarti msg dikirimkan untuk memberikan pesan error pada field input email/no.telepon, seperti yang ditetapkan di code bawah
                if (msg.arg1 == 1)
                    halamanLogin_inputEmail.error = errorMessage
                // arg1 = 2 untuk msg yang dikirimkan untuk memberikan pesan error pada field input password (nilai arg tergantung pada programmer)
                else
                    halamanLogin_inputPassword.error = errorMessage
            }
        }

        halamanLogin_inputEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Menjalankan thread ketika terjadi perubahan isi pada field email/no.telepon
                Thread(Runnable {
                    // Thread akan mengecek isi dari field terlebih dahulu, jika kosong, maka akan mengirimkan pesan error tentang field email yang belum diisi
                    // ke handler yang telah disediakan
                    if (halamanLogin_inputEmail.text.toString() == "") {
                        val msg = Message.obtain(handlerThread)
                        msg.obj = "Isi dengan E-mail atau No. Telepon Terdaftar"
                        msg.arg1 = 1
                        msg.sendToTarget()
                    }
                    // Jika telah terisi dan tidak menimbulkan error, thread akan menjalankan fungsi cekValiditasForm() untuk mengecek kembali apakah seluruh field
                    // termasuk field e-mail/no.telepon dan field password telah terisi dengan benar. Action yang akan dijalankan dijelaskan dibagian fungsi.
                    cekValiditasForm()
                }).start()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        halamanLogin_inputPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Menjalankan thread ketika terjadi perubahan isi pada field input password
                Thread(Runnable {
                    // Thread akan mengecek isi dari field terlebih dahulu, jika kosong, maka akan mengirimkan pesan error tentang field password yang belum diisi
                    // ke handler yang telah disediakan
                    if (halamanLogin_inputPassword.text.toString() == "") {
                        val msg = Message.obtain(handlerThread)
                        msg.obj = "Harap isi Password Anda"
                        msg.arg1 = 2
                        msg.sendToTarget()
                    }
                    // Jika telah terisi dan tidak menimbulkan error, thread akan menjalankan fungsi cekValiditasForm() untuk mengecek kembali apakah seluruh field
                    // termasuk field e-mail/no.telepon dan field password telah terisi dengan benar. Action yang akan dijalankan dijelaskan dibagian fungsi.
                    cekValiditasForm()
                }).start()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        halamanLogin_btnDaftar.setOnClickListener {
            val register = Intent(this, RegisterActivity::class.java)
            register.putExtra(EXTRA_USERS, users)
            startActivity(register)
            finish()
        }
    }


    fun cekValiditasForm() {
        // 2 baris kode dibawah untuk mengambil isi dari edit text input email dan input password
        val emailOrPhoneNumber = halamanLogin_inputEmail.text.toString()
        val password = halamanLogin_inputPassword.text.toString()

        // Jika salah satu dari kedua field tersebut kosong, maka button masih disable, dan login tidak dapat dilakukan, ditandai dengan latar button yang berwarna
        // abu-abu dan belum di set event OnClickListener pada button login, seperti yang dilakukan pada baris kode dalam if dibawah ini
        if (emailOrPhoneNumber == "" || password == "") {
            halamanLogin_btnMasuk.setBackgroundColor(getColor(R.color.gray))
            halamanLogin_btnMasuk.setOnClickListener {
                if(soundID!=0) {
                    sp?.play(soundID,.99f,.99f,1,0,.99f)
                }
            }
            return
        }

        // Sampai ke tahapan ini berarti tidak ada field yang kosong, maka sudah dapat dilakukan login, ditandai dengan latar button berwarna biru dan sudah di-set event
        // OnClickListener pada button
        halamanLogin_btnMasuk.setBackgroundColor(getColor(R.color.darkestBlue))
        // Event OnClickListener akan mengecek validasi dari login yang akan dilakukan
        halamanLogin_btnMasuk.setOnClickListener {
            var user: User? = null
            // Perulangan dilakukan pada daftar user untuk mencari apakah ada user yang terdaftar dengan email ataupun no. telepon yang diinput
            for (i in users) {
                if (emailOrPhoneNumber == i.email || emailOrPhoneNumber == i.noTelp) {
                    user = i
                    break
                }
            }
            // Jika tidak ada, maka akan dipanggil fungsi loginFailed() yang berfungsi untuk menampikan dialog yang menyatakan proses login gagal
            if (user == null) loginFailed()
            // Jika ditemukan user tersebut, maka akan dicek apakah password yang dimasukkan sama dengan password yang dimiliki oleh user yang bersangkutan
            // Jika salah, maka akan memanggil fungsi loginFailed()
            else if (password != user.password) loginFailed()
            else {
                var currentUser = emailOrPhoneNumber
                // Jika user ditemukan, dan password  yang dimasukkan benar, maka button akan membawa masuk ke intent baru yaitu intent home, menandakan proses login
                    // berhasil dilakukan
                val home = Intent(this, HomeActivity::class.java)
                home.putExtra(EXTRA_USER, user)

                val sharedPref = AccountSharedPref(this)
                sharedPref.email = user.email

                startActivity(home)
                finishAffinity()
            }
        }
    }

    // Fungsi loginFailed() untuk menampilkan dialog yang menandai proses login gagal dilakukan dikarenakan user yang tidak ditemukan dengan email/password yang diinput
    // atau password yang tidak cocok dengan password yang dimiliki oleh akun yang bersangkutan
    private fun loginFailed() {
        val dialog = AlertDialog.Builder(this)
                .setTitle("Login Gagal")
                .setMessage("E-mail/No. Telepon atau Password yang dimasukkan salah")
                .setPositiveButton("OK", DialogInterface.OnClickListener { dialogInterface: DialogInterface, i: Int -> })
        if(soundID!=0) {
            sp?.play(soundID,.99f,.99f,1,0,.99f)
        }
        dialog.show()
    }

    fun forget(view: View) {
        startActivity(Intent(this, ForgetPasswordActivity::class.java))
    }

    fun loginGmail(view: View) {}
    fun loginFB(view: View) {}

    override fun onStart() {
        super.onStart()
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            createNewSoundPool()
        else
            createOldSoundPool()
        soundID = sp?.load(this, R.raw.error,1) ?: 0
    }
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun createNewSoundPool() {
        sp = SoundPool.Builder()
                .setMaxStreams(15)
                .build()
    }
    @Suppress("DEPRECATION")
    private fun createOldSoundPool() {
        sp = SoundPool(15, AudioManager.STREAM_MUSIC,0)
    }

    override fun onStop() {
        super.onStop()
        sp?.release()
        sp = null
    }
}

