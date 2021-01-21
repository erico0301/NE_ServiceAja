package com.example.serviceaja.LoginRegister

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.serviceaja.EXTRA_USERS
import com.example.serviceaja.R
import com.example.serviceaja.classes.User
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    var users = arrayListOf<User>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        halamanAwal_btnMasuk.setOnClickListener {
            var login = Intent(this, LoginActivity::class.java)
            login.putExtra(EXTRA_USERS, users)
            startActivity(login)
        }

        halamanAwal_btnDaftar.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}




