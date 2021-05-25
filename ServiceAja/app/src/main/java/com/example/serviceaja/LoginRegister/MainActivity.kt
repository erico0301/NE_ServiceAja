package com.example.serviceaja.LoginRegister

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.serviceaja.EXTRA_USERS
import com.example.serviceaja.R
import com.example.serviceaja.classes.DBHelper
import com.example.serviceaja.classes.User
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    var users = arrayListOf(User("admin", "admin@gmail.com", "081234567890", "admin"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val db = DBHelper(this)

        halamanAwal_btnMasuk.setOnClickListener {
            var login = Intent(this, LoginActivity::class.java)
            login.putExtra(EXTRA_USERS, users)
            startActivity(login)
        }

        halamanAwal_btnDaftar.setOnClickListener {
            var register = Intent(this, RegisterActivity::class.java)
            register.putExtra(EXTRA_USERS, users)
            startActivity(register)
        }
    }
}




