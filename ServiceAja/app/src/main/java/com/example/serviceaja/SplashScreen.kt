package com.example.serviceaja

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.serviceaja.LoginRegister.MainActivity
import com.example.serviceaja.classes.AccountSharedPref
import com.example.serviceaja.classes.DBHelper
import com.example.serviceaja.classes.User
import com.example.serviceaja.classes.WatchAdsSharedPref
import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val db = DBHelper(this)
        splashScreenLayout.animate().setDuration(200).alpha(1f).withEndAction {
            val no_telp = AccountSharedPref(this).no_telp
            val user: User?
            val intent: Intent
            if (no_telp != "Kosong") {
                user = db.getUserWithNoTelp(no_telp!!)
                if (user == null) {
                    AccountSharedPref(this).clearValues()
                    intent = Intent(this, MainActivity::class.java)
                }
                else {
                    intent = Intent(this, HomeActivity::class.java)
                    intent.putExtra(EXTRA_USER, user)
                }
            }
            else
                intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }
    }
}