package com.example.serviceaja.LoginRegister

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.CompoundButton
import com.example.serviceaja.EXTRA_USER
import com.example.serviceaja.EXTRA_USERS
import com.example.serviceaja.R
import com.example.serviceaja.classes.User
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_verification_code.*

class RegisterActivity : AppCompatActivity() {
    private lateinit var users: ArrayList<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        users = intent.getSerializableExtra(EXTRA_USERS) as ArrayList<User>

        halamanDaftar_btnLogin.setOnClickListener {
            val login = Intent(this, LoginActivity::class.java)
            login.putExtra(EXTRA_USERS, users)
            startActivity(login)
            finish()
        }

        halamanDaftar_namaLengkap.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                cekValiditasForm()
            }
        })

        halamanDaftar_namaLengkap.setOnFocusChangeListener { view: View, b: Boolean -> cekValiditasForm() }

        halamanDaftar_alamatEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                cekValiditasForm()
            }
        })
        halamanDaftar_alamatEmail.setOnFocusChangeListener { view: View, b: Boolean ->
            cekValiditasForm()
        }

        halamanDaftar_password.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                cekValiditasForm()
            }
        })
        halamanDaftar_password.setOnFocusChangeListener { view: View, b: Boolean ->
            cekValiditasForm()
        }

        halamanDaftar_konfirmasiPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                cekValiditasForm()
            }
        })
        halamanDaftar_konfirmasiPassword.setOnFocusChangeListener { view: View, b: Boolean ->
            cekValiditasForm()
        }

        halamanDaftar_noTelepon.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                cekValiditasForm()
            }
        })
        halamanDaftar_noTelepon.setOnFocusChangeListener { view: View, b: Boolean ->
            cekValiditasForm()
        }
        halamanDaftar_persetujuan.setOnCheckedChangeListener { compoundButton: CompoundButton, b: Boolean -> cekValiditasForm() }

    }

    fun cekValiditasForm() {
        val name = halamanDaftar_namaLengkap.text.toString()
        val email = halamanDaftar_alamatEmail.text.toString()
        val telp = halamanDaftar_noTelepon.text.toString()
        val password = halamanDaftar_password.text.toString()
        val confirmPassword = halamanDaftar_konfirmasiPassword.text.toString()
        val checked = halamanDaftar_persetujuan.isChecked

        var invalid = false
        fun processInvalid(invalid: Boolean) {
            if (!invalid) {
                halamanDaftar_btnDaftar.setBackgroundColor(getColor(R.color.darkestBlue))
                halamanDaftar_btnDaftar.setOnClickListener{
                    val user = User(name, email, telp, password)
                    var verificationCodeIntent = Intent(this, VerificationCodeActivity::class.java)
                    verificationCodeIntent.putExtra(EXTRA_USER, user)
                    startActivity(verificationCodeIntent)
                }
            }
            else {
                halamanDaftar_btnDaftar.setBackgroundColor(getColor(R.color.gray))
                halamanDaftar_btnDaftar.setOnClickListener {  }
            }
        }

        if (!checked || name == "" || email == "" || telp == "" || password == "" || confirmPassword == "") {
            invalid = true
            processInvalid(invalid)
            return
        }

        for (i in users) {
            if (email == i.email) {
                halamanDaftar_alamatEmail.error = "Alamat E-mail telah terdaftar"
                invalid = true
                processInvalid(invalid)
                return
            }
        }

        for (i in users) {
            if ("0$telp" == i.noTelp) {
                halamanDaftar_alamatEmail.error = "No. Telepon telah terdaftar"
                invalid = true
                processInvalid(invalid)
                return
            }
        }

        if (password.length < 8 && !password.contains("[a-zA-Z]") && !password.contains("[0-9]")) {
            halamanDaftar_password.error = "Password wajib minimal 8 karakter campuran huruf dan angka!"
            invalid = true
            processInvalid(invalid)
            return
        }
        if (password != confirmPassword) {
            halamanDaftar_konfirmasiPassword.error = "Password yang Anda masukkan berbeda!"
            invalid = true
            processInvalid(invalid)
            return
        }

        processInvalid(invalid)
    }

    fun loginGmail(view: View) {}
    fun loginFB(view: View) {}
}