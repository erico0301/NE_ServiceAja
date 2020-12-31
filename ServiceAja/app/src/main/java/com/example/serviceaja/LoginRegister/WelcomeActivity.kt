package com.example.serviceaja.LoginRegister

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.serviceaja.HomeActivity
import com.example.serviceaja.R
import kotlinx.android.synthetic.main.activity_welcome.*

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        startBtn.setOnClickListener {
            var homeIntent = Intent(this, HomeActivity::class.java)
            startActivity(homeIntent)
            finishAffinity()
        }
    }
}