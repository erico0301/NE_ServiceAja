package com.example.serviceaja.LoginRegister

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.serviceaja.R
import kotlinx.android.synthetic.main.activity_kode_pemulihan.*
import kotlinx.android.synthetic.main.activity_kode_pemulihan.backBtn

class KodePemulihanActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kode_pemulihan)

        backBtn.setOnClickListener {
            onBackPressed()
        }

        lanjutBtn.setOnClickListener {
            var resetIntent = Intent(this, ResetPasswordActivity::class.java)
            startActivity(resetIntent)
        }

    }
}