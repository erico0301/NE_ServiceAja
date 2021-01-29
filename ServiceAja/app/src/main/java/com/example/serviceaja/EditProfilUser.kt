package com.example.serviceaja

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_edit_profil_user.*

class EditProfilUser : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profil_user)

        editProfil_ubahPassword.visibility = View.GONE

        editProfil_toolbar.setNavigationOnClickListener {
            onBackPressed()
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

                    }
                    .setNegativeButton("BATAL") { dialogInterface: DialogInterface, i: Int -> }
            dialog.show()
        }
    }
}