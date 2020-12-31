package com.example.serviceaja.LoginRegister

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.serviceaja.R
import kotlinx.android.synthetic.main.activity_reset_password.*

class ResetPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        backBtn.setOnClickListener {
            onBackPressed()
        }

        resetPassBtn.setOnClickListener {
            var mainIntent = Intent(this, MainActivity::class.java)
            startActivity(mainIntent)
            finishAffinity()
        }
    }
}