package com.example.serviceaja.LoginRegister

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.serviceaja.HomeActivity
import com.example.serviceaja.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        masukBtn.setOnClickListener {
            var homeIntent = Intent(this, HomeActivity::class.java)
            startActivity(homeIntent)
            finishAffinity()
        }
    }

    fun forget(view: View) {
        var forgotPassIntent = Intent(this, ForgetPasswordActivity::class.java)
        startActivity(forgotPassIntent)
    }

    fun daftarSekarang(view: View) {
        var registerIntent = Intent(this, RegisterActivity::class.java)
        startActivity(registerIntent)
    }
    fun loginGmail(view: View) {}
    fun loginFB(view: View) {}
}