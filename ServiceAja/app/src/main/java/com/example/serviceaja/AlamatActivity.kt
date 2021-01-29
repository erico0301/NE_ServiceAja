package com.example.serviceaja

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.ViewManager
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.serviceaja.classes.Alamat
import com.example.serviceaja.fragment.DaftarAlamat
import com.example.serviceaja.fragment.DetailAlamat
import com.example.serviceaja.recyclerview.RVDetailAlamat
import kotlinx.android.synthetic.main.activity_daftar_alamat.*

class AlamatActivity : AppCompatActivity() {
    var daftarAlamat = arrayListOf<Alamat>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daftar_alamat)

        daftarAlamat.add(Alamat("Rumah", "Budi", "0812920398", "BINJAI KOTA",
                "BINJAI", "SUMATERA UTARA", "ALAMAT LENGKAP"))

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.alamat_fragmentContainer, DaftarAlamat())
            commit()
        }

    }
}