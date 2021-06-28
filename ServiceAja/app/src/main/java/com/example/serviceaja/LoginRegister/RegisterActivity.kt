package com.example.serviceaja.LoginRegister

import DBClass.DBUser
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.provider.BaseColumns
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.CompoundButton
import android.widget.Toast
import androidx.core.content.getSystemService
import com.example.serviceaja.*
import com.example.serviceaja.classes.DBHelper
import com.example.serviceaja.classes.User
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_verification_code.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class RegisterActivity : AppCompatActivity() {
    private lateinit var users: ArrayList<User>
    private lateinit var db: DBHelper
    private lateinit var user: User

    // Variabel untuk mengecek koneksi
    private var connectionStatus = false
    private var phoneNumChecked = false
    private var phoneNumAvailable = false
    private var emailAddressChecked = false
    private var emailAddressAvailable = false

    private val checkDataAvailabilityReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            Log.e("Receiver received", "$phoneNumAvailable, $emailAddressAvailable")
            when (intent?.action) {
                PHONE_NUM_USED -> {
                    phoneNumChecked = true
                    phoneNumAvailable = false
                }
                PHONE_NUM_AVAILABLE -> {
                    phoneNumChecked = true
                    phoneNumAvailable = true
                }
                EMAIL_ADDRESS_USED -> {
                    emailAddressChecked = true
                    emailAddressAvailable = false
                }
                EMAIL_ADDRESS_AVAILABLE -> {
                    emailAddressChecked = true
                    emailAddressAvailable = true
                }
            }

            if (phoneNumChecked && emailAddressChecked) {
                showDialogDataChecked()
            }
        }
    }

    private fun showDialogDataChecked() {
        Log.e("showDialogDataChecked called", "$phoneNumAvailable, $emailAddressAvailable")
        if (phoneNumAvailable && emailAddressAvailable) {
            Toast.makeText(this, "Anda Berhasil Mendaftar!", Toast.LENGTH_LONG).show()
            var verificationCodeIntent = Intent(this, VerificationCodeActivity::class.java)
            verificationCodeIntent.putExtra(EXTRA_USER, user)
            startActivity(verificationCodeIntent)
        }
        else if (phoneNumAvailable && !emailAddressAvailable) {
            Toast.makeText(this, "E-mail yang digunakan telah terdaftar. Silahkan gunakan alamat e-mail lain.", Toast.LENGTH_SHORT).show()
            halamanDaftar_alamatEmail.hasFocus()
            halamanDaftar_alamatEmail.error = "Alamat E-mail telah terdaftar"
        }
        else if (!phoneNumAvailable && emailAddressAvailable) {
            Toast.makeText(this, "No. Telepon yang digunakan telah terdaftar. Silahkan gunakan no. telepon lain.", Toast.LENGTH_SHORT).show()
            halamanDaftar_noTelepon.hasFocus()
            halamanDaftar_noTelepon.error = "No. Telepon telah terdaftar"
        }
        else {
            Toast.makeText(this, "No. Telepon dan alamat E-mail yang digunakan telah terdaftar. Silahkan gunakan no. telepon dan alamat e-mail lain.", Toast.LENGTH_SHORT).show()
            halamanDaftar_noTelepon.hasFocus()
            halamanDaftar_noTelepon.error = "No. Telepon telah terdaftar"
            halamanDaftar_alamatEmail.error = "Alamat E-mail telah terdaftar"
        }

        phoneNumChecked = false
        emailAddressChecked = false
    }

    // Broadcast Receiver untuk menangkap status konektivitas perangkat
    private var networkReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            // Mendapatkan objek ConnectivityManager untuk mengecek status jaringan yang aktif
            val connection = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
            // Memasukkan nilai konektivitas jaringan (aktif atau tidak) ke dalam variabel connectionStatus yang
            // digunakan untuk pengecekan pada saat akan dilakukan registrasi
            connectionStatus = connection.activeNetwork != null
        }
    }
    // Intent Filter untuk menangkap perubahan status koneksi yang terjadi
    val filter = IntentFilter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)

        registerReceiver(checkDataAvailabilityReceiver, IntentFilter(PHONE_NUM_AVAILABLE))
        registerReceiver(checkDataAvailabilityReceiver, IntentFilter(PHONE_NUM_USED))
        registerReceiver(checkDataAvailabilityReceiver, IntentFilter(EMAIL_ADDRESS_AVAILABLE))
        registerReceiver(checkDataAvailabilityReceiver, IntentFilter(EMAIL_ADDRESS_USED))

        db = DBHelper(this)
        users = db.getAllUsers()

        connectionStatus = (getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetwork != null

        halamanDaftar_btnLogin.setOnClickListener {
            val login = Intent(this, LoginActivity::class.java)
            login.putExtra(EXTRA_USERS, users)

            startActivity(login)
            finish()
        }

        halamanDaftar_namaLengkap.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Dilakukan proses asynchronous yaitu memanggil fungsi cekValiditasForm() yang berfungsi untuk mengecek setiap inputan pada seluruh field yang terdapat pada
                // halaman register ini. Jika seluruh inputan benar dan valid, maka proses register akan dapat dilanjutkan ke tahap berikutnya.
                doAsync {
                    if (halamanDaftar_namaLengkap.text.toString() == "")
                        uiThread { halamanDaftar_namaLengkap.error = "Nama Lengkap tidak boleh kosong" }
                    val validation = cekValiditasForm()
                    // UI Thread ini untuk mengubah tampilan pada button register, yang dimana button pertama kali di-set disable (tidak memiliki event onClickListener), dan
                    // ditandai dengan latar berwarna abu-abu. Button yang telah berfungsi dan telah disematkan event OnClickListener akan memiliki warna dasar biru tua
                    uiThread {
                        enableDisableBtnDaftar(validation)
                    }
                }
            }
        })

        halamanDaftar_namaLengkap.setOnFocusChangeListener { view: View, b: Boolean -> cekValiditasForm() }

        halamanDaftar_alamatEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                doAsync {
                    // Proses asynchronous yang dilakukan dengan memanggil fungsi cekEmail() terlebih dahulu untuk memastikan email yang dimasukkan valid dan tidak pernah
                    // terdaftar sebelumnya
                    if (halamanDaftar_alamatEmail.text.toString() == "")
                        uiThread {
                            halamanDaftar_alamatEmail.error = "Alamat E-mail tidak boleh kosong"
                        }
                    if (!cekEmail(halamanDaftar_alamatEmail.text.toString()))
                        // Jika e-mail tidak valid, maka akan menampilkan UI berupa pesan error pada field e-mail
                        uiThread {
                            halamanDaftar_alamatEmail.error = "Alamat E-mail telah terdaftar"
                        }
                    else {
                        // JIka e-mail valid, maka akan dijalankan fungsi cekValiditasForm() yang berfungsi untuk mengecek validitas input dari seluruh field yang nantinya akan
                            // digunakan untuk mengubah tampilan pada button register agar dapat ditekan atau tidak sama sekali
                        val validation = cekValiditasForm()
                        uiThread {
                            enableDisableBtnDaftar(validation)
                        }
                    }
                }
            }
        })
        // Event yang dijalankan setiap kali fokus pada field e-mail berubah
        halamanDaftar_alamatEmail.setOnFocusChangeListener { view: View, b: Boolean ->
            // Proses asynchronous yang dilakukan dengan memanggil fungsi cekEmail() terlebih dahulu untuk memastikan email yang dimasukkan valid dan tidak pernah terdaftar
            // sebelumnya
            doAsync {
                if (!cekEmail(halamanDaftar_alamatEmail.text.toString()))
                // Jika e-mail tidak valid, maka akan menampilkan UI berupa pesan error pada field e-mail
                    uiThread {
                        halamanDaftar_alamatEmail.error = "Alamat E-mail telah terdaftar"
                    }
                // JIka e-mail valid, maka akan dijalankan fungsi cekValiditasForm() yang berfungsi untuk mengecek validitas input dari seluruh field yang nantinya akan
                // digunakan untuk mengubah tampilan pada button register agar dapat ditekan atau tidak sama sekali
                else {
                    val validation = cekValiditasForm()
                    uiThread {
                        enableDisableBtnDaftar(validation)
                    }
                }
            }
        }

        halamanDaftar_noTelepon.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
            // Event yang dijalankan setiap kali menerima inputan pada field no. telepon
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Proses asynchronous yang dilakukan dengan memanggil fungsi cekNoTelp() terlebih dahulu untuk memastikan no. telepon yang dimasukkan valid dan
                // tidak pernah terdaftar sebelumnya
                doAsync {
                    if (halamanDaftar_noTelepon.text.toString() == "")
                        uiThread { halamanDaftar_noTelepon.error = "No. Telepon tidak boleh kosong" }
                    if (!cekNoTelp(halamanDaftar_noTelepon.text.toString()))
                        // Jika no. telepon tidak valid, maka akan menampilkan UI berupa pesan error pada field no. telepon
                        uiThread { halamanDaftar_noTelepon.error = "No. Telepon telah terdaftar" }
                    // Jika no. telepon valid, maka akan dijalankan fungsi cekValiditasForm() yang berfungsi untuk mengecek validitas input dari seluruh field yang nantinya
                    // akan digunakan untuk mengubah tampilan pada button register agar dapat ditekan atau tidak sama sekali
                    else {
                        val validation = cekValiditasForm()
                        uiThread {
                            enableDisableBtnDaftar(validation)
                        }
                    }
                }
            }
        })

        // Event yang dijalankan setiap kali terjadi perubahan fokus yang melibatkan field pengisian no. telepon
        halamanDaftar_noTelepon.setOnFocusChangeListener { view: View, b: Boolean ->
            // Proses asynchronous yang dilakukan dengan memanggil fungsi cekNoTelp() terlebih dahulu untuk memastikan no. telepon yang dimasukkan valid dan
            // tidak pernah terdaftar sebelumnya
            doAsync {
                if (!cekNoTelp(halamanDaftar_noTelepon.text.toString()))
                // Jika no. telepon tidak valid, maka akan menampilkan UI berupa pesan error pada field no. telepon
                    uiThread { halamanDaftar_noTelepon.error = "No. Telepon telah terdaftar" }
                // Jika no. telepon valid, maka akan dijalankan fungsi cekValiditasForm() yang berfungsi untuk mengecek validitas input dari seluruh field yang nantinya akan
                // digunakan untuk mengubah tampilan pada button register agar dapat ditekan atau tidak sama sekali
                else {
                    val validation = cekValiditasForm()
                    uiThread {
                        enableDisableBtnDaftar(validation)
                    }
                }
            }
        }

        halamanDaftar_password.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
            // Event yang dijalankan setiap kali terjadi perubahan pada isi field no. telepon
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Proses asynchronous yang dilakukan dengan memanggil fungsi cekPassword() terlebih dahulu untuk memastikan password yang dimasukkan telah memenuhi syarat
                // yaitu sebanyak 8 karakter
                doAsync {
                    if (!cekPassword(halamanDaftar_password.text.toString()))
                        // Jika password tidak valid, maka akan menampilkan UI berupa pesan error pada field password
                        uiThread {
                            halamanDaftar_password.error = "Password wajib minimal 8 karakter campuran huruf dan angka"
                        }
                    // Jika password valid, maka akan dijalankan fungsi cekValiditasForm() yang berfungsi untuk mengecek validitas input dari seluruh field yang nantinya akan
                    // digunakan untuk mengubah tampilan pada button register agar dapat ditekan atau tidak sama sekali
                    else {
                        val validation = cekValiditasForm()
                        uiThread {
                            enableDisableBtnDaftar(validation)
                        }
                    }
                }
            }
        })

        // Event yang dijalankan ketika terjadi perubahan fokus yang berpengaruh pada field password
        halamanDaftar_password.setOnFocusChangeListener { view: View, b: Boolean ->
            // Proses asynchronous yang dilakukan dengan memanggil fungsi cekPassword() terlebih dahulu untuk memastikan password yang dimasukkan telah memenuhi syarat
            // yaitu sebanyak 8 karakter dan memenuhi ketentuan tertentu sesuai dengan Regex yang telah dibentuk
            doAsync {
                if (!cekPassword(halamanDaftar_password.text.toString()))
                // Jika password tidak valid, maka akan menampilkan UI berupa pesan error pada field password
                    uiThread {
                        halamanDaftar_password.error = "Password wajib minimal 8 karakter campuran huruf dan angka"
                    }
                // Jika password valid, maka akan dijalankan fungsi cekValiditasForm() yang berfungsi untuk mengecek validitas input dari seluruh field yang nantinya akan
                // digunakan untuk mengubah tampilan pada button register agar dapat ditekan atau tidak sama sekali
                else {
                    val validation = cekValiditasForm()
                    uiThread {
                        enableDisableBtnDaftar(validation)
                    }
                }
            }
        }

        halamanDaftar_konfirmasiPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
            // Event yang dijalankan ketika terjadi perubahan pada field konfirmasi password
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Proses asynchronous yang dilakukan dengan memanggil fungsi cekKonfirmasiPassword() terlebih dahulu untuk memastikan password yang dimasukkan sama
                // dengan password yang telah ditetapkan di field password
                doAsync {
                    if (halamanDaftar_konfirmasiPassword.text.toString() == "") {
                        uiThread { halamanDaftar_konfirmasiPassword.error = "Konfirmasi Password tidak boleh kosong" }
                        return@doAsync
                    }

                    if (!cekKonfirmasiPassword(halamanDaftar_password.text.toString(), halamanDaftar_konfirmasiPassword.text.toString()))
                        // Jika password pada konfirmasi password berbeda dengan password yang dimasukkan dalam field password, maka akan menampilkan UI
                            // berupa pesan error yang menyatakan password yang diinput berbeda.
                        uiThread {
                            halamanDaftar_konfirmasiPassword.error = "Password yang Anda masukkan berbeda"
                        }
                    // Jika input pada field konfirmasi password valid (sama dengan password pada field password), maka akan maka akan dijalankan fungsi cekValiditasForm()
                    // yang berfungsi untuk mengecek validitas input dari seluruh field yang nantinya akan digunakan untuk mengubah tampilan pada button register agar dapat
                    // ditekan atau tidak sama sekali
                    else {
                        val validation = cekValiditasForm()
                        uiThread {
                            enableDisableBtnDaftar(validation)
                        }
                    }
                }
            }
        })

        // Event yang dijalankan ketika terjadi perubahan fokus yang mempengaruhi field konfirmasi password
        halamanDaftar_konfirmasiPassword.setOnFocusChangeListener { view: View, b: Boolean ->
            // Proses asynchronous yang dilakukan dengan memanggil fungsi cekKonfirmasiPassword() terlebih dahulu untuk memastikan password yang dimasukkan sama
            // dengan password yang telah ditetapkan di field password
            doAsync {
                if (!cekKonfirmasiPassword(halamanDaftar_password.text.toString(), halamanDaftar_konfirmasiPassword.text.toString()))
                // Jika password pada konfirmasi password berbeda dengan password yang dimasukkan dalam field password, maka akan menampilkan UI
                // berupa pesan error yang menyatakan password yang diinput berbeda.
                    uiThread {
                        halamanDaftar_konfirmasiPassword.error = "Password yang Anda masukkan berbeda"
                    }
                // Jika input pada field konfirmasi password valid (sama dengan password pada field password), maka akan maka akan dijalankan fungsi cekValiditasForm() yang
                // berfungsi untuk mengecek validitas input dari seluruh field yang nantinya akan  digunakan untuk mengubah tampilan pada button register agar dapat ditekan
                // atau tidak sama sekali
                else {
                    val validation = cekValiditasForm()
                    uiThread {
                        enableDisableBtnDaftar(validation)
                    }
                }
            }
        }

        // Event yang dijalankan ketika terjadi perubahan check pada view checkbox
        halamanDaftar_persetujuan.setOnCheckedChangeListener { compoundButton: CompoundButton, b: Boolean ->
            // Proses asynchronous yang dilakukan dengan memanggil fungsi cekValiditasForm() yang berfungsi untuk mengecek validitas input dari seluruh field yang
            // nantinya akan  digunakan untuk mengubah tampilan pada button register agar dapat ditekan atau tidak sama sekali
            doAsync {
                val validation = cekValiditasForm()
                uiThread {
                    enableDisableBtnDaftar(validation)
                }
            }
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val user = User(halamanDaftar_namaLengkap.text.toString(), halamanDaftar_alamatEmail.text.toString(),
        halamanDaftar_noTelepon.text.toString(), halamanDaftar_password.text.toString())
        val confirm_password = halamanDaftar_konfirmasiPassword.text.toString()

        outState.putParcelable(EXTRA_USER, user)
        outState.putString(EXTRA_PASSWORD, confirm_password)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val user = savedInstanceState.getParcelable<User>(EXTRA_USER)
        halamanDaftar_namaLengkap.setText(user?.nama)
        halamanDaftar_alamatEmail.setText(user?.email)
        halamanDaftar_noTelepon.setText(user?.noTelp)
        halamanDaftar_password.setText(user?.password)
        halamanDaftar_konfirmasiPassword.setText(savedInstanceState.getString(EXTRA_PASSWORD))
    }

    // Fungsi yang digunakan untuk melakukan validasi dari data-data yang dimasukkan oleh pengguna yang hendak mendaftar
    fun cekValiditasForm(): Boolean {
        // 6 baris kode dibawah ini digunakan untuk mengambil data dari masing-masing edittext melalui id yang telah ditetapkan
        val name = halamanDaftar_namaLengkap.text.toString()
        val email = halamanDaftar_alamatEmail.text.toString()
        val telp = halamanDaftar_noTelepon.text.toString()
        val password = halamanDaftar_password.text.toString()
        val confirmPassword = halamanDaftar_konfirmasiPassword.text.toString()
        val checked = halamanDaftar_persetujuan.isChecked

        // Mengecek apakah ada field yang masih kosong, jika ada maka form pendaftaran dinyatakan belum valid
        if (!checked || name == "" || email == "" || telp == "" || password == "" || confirmPassword == "")
            // Mengembalikan false langsung jika ada field yang kosong
            return false

        // Melakukan re-check terhadap isi dari setiap field pada form pendaftaran dengan memanggil fungsi pengecekan pada field masing-masing
        val valid = cekEmail(email) && cekPassword(password) && cekNoTelp(telp) && cekPassword(password) && cekKonfirmasiPassword(password, confirmPassword) && checked
        // Mengembalikan hasil pengecekan ulang
        return valid
    }

    // Fungsi lanjutan dari cekValiditasForm() yang berfungsi sepenuhnya untuk pengolahan UI pada button daftar
    fun enableDisableBtnDaftar(valid: Boolean) {
        // Jika form telah valid, maka akan dilakukan proses berikut
        if (valid) {
            // Button akan di-enable, dengan pemberian warna yang sebelumnya abu-abu menjadi biru
            halamanDaftar_btnDaftar.setBackgroundColor(getColor(R.color.darkestBlue))
            // Button yang di-enable ini akan diberikan event OnClickListener untuk menangkap event klik pada button tersebut
            halamanDaftar_btnDaftar.setOnClickListener{
                val name = halamanDaftar_namaLengkap.text.toString()
                val email = halamanDaftar_alamatEmail.text.toString()
                val telp = halamanDaftar_noTelepon.text.toString()
                val password = halamanDaftar_password.text.toString()

                // Mendaftarkan receiver untuk menangkap perubahan konektivitas perangkat
                registerReceiver(networkReceiver, filter)
                // Jika terdapat koneksi yang terhubung (baik Wi-Fi maupun Mobile Data), maka button ini akan dapat
                // membawa user ke aktivitas berikutnya, menandakan bahwa registrasi akun berhasil dilakukan
                if (connectionStatus) {
                    user = User(name, email, telp, password)
                    FirebaseRealtimeDBHelper(this).apply {
                        findUserByPhoneNumber(telp)
                        findUserByEmailAddress(email)
                    }
                }
                // Jika tidak terdapat koneksi, maka penekanan button akan memicu munculnya pesan yang menyatakan
                // bahwa perangkat tidak memiliki koneksi, dan registrasi akun tidak dapat dilakukan
                else
                    Toast.makeText(this, "Koneksi Internet tidak terdeteksi.", Toast.LENGTH_LONG).show()
            }
        }
        // Jika belum valid, maka button akan kembali diubah ke warna abu-abu, dan akan kehilangan event
        // OnClickListener-nya
        else {
            halamanDaftar_btnDaftar.setBackgroundColor(getColor(R.color.gray))
            halamanDaftar_btnDaftar.setOnClickListener {  }
        }
    }

    // Fungsi yang digunakan untuk mengecek e-mail yang dimasukkan pengguna saat mendaftar
    private fun cekEmail(email: String): Boolean {
        // Melakukan perulangan pada List daftar user untuk memastikan tidak ada e-mail ganda yang terdaftar dalam sistem
        for (i in users) {
            if (email == i.email)
                return false
        }
        // Jika tidak ditemukan e-mail ganda, maka akan dikembalikan "true"
        return true
    }

    // Fungsi yang digunakan untuk mengecek no. telepon yang dimasukkan pengguna saat mendaftar
    private fun cekNoTelp(noTelp: String): Boolean {
        // Melakukan perulangan pada daftar user untuk memastikan tidak ada no. telepon ganda yang terdaftar dalam sistem
        for (i in users) {
            if ("0$noTelp" == i.noTelp)
                return false
        }
        // Jika tidak ditemukan no.telepon ganda, maka akan dikembalikan "true"
        return true
    }

    // Fungsi untuk mengecek password yang dimasukkan oleh pengguna saat mendaftar
    private fun cekPassword(password: String): Boolean {
        // Mengecek apakah password sudah memiliki sebanyak 8 karakter
        if (password.length < 8 || !password.matches(Regex(".*[0-9]+.*")) || !password.matches(Regex(".*[a-zA-Z]+.*")))
            return false
        // Jika password yang dipakai sudah memenuhi syarat, maka akan dikembalikan "true"
        return true
    }

    // Fungsi untuk mengecek kesamaan antara password dengan kolom konfirmasi password
    private fun cekKonfirmasiPassword(password: String, konfirmasiPassword: String): Boolean {
        // Mengecek apakah password yang diisi pada konfirmasi password telah sama dengan password
        if (password != konfirmasiPassword)
            return false
        // Jika password yang digunakan sama dengan password yang dimasukkan pada kolom password, maka
        // akan mengembalikan nilai true
        return true
    }

    fun loginGmail(view: View) {}
    fun loginFB(view: View) {}
}