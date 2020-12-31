package com.example.serviceaja.LoginRegister

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import com.example.serviceaja.R
import kotlinx.android.synthetic.main.activity_forget_password.*

class ForgetPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forget_password)

        backBtn.setOnClickListener {
            onBackPressed()
        }

        kirimKodeBtn.setOnClickListener {
            var recoveryCodeIntent = Intent(this, KodePemulihanActivity::class.java)
            startActivity(recoveryCodeIntent)
        }

        inputEmail.setOnEditorActionListener { v, i, event ->
            if (i == EditorInfo.IME_ACTION_SEND) {
                var recoveryCodeIntent = Intent(this, KodePemulihanActivity::class.java)
                startActivity(recoveryCodeIntent)
            }
            return@setOnEditorActionListener true
        }
    }
}