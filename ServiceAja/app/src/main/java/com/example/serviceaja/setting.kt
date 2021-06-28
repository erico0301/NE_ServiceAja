package com.example.serviceaja

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.serviceaja.LoginRegister.LoginActivity
import com.example.serviceaja.LoginRegister.MainActivity
import com.example.serviceaja.classes.AccountSharedPref
import com.example.serviceaja.classes.DBHelper
import com.example.serviceaja.classes.User
import kotlinx.android.synthetic.main.activity_setting.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast

class setting : AppCompatActivity() {
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        //database Help
        val db = DBHelper(this)
        user = intent.getParcelableExtra(EXTRA_USER)!!
        //variable untuk mengecek user yang sedang login
        //var crnUser = LoginActivity().currentUser

        //eksekusi pada saat menekan to9mbol hapus akun
        setting_HapusAkun.setOnClickListener{
            val dialog = AlertDialog.Builder(this)
                    .setTitle("Hapus Akun ServiceAja")
                    .setMessage("Anda yakin untuk menghapus akun? Akun yang terhapus akan kehilangan semua data dan tidak dapat diakses kembali.")
                    .setPositiveButton("YA") { dialogInterface: DialogInterface, i: Int ->
                        FirebaseRealtimeDBHelper(this).deleteUser(user)
                        DBHelper(this).deleteUser(user.noTelp)
                        AlertDialog.Builder(this)
                                .setTitle("Akun Berhasil Dihapus")
                                .setMessage("Sedih mendengar Anda telah menutup akun Anda. Selamat tinggal, sampai berjumpa lagi!")
                                .setPositiveButton("SELAMAT TINGGAL") { dialogInterface: DialogInterface, i: Int ->
                                    startActivity(Intent(this, MainActivity::class.java))
                                    finishAffinity()
                                }
                                .show()
                    }
                    .setNegativeButton("TIDAK") { dialogInterface: DialogInterface, i: Int -> }

            dialog.show()
        }

        setting_logout.setOnClickListener{
            home()
        }
    }

    fun home (){
        startActivity(Intent(this, MainActivity::class.java))
        AccountSharedPref(this!!).clearValues()
        this?.finishAffinity()
    }
}