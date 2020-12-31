package com.example.serviceaja.LoginRegister

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.serviceaja.R
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        daftarBtn.setOnClickListener{
            var verificationCodeIntent = Intent(this, VerificationCodeActivity::class.java)
            startActivity(verificationCodeIntent)
        }
    }

    fun loginGmail(view: View) {}
    fun loginFB(view: View) {}

    fun masukSekarang(view: View) {
        var loginIntent = Intent(this, LoginActivity::class.java)
        startActivity(loginIntent)
    }
}