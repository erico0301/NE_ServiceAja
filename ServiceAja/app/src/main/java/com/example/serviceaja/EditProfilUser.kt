package com.example.serviceaja

import android.content.*
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
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

        user = intent.getParcelableExtra<User>(EXTRA_USER)!!
        title = "Edit Profil"

        editProfil_nama.setText(user.nama)
        editProfil_alamatEmail.setText(user.email)
        editProfil_noTelepon.setText(user.noTelp)

        editProfil_ubahPassword.visibility = View.GONE
        editProfil_progressUploadFoto.visibility = View.GONE

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
                        else {
                            // Bagian ini menunjukkan implementasi dari Intent Implisit, yaitu membuka kamera
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
                        true
                    }
                    R.id.menuChooseItem_ambilDariGallery -> {
                        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 102)
                        else {
                            // Bagian ini menunjukkan implementasi dari Intent Implisit, yaitu membuka Gallery dan memilih foto
                            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                            startActivityForResult(intent, 2)
                        }
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
            val dialog = AlertDialog.Builder(this)
                    .setTitle("Ubah Password")
                    .setMessage("Konfirmasi ubah password?")
                    .setPositiveButton("YA") { dialogInterface: DialogInterface, i: Int ->

                    }
                    .setNegativeButton("BATAL") { dialogInterface: DialogInterface, i: Int -> }
            dialog.show()
        }

        editProfil_btnEditProfil.setOnClickListener {
            val dialog = AlertDialog.Builder(this)
                    .setTitle("Ubah Informasi Akun")
                    .setMessage("Konfirmasi ubah informasi akun Anda?")
                    .setPositiveButton("YA") { dialogInterface: DialogInterface, i: Int ->
                        user.nama = editProfil_nama.text.toString()
                        user.email = editProfil_alamatEmail.text.toString()
                        user.noTelp = editProfil_noTelepon.text.toString()

                        finishActivity()
                    }
                    .setNegativeButton("BATAL") { dialogInterface: DialogInterface, i: Int -> }
            dialog.show()
        }
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
        val intent = Intent()
        intent.putExtra(EXTRA_USER, user)
        setResult(RESULT_OK, intent)
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        var uri: Uri? = null
        // Jika hasil aktivitas berasal dari intent kamera, maka akan diambil path dari file gambar yang telah dibuat sebelumnya dan mendapatkan nilai URI dari file tersebut
        // untuk kemudian dikirimkan ke JobIntentService untuk memasuki proses upload
        if (requestCode == 1 && resultCode == AppCompatActivity.RESULT_OK) {
            uri = Uri.parse(photoFile?.absolutePath)
        }
        // Jika hasil aktivitas berasal dari intent membuka gallery, maka langsung diambil nilai data.data yang merupakan URI dari file gambar yang telah terpilih
        else if (requestCode == 2 && resultCode == AppCompatActivity.RESULT_OK && data != null) {
            uri = data.data
        }
        Log.d("Upload Image", uri.toString())
        editProfil_progressUploadFoto.visibility = View.VISIBLE  // Progress bar akan dibuat visible (terlihat) disini
        Toast.makeText(this, "Sedang di-upload...", Toast.LENGTH_SHORT).show()
        val service = Intent(this, UploadImageService::class.java)  // Pembentukan intent untuk JobIntentService yang merupakan proses upload gambar
        service.putExtra(EXTRA_IMAGE, uri)                                         // Memasukkan URI dari gambar ke dalam extra yang disematkan dalam intent yang telah dibuat

        val filter = IntentFilter(ACTION_UPLOAD_IMAGE)  // Membuat intentfilter untuk menangkap aksi upload foto yang terjadi pada JobIntentService UploadImageService.kt
        registerReceiver(uploadReceiver, filter)        // Mendaftarkan receiver

        UploadImageService.enqueueWork(this, service)       // Memanggil JobIntentService
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(uploadReceiver)
    }
}