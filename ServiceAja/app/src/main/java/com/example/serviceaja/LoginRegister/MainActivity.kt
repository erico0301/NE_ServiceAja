package com.example.serviceaja.LoginRegister

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.serviceaja.EXTRA_USERS
import com.example.serviceaja.R
import com.example.serviceaja.classes.DBHelper
import com.example.serviceaja.classes.User
import com.google.android.gms.ads.MobileAds
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        MobileAds.initialize(this)

        halamanAwal_btnMasuk.setOnClickListener {
            val login = Intent(this, LoginActivity::class.java)
            startActivity(login)
        }

        halamanAwal_btnDaftar.setOnClickListener {
            val register = Intent(this, RegisterActivity::class.java)
            startActivity(register)
        }
    }
}




