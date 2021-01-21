package com.example.serviceaja.LoginRegister

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import com.example.serviceaja.R
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        halamanDaftar_btnLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        halamanDaftar_namaLengkap.setOnFocusChangeListener { view: View, b: Boolean -> cekValiditasForm() }
        halamanDaftar_alamatEmail.setOnFocusChangeListener { view: View, b: Boolean -> cekValiditasForm() }
        halamanDaftar_password.setOnFocusChangeListener { view: View, b: Boolean -> cekValiditasForm() }
        halamanDaftar_konfirmasiPassword.setOnFocusChangeListener { view: View, b: Boolean -> cekValiditasForm() }
        halamanDaftar_noTelepon.setOnFocusChangeListener { view: View, b: Boolean -> cekValiditasForm() }
        halamanDaftar_persetujuan.setOnCheckedChangeListener { compoundButton: CompoundButton, b: Boolean -> cekValiditasForm() }

    }

    fun cekValiditasForm() {

        if (halamanDaftar_persetujuan.isChecked && halamanDaftar_namaLengkap.text.toString() != "" && halamanDaftar_alamatEmail.text.toString() != ""
                && halamanDaftar_noTelepon.text.toString() != "" && halamanDaftar_password.text.toString() != "" &&
                halamanDaftar_password.text == halamanDaftar_konfirmasiPassword.text) {
            halamanDaftar_btnDaftar.setBackgroundColor(getColor(R.color.darkestBlue))
            halamanDaftar_btnDaftar.setOnClickListener{

                var verificationCodeIntent = Intent(this, VerificationCodeActivity::class.java)
                startActivity(verificationCodeIntent)
            }
        }
        else {
            halamanDaftar_btnDaftar.setBackgroundColor(getColor(R.color.gray))
            halamanDaftar_btnDaftar.setOnClickListener {  }
        }

    }

    fun loginGmail(view: View) {}
    fun loginFB(view: View) {}
}