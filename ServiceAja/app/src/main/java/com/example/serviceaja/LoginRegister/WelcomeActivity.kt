package com.example.serviceaja.LoginRegister

import DBClass.DBUser
import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.serviceaja.EXTRA_USER
import com.example.serviceaja.HomeActivity
import com.example.serviceaja.R
import com.example.serviceaja.classes.AccountSharedPref
import com.example.serviceaja.classes.DBHelper
import com.example.serviceaja.classes.User
import kotlinx.android.synthetic.main.activity_welcome.*

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        val user = intent.getParcelableExtra<User>(EXTRA_USER)

        val db = DBHelper(this)
        db.addUser(user!!)
        AccountSharedPref(this).email = user.email

        startBtn.setOnClickListener {
            var homeIntent = Intent(this, HomeActivity::class.java)
            homeIntent.putExtra(EXTRA_USER, user)
            startActivity(homeIntent)
            finishAffinity()
        }
    }
}