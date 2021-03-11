package com.example.serviceaja.LoginRegister

import android.app.Notification
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import com.example.serviceaja.*
import com.example.serviceaja.classes.User
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_search.*

class LoginActivity : AppCompatActivity() {
    private lateinit var users: ArrayList<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        users = intent.getSerializableExtra(EXTRA_USERS) as ArrayList<User>

        halamanLogin_inputEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                cekValiditasForm()
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        halamanLogin_inputPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                cekValiditasForm()
            }

            override fun afterTextChanged(s: Editable?) {}

        })

        halamanLogin_inputEmail.setOnFocusChangeListener { view: View, b: Boolean ->
            cekValiditasForm()
        }

        halamanLogin_inputPassword.setOnFocusChangeListener { v, hasFocus ->
            cekValiditasForm()
        }

        halamanLogin_btnDaftar.setOnClickListener {
            val register = Intent(this, RegisterActivity::class.java)
            register.putExtra(EXTRA_USERS, users)
            startActivity(register)
            finish()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val email = halamanLogin_inputEmail.text.toString()
        val password = halamanLogin_inputPassword.text.toString()

        outState.putString(EXTRA_EMAIL, email)
        outState.putString(EXTRA_PASSWORD, password)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        halamanLogin_inputEmail.setText(savedInstanceState.getString(EXTRA_EMAIL))
        halamanLogin_inputPassword.setText(savedInstanceState.getString(EXTRA_PASSWORD))
    }

    fun cekValiditasForm() {
        var user: User? = null
        val emailOrPhoneNumber = halamanLogin_inputEmail.text.toString()
        val password = halamanLogin_inputPassword.text.toString()

        var invalid = false

        fun processInvalid(invalid: Boolean) {
            if (!invalid) {
                halamanLogin_btnMasuk.setBackgroundColor(getColor(R.color.darkestBlue))
                halamanLogin_btnMasuk.setOnClickListener{
                    if (user?.password == password) {
                        val home = Intent(this, HomeActivity::class.java)
                        home.putExtra(EXTRA_USER, user)
                        startActivity(home)
                        finishAffinity()
                    }
                    else {
                        val alertDialog = AlertDialog.Builder(this)
                            .setMessage("Password yang dimasukkan salah!")
                            .setPositiveButton("OK", DialogInterface.OnClickListener { dialog, which ->

                            })
                            .show()
                    }
                }
            }
            else {
                halamanLogin_btnMasuk.setBackgroundColor(getColor(R.color.gray))
                halamanLogin_btnMasuk.setOnClickListener {  }
            }
        }

        if (emailOrPhoneNumber == "" || password == "") {
            invalid = true
            processInvalid(invalid)
            return
        }

        for (i in users) {
            if (emailOrPhoneNumber == i.email || emailOrPhoneNumber == i.noTelp)
                user = i
        }
        if (user == null) {
            halamanLogin_inputEmail.error = "E-mail atau No. Telepon belum terdaftar"
            invalid = true
            processInvalid(invalid)
            return
        }
        processInvalid(invalid)
    }

    fun forget(view: View) {
        startActivity(Intent(this, ForgetPasswordActivity::class.java))
    }

    fun loginGmail(view: View) {}
    fun loginFB(view: View) {}
}