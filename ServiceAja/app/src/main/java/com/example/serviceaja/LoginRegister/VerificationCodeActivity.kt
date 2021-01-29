package com.example.serviceaja.LoginRegister

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import com.example.serviceaja.EXTRA_USER
import com.example.serviceaja.R
import com.example.serviceaja.classes.User
import kotlinx.android.synthetic.main.activity_verification_code.*

class VerificationCodeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verification_code)

        var welcomeIntent = Intent(this, WelcomeActivity::class.java)
        welcomeIntent.putExtra(EXTRA_USER, intent.getParcelableExtra<User>(EXTRA_USER))

        forthCode.setOnEditorActionListener { v, actionId, event ->
            if (actionId==EditorInfo.IME_ACTION_DONE) {
                startActivity(welcomeIntent)
            }
            return@setOnEditorActionListener true
        }

        konfirmasiKodeBtn.setOnClickListener {
            startActivity(welcomeIntent)
        }
    }
}