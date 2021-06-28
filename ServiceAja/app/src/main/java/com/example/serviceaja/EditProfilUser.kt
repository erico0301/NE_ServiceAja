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
import com.example.serviceaja.LoginRegister.VerificationCodeActivity
import com.example.serviceaja.classes.DBHelper
import com.example.serviceaja.classes.User
import kotlinx.android.synthetic.main.activity_edit_profil_user.*
import kotlinx.android.synthetic.main.activity_register.*
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

    private var phoneNumChecked = true
    private var phoneNumAvailable = true
    private var emailAddressChecked = true
    private var emailAddressAvailable = true

    private val checkDataAvailabilityReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
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

            if (phoneNumChecked && emailAddressChecked)
                showDialogDataChecked()
        }
    }

    private fun showDialogDataChecked() {
        if (phoneNumAvailable && emailAddressAvailable) {
            val dialog = AlertDialog.Builder(this)
                    .setTitle("Ubah Informasi Akun")
                    .setMessage("Konfirmasi ubah informasi akun Anda?")
                    .setPositiveButton("YA") { dialogInterface: DialogInterface, i: Int ->
                        val newValueUser = User(
                                editProfil_nama.text.toString(),
                                editProfil_alamatEmail.text.toString(),
                                editProfil_noTelepon.text.toString(), user.password, user.points, user.premium_user
                        )
                        FirebaseRealtimeDBHelper(this@EditProfilUser).updateUser(user, newValueUser)
                        DBHelper(this).updateUser(user.noTelp, newValueUser)
                        setResult(RESULT_OK, Intent().putExtra(EXTRA_USER_RETURN, newValueUser))
                        finish()
                    }
                    .setNegativeButton("BATAL") { dialogInterface: DialogInterface, i: Int -> }
            dialog.show()
        }
        else if (phoneNumAvailable && !emailAddressAvailable) {
            Toast.makeText(this, "E-mail yang digunakan telah terdaftar. Silahkan gunakan alamat e-mail lain.", Toast.LENGTH_SHORT).show()
            editProfil_alamatEmail.hasFocus()
            editProfil_alamatEmail.error = "Alamat E-mail telah terdaftar"
        }
        else if (!phoneNumAvailable && emailAddressAvailable) {
            Toast.makeText(this, "No. Telepon yang digunakan telah terdaftar. Silahkan gunakan no. telepon lain.", Toast.LENGTH_SHORT).show()
            editProfil_noTelepon.hasFocus()
            editProfil_noTelepon.error = "No. Telepon telah terdaftar"
        }
        else {
            Toast.makeText(this, "No. Telepon dan alamat E-mail yang digunakan telah terdaftar. Silahkan gunakan no. telepon dan alamat e-mail lain.", Toast.LENGTH_SHORT).show()
            editProfil_noTelepon.hasFocus()
            editProfil_noTelepon.error = "No. Telepon telah terdaftar"
            editProfil_alamatEmail.error = "Alamat E-mail telah terdaftar"
        }

        phoneNumChecked = false
        emailAddressChecked = false
    }

    private val uploadReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val uploadProgress = intent?.getIntExtra(EXTRA_UPLOAD_PROGRESS, 0)
            if (uploadProgress == 100) {
                Toast.makeText(this@EditProfilUser, "Upload Foto Berhasil Dilakukan!", Toast.LENGTH_LONG).show()
                val uri = intent.getParcelableExtra<Uri>(EXTRA_IMAGE)
                editProfil_foto.setImageURI(uri)
                editProfil_progressUploadFoto.visibility = View.GONE
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profil_user)

        registerReceiver(checkDataAvailabilityReceiver, IntentFilter(PHONE_NUM_AVAILABLE))
        registerReceiver(checkDataAvailabilityReceiver, IntentFilter(PHONE_NUM_USED))
        registerReceiver(checkDataAvailabilityReceiver, IntentFilter(EMAIL_ADDRESS_AVAILABLE))
        registerReceiver(checkDataAvailabilityReceiver, IntentFilter(EMAIL_ADDRESS_USED))

        setSupportActionBar(editProfil_toolbar)
        editProfil_toolbar.setNavigationOnClickListener {
            onBackPressed()
        }


        var db = DBHelper(this)
        user = intent.extras?.getParcelable(EXTRA_USER)!!
        users = db.getAllUsers()

        editProfil_nama.setText(user.nama)
        editProfil_alamatEmail.setText(user.email)
        editProfil_noTelepon.setText(user.noTelp)

        editProfil_btnEditProfil.setOnClickListener {
            var userDataTemp = User()
            userDataTemp.nama = editProfil_nama.text.toString()
            userDataTemp.noTelp = editProfil_noTelepon.text.toString()
            userDataTemp.email = editProfil_alamatEmail.text.toString()

            //Update database
            db.update(userDataTemp)

            finish()
        }

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

        editProfil_btnEditFoto.setOnClickListener {
            val menu = PopupMenu(this, editProfil_btnEditFoto)
            menu.menuInflater.inflate(R.menu.menu_choose_image, menu.menu)
            menu.setOnMenuItemClickListener {
                when(it.itemId) {
                    R.id.menuChooseItem_bukaKamera -> {
                        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), 101)
                        openCamera()
                        true
                    }
                    R.id.menuChooseItem_ambilDariGallery -> {
                        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 102)
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
    }

    private fun openCamera() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
            return
        val openCam = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (openCam.resolveActivity(packageManager) != null) {
            photoFile = try {
                createImageFile()
            } catch (ex: IOException) {
                Log.e("Failed to save image", ex.toString())
                null
            }
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
            val noTelp = editProfil_noTelepon.text.toString()

            if (user.nama == nama && user.email == email && user.noTelp == noTelp)
                finish()
            if (editProfil_nama.error != null || editProfil_alamatEmail.error != null || editProfil_noTelepon.error != null)
                error = true
            if (!noTelp.equals(user.noTelp) || !email.equals(user.email)) {


                if (!noTelp.equals(user.noTelp)) {
                    phoneNumAvailable = false
                    phoneNumChecked = false
                    Log.e("user.notelp", user.noTelp)
                    FirebaseRealtimeDBHelper(this).findUserByPhoneNumber(noTelp)
                }

                if (!email.equals(user.email)) {
                    emailAddressChecked = false
                    emailAddressAvailable = false
                    Log.e("user.email", user.email)
                    FirebaseRealtimeDBHelper(this).findUserByEmailAddress(email)
                }
            }
            else {
                val dialog = AlertDialog.Builder(this)
                        .setTitle("Ubah Informasi Akun")
                        .setMessage("Konfirmasi ubah informasi akun Anda?")
                        .setPositiveButton("YA") { dialogInterface: DialogInterface, i: Int ->
                            val newValueUser = User(
                                    editProfil_nama.text.toString(),
                                    editProfil_alamatEmail.text.toString(),
                                    editProfil_noTelepon.text.toString(), user.password, user.points, user.premium_user
                            )
                            FirebaseRealtimeDBHelper(this@EditProfilUser).updateUser(user, newValueUser)
                            DBHelper(this).updateUser(user.noTelp, newValueUser)
                            setResult(RESULT_OK, Intent().putExtra(EXTRA_USER_RETURN, newValueUser))
                            finish()
                        }
                        .setNegativeButton("BATAL") { dialogInterface: DialogInterface, i: Int -> }
                dialog.show()
            }
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    // Fungsi ini digunakan untuk membuat file baru untuk menampung gambar yang dipotret langsung dari kamera yang dibuka dalam aplikasi
    private fun createImageFile(): File {
        val dateFormat = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")
        val fileName = "JPEG_" + LocalDateTime.now().format(dateFormat)
        val dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(fileName, ".jpg", dir).apply {
            photoPath = absolutePath
        }
    }

   /* fun finishActivity() {
        Log.e("Finish Activity", "${user.nama}, ${user.email}, ${user.noTelp}")
        val intent = Intent()
        intent.putExtra(EXTRA_USER_RETURN, user)
        setResult(RESULT_OK, intent)
        finish()
    }*/

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
        if (data != null) {
            if (requestCode == 1 && resultCode == RESULT_OK)
                uri = Uri.parse(photoFile?.absolutePath)
            else if (requestCode == 2 && resultCode == RESULT_OK)
                uri = data.data
            Log.d("Upload Image", uri.toString())
            editProfil_progressUploadFoto.visibility = View.VISIBLE
            Toast.makeText(this, "Sedang di-upload...", Toast.LENGTH_SHORT).show()
            val service = Intent(this, UploadImageService::class.java)
            service.putExtra(EXTRA_IMAGE, uri)

            UploadImageService.enqueueWork(this, service)       // Memanggil JobIntentService
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        finish()
        unregisterReceiver(uploadReceiver)
    }
}