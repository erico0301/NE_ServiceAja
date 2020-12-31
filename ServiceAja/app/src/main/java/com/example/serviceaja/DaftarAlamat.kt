package com.example.serviceaja

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class DaftarAlamat : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daftar_alamat)
    }

    fun deleteAddressDialog(view: View) {}
    fun editAddressDialog(view: View) {}
    fun addAddressDialog(view: View) {}
}