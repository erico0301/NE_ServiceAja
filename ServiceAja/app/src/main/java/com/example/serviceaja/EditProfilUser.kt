package com.example.serviceaja

import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.PopupMenu
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.serviceaja.classes.User
import kotlinx.android.synthetic.main.activity_edit_profil_user.*
import kotlinx.android.synthetic.main.fragment_detail_kendaraan.*

class EditProfilUser : AppCompatActivity() {
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profil_user)

        user = intent.getParcelableExtra<User>(EXTRA_USER)!!

        editProfil_nama.setText(user.nama)
        editProfil_alamatEmail.setText(user.email)
        editProfil_noTelepon.setText(user.noTelp)

        editProfil_ubahPassword.visibility = View.GONE

        editProfil_toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        editProfil_btnEditFoto.setOnClickListener {
            val menu = PopupMenu(this, editProfil_btnEditFoto)
            menu.menuInflater.inflate(R.menu.menu_choose_image, menu.menu)
            menu.setOnMenuItemClickListener {
                when(it.itemId) {
                    R.id.menuChooseItem_bukaKamera -> {
                        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), 101)
                        else {
                            val openCam = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                            if (openCam.resolveActivity(packageManager) != null)
                                startActivityForResult(openCam, 1)
                        }
                        true
                    }
                    R.id.menuChooseItem_ambilDariGallery -> {
                        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 102)
                        else {
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

    fun finishActivity() {
        val intent = Intent()
        intent.putExtra(EXTRA_USER, user)
        setResult(RESULT_OK, intent)
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == AppCompatActivity.RESULT_OK && data != null)
            editProfil_foto.setImageBitmap(data.extras?.get("data") as Bitmap)
        else if (requestCode == 2 && resultCode == AppCompatActivity.RESULT_OK && data != null)
            editProfil_foto.setImageURI(data.data)
    }
}