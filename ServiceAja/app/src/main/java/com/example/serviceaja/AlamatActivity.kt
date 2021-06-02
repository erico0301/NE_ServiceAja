package com.example.serviceaja

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.ViewManager
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.serviceaja.classes.Alamat
import com.example.serviceaja.classes.User
import com.example.serviceaja.fragment.DaftarAlamat
import com.example.serviceaja.fragment.DetailAlamat
import com.example.serviceaja.recyclerview.RVDetailAlamat
import kotlinx.android.synthetic.main.activity_daftar_alamat.*

class AlamatActivity : AppCompatActivity() {
    lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daftar_alamat)

        user = intent.getParcelableExtra(EXTRA_USER) ?: User("Testing", "testing@gmail.com", "08123456789", "asdfghjkl")

        supportFragmentManager.beginTransaction().apply {
            val daftarAlamat = DaftarAlamat()
            val bundle = Bundle()
            bundle.putParcelable(EXTRA_USER, user)
            daftarAlamat.arguments = bundle
            replace(R.id.alamat_fragmentContainer, daftarAlamat)
            commit()
        }

    }
}