package com.example.serviceaja

import android.content.*
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.serviceaja.classes.DBHelper
import com.example.serviceaja.classes.User
import kotlinx.android.synthetic.main.activity_edit_profil_user.*
import kotlinx.android.synthetic.main.fragment_detail_kendaraan.*
import java.io.File
import java.io.IOException
import java.security.Timestamp
import java.sql.Time
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class EditProfilUser : AppCompatActivity() {
    private lateinit var user: User
    private lateinit var users: ArrayList<User>
    private lateinit var photoPath: String
    private var photoFile: File? = null

    // Receiver untuk menangkap progress upload foto pada JobIntentService
    private val uploadReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val uploadProgress = intent?.getIntExtra(EXTRA_UPLOAD_PROGRESS, 0) // Mendapatkan nilai progress yang dikirimkan dari JobIntentService
            // Jika progres upload sudah mencapai 100%, maka receiver akan menerima extra berupa URI untuk foto yang diupload oleh user.
            if (uploadProgress == 100) {
                Toast.makeText(this@EditProfilUser, "Upload Foto Berhasil Dilakukan!", Toast.LENGTH_LONG).show()
                val uri = intent?.getParcelableExtra<Uri>(EXTRA_IMAGE)
                editProfil_foto.setImageURI(uri)  // Set imageview dengan gambar yang diterima dari JobIntentService hasil dari upload user
                editProfil_progressUploadFoto.visibility = View.GONE  // Progress bar akan hilang setelah proses upload selesai
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profil_user)

        setSupportActionBar(editProfil_toolbar)
        editProfil_toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        val db = DBHelper(this)
        user = intent.extras?.getParcelable(EXTRA_USER)!!
        users = db.getAllUsers()

        editProfil_nama.setText(user.nama)
        editProfil_alamatEmail.setText(user.email)
        editProfil_noTelepon.setText(user.noTelp)

        editProfil_ubahPassword.visibility = View.GONE
        editProfil_progressUploadFoto.visibility = View.GONE

        val filter = IntentFilter(ACTION_UPLOAD_IMAGE)  // Membuat intentfilter untuk menangkap aksi upload foto yang terjadi pada JobIntentService UploadImageService.kt
        registerReceiver(uploadReceiver, filter)        // Mendaftarkan receiver

        editProfil_nama.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {  }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (editProfil_nama.text.toString() == "")
                    editProfil_nama.error = "Nama tidak boleh kosong"
                cekFieldValidation()
            }

            override fun afterTextChanged(s: Editable?) {  }
        })

        editProfil_alamatEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {  }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (editProfil_alamatEmail.text.toString() == "")
                    editProfil_alamatEmail.error = "Alamat E-mail tidak boleh kosong"
                if (!cekEmail(editProfil_alamatEmail.text.toString()))
                    editProfil_alamatEmail.error = "Alamat E-mail telah terdaftar"
                cekFieldValidation()
            }

            override fun afterTextChanged(s: Editable?) {  }

        })

        editProfil_noTelepon.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {  }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (editProfil_noTelepon.text.toString() == "")
                    editProfil_noTelepon.error = "No. Telepon tidak boleh kosong"
                if (!cekNoTelp("0" + editProfil_noTelepon.text.toString()))
                    editProfil_noTelepon.error = "No. Telepon telah terdaftar"
                cekFieldValidation()
            }

            override fun afterTextChanged(s: Editable?) {  }
        })


        // Bagian ini menambahkan event OnClickListener pada view ImageButton.
        // ImageButton yang diklik akan memicu munculnya pop up menu yang memberikan dua pilihan.
        // Pilihan pertama yaitu memasukkan foto dengan langsung menjepret dari kamera
        // Pilihan kedua yaitu memasukkan foto dengan memilih foto yang sudah ada di Gallery
        editProfil_btnEditFoto.setOnClickListener {
            val menu = PopupMenu(this, editProfil_btnEditFoto)
            menu.menuInflater.inflate(R.menu.menu_choose_image, menu.menu)
            menu.setOnMenuItemClickListener {
                when(it.itemId) {
                    R.id.menuChooseItem_bukaKamera -> {
                        // Sebelum membuka kamera, akan dicek terlebih dahulu permission, apakah sudah diberikan
                        // atau belum
                        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), 101)
                        // Bagian ini menunjukkan implementasi dari Intent Implisit, yaitu membuka kamera
                        openCamera()
                        true
                    }
                    R.id.menuChooseItem_ambilDariGallery -> {
                        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 102)
                        // Bagian ini menunjukkan implementasi dari Intent Implisit, yaitu membuka Gallery dan memilih foto
                        openGallery()
                        true
                    }
                    else -> false
                }
            }
            menu.show()
        }

        editProfil_btnShowEditPassword.setOnClickListener {
            editProfil_ubahPassword.visibility = when(editProfil_ubahPassword.visibility) {
                View.GONE -> View.VISIBLE
                else -> View.GONE
            }
        }

        editProfil_btnEditPassword.setOnClickListener {
            var error = false
            if (editProfil_passwordLama.text.toString() == "" || editProfil_passwordBaru.text.toString() == "" || editProfil_konfirmasiPassword.text.toString() == "") {
                Toast.makeText(this, "Field Password tidak terisi lengkap. Silahkan isi setiap field dengan lengkap", Toast.LENGTH_SHORT).show()
                error = true
            }

            if (editProfil_passwordLama.text.toString() != user.password) {
                editProfil_passwordLama.error = "Password yang anda masukkan salah"
                error = true
            }

            if (editProfil_passwordBaru.text.toString().length < 8 || !editProfil_passwordBaru.text.toString().matches(Regex(".*[0-9]+.*")) || !editProfil_passwordBaru.text.toString().matches(Regex(".*[a-zA-Z]+.*"))) {
                editProfil_passwordBaru.error = "Password harus mengandung minimal 8 karakter, terdiri dari huruf dan angka"
                error = true
            }

            if (!editProfil_passwordBaru.text.toString().equals(editProfil_konfirmasiPassword.text.toString())) {
                editProfil_konfirmasiPassword.error = "Password yang Anda masukkan berbeda"
                error = true
            }

            if (!error) {
                val dialog = AlertDialog.Builder(this)
                    .setTitle("Ubah Password")
                    .setMessage("Konfirmasi ubah password?")
                    .setPositiveButton("YA") { dialogInterface: DialogInterface, i: Int ->
                        user.password = editProfil_passwordBaru.text.toString()
                        Toast.makeText(this, "Berhasil Mengubah Password!", Toast.LENGTH_SHORT).show()
                        editProfil_passwordLama.setText("")
                        editProfil_passwordBaru.setText("")
                        editProfil_konfirmasiPassword.setText("")
                        editProfil_ubahPassword.visibility = View.GONE
                    }
                    .setNegativeButton("BATAL") { dialogInterface: DialogInterface, i: Int -> }
                dialog.show()
            }
        }

        editProfil_btnEditProfil.setOnClickListener { finishActivity() }
    }

    private fun openCamera() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
            return
        val openCam = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (openCam.resolveActivity(packageManager) != null) {
            // Saat membuka intent implisit kamera, proses akan sekaligus membuat suatu file baru yang merupakan file foto yang akan disimpan dalam
            // folder khusus untuk aplikasi, ditandai dengan pemanggilan fungsi createImageFile()
            photoFile = try {
                createImageFile()
            } catch (ex: IOException) {
                Log.e("Failed to save image", ex.toString())
                null
            }
            // Jika file terbentuk, maka akan disimpan dalam direktori untuk app, lalu akan membuka kamera
            if (photoFile != null) {
                val photoUri = FileProvider.getUriForFile(this, "com.example.android.fileprovider", photoFile!!)
                openCam.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                startActivityForResult(openCam, 1)
            }
        }
    }

    private fun openGallery() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            return
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, 2)
    }

    private fun cekEmail(email: String): Boolean {
        if (user.email != email)
            for (u in users) {
                if (u.email == user.email)
                    continue
                if (u.email == email)
                    return false
            }
        return true
    }

    private fun cekNoTelp(noTelp: String): Boolean {
        if (user.noTelp != noTelp)
            for (u in users) {
                if (u.noTelp == user.noTelp)
                    continue
                if (u.noTelp == noTelp)
                    return false
            }
        return true
    }

    private fun cekFieldValidation() {
        editProfil_btnEditProfil.setOnClickListener {
            var error = false
            val nama = editProfil_nama.text.toString()
            val email = editProfil_alamatEmail.text.toString()
            val noTelp = "0" + editProfil_noTelepon.text.toString()
            if (user.nama == nama && user.email == email && user.noTelp == noTelp)
                finishActivity()
            if (editProfil_nama.error != null || editProfil_alamatEmail.error != null || editProfil_noTelepon.error != null)
                error = true
            if (!error) {
                val dialog = AlertDialog.Builder(this)
                    .setTitle("Ubah Informasi Akun")
                    .setMessage("Konfirmasi ubah informasi akun Anda?")
                    .setPositiveButton("YA") { dialogInterface: DialogInterface, i: Int ->
                        user.nama = editProfil_nama.text.toString()
                        user.email = editProfil_alamatEmail.text.toString()
                        user.noTelp = "0" + editProfil_noTelepon.text.toString()
                        finishActivity()
                    }
                    .setNegativeButton("BATAL") { dialogInterface: DialogInterface, i: Int -> }
                dialog.show()
            }
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishActivity()
    }

    // Fungsi ini digunakan untuk membuat file baru untuk menampung gambar yang dipotret langsung dari kamera yang dibuka dalam aplikasi
    private fun createImageFile(): File {
        val dateFormat = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")
        val fileName = "JPEG_" + LocalDateTime.now().format(dateFormat)
        val dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        // Setelah di-generasi nama dan direktori, maka file akan dibentuk, yang kemudian akan diisi dengan foto hasil jepretan.
        return File.createTempFile(fileName, ".jpg", dir).apply {
            photoPath = absolutePath
        }
    }

    fun finishActivity() {
        Log.e("Finish Activity", "${user.nama}, ${user.email}, ${user.noTelp}")
        val intent = Intent()
        intent.putExtra(EXTRA_USER_RETURN, user)
        setResult(RESULT_OK, intent)
        finish()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 101) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                openCamera()
            else
                Toast.makeText(this, "Tidak dapat membuka kamera karena tidak diberi izin akses", Toast.LENGTH_SHORT).show()
        }
        else if (requestCode == 102) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                openGallery()
            else
                Toast.makeText(this, "Tidak dapat membuka Gallery karena tidak diberi izin akses", Toast.LENGTH_SHORT).show()
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        var uri: Uri? = null
        // Jika hasil aktivitas berasal dari intent kamera, maka akan diambil path dari file gambar yang telah dibuat sebelumnya dan mendapatkan nilai URI dari file tersebut
        // untuk kemudian dikirimkan ke JobIntentService untuk memasuki proses upload
        if (data != null) {
            if (requestCode == 1 && resultCode == RESULT_OK)
                uri = Uri.parse(photoFile?.absolutePath)
            // Jika hasil aktivitas berasal dari intent membuka gallery, maka langsung diambil nilai data.data yang merupakan URI dari file gambar yang telah terpilih
            else if (requestCode == 2 && resultCode == RESULT_OK)
                uri = data.data
            Log.d("Upload Image", uri.toString())
            editProfil_progressUploadFoto.visibility = View.VISIBLE  // Progress bar akan dibuat visible (terlihat) disini
            Toast.makeText(this, "Sedang di-upload...", Toast.LENGTH_SHORT).show()
            val service = Intent(this, UploadImageService::class.java)  // Pembentukan intent untuk JobIntentService yang merupakan proses upload gambar
            service.putExtra(EXTRA_IMAGE, uri)                                         // Memasukkan URI dari gambar ke dalam extra yang disematkan dalam intent yang telah dibuat

            UploadImageService.enqueueWork(this, service)       // Memanggil JobIntentService
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        finishActivity()
        unregisterReceiver(uploadReceiver)
    }
}