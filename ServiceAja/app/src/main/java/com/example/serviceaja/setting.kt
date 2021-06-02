package com.example.serviceaja

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.serviceaja.LoginRegister.LoginActivity
import com.example.serviceaja.LoginRegister.MainActivity
import com.example.serviceaja.classes.AccountSharedPref
import com.example.serviceaja.classes.DBHelper
import kotlinx.android.synthetic.main.activity_setting.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast

class setting : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        //database Help
        val db = DBHelper(this)

        //variable untuk mengecek user yang sedang login
        var crnUser = LoginActivity().currentUser

        //eksekusi pada saat menekan to9mbol hapus akun
        setting_HapusAkun.setOnClickListener{
            //pengecekan apakah emailUser ada atau kosong
            if(crnUser!=null || crnUser !=""){
                //proses penghapusan data dari database
                doAsync {
                    db?.deleteData(crnUser)

                    //function kembali ke startpage
                    home()
                }
            }
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