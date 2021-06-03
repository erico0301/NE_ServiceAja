package com.example.serviceaja

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.serviceaja.LoginRegister.MainActivity
import com.example.serviceaja.classes.AccountSharedPref
import com.example.serviceaja.classes.DBHelper
import com.example.serviceaja.classes.User
import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val db = DBHelper(this)
        val users = db.getAllUsers()

        splashScreenLayout.animate().setDuration(200).alpha(1f).withEndAction {
            val email = AccountSharedPref(this).email
            var user: User? = null
            val intent: Intent
            if (email != "Kosong") {
                intent = Intent(this, HomeActivity::class.java)
                for (i in users)
                    if (i.email.equals(email))
                        user = i
                intent.putExtra(EXTRA_USER, user)
            }
            else
                intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }
    }
}