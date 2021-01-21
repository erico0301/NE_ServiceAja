package com.example.serviceaja.LoginRegister

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.serviceaja.EXTRA_USER
import com.example.serviceaja.EXTRA_USERS
import com.example.serviceaja.HomeActivity
import com.example.serviceaja.R
import com.example.serviceaja.classes.User
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val users = intent.getSerializableExtra(EXTRA_USERS) as ArrayList<User>

        halamanLogin_btnMasuk.setOnClickListener {
            for (i in users) {
                if ((halamanLogin_inputEmail.text.toString() == i.email || halamanLogin_inputEmail.text.toString() == i.noTelp)
                        && halamanLogin_inputPassword.text.toString() == i.password) {
                    val home = Intent(this, HomeActivity::class.java)
                    home.putExtra(EXTRA_USER, i)
                    startActivity(home)
                    finish()
                }
            }
        }

        halamanLogin_btnDaftar.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }
    }

    fun forget(view: View) {
        startActivity(Intent(this, ForgetPasswordActivity::class.java))
    }

    fun loginGmail(view: View) {}
    fun loginFB(view: View) {}
}