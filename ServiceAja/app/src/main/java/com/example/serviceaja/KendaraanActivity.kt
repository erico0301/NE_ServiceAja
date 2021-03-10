package com.example.serviceaja

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.example.serviceaja.classes.Kendaraan
import com.example.serviceaja.classes.PagerAdapterKendaraan
import com.example.serviceaja.fragment.DaftarKendaraan
import com.example.serviceaja.fragment.DetailKendaraan
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_daftar_kendaraan.*

class KendaraanActivity : AppCompatActivity() {
    var daftarMobil = arrayListOf<Kendaraan>()
    var daftarMotor = arrayListOf<Kendaraan>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daftar_kendaraan)

        daftarMobil.add(Kendaraan("Mobil", "BK 9999 XYZ", "Toyota", "Fortuner", 2018, "Bensin",
            "Putih", "123456789123", "1234567891234", "12345678912345"))

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.kendaraan_fragmentContainer, DaftarKendaraan())
            commit()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(MOBIL_ARRAY, daftarMobil)
        outState.putParcelableArrayList(MOTOR_ARRAY, daftarMotor)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        daftarMobil = savedInstanceState.getParcelableArrayList<Kendaraan>(MOBIL_ARRAY) ?: arrayListOf()
        daftarMotor = savedInstanceState.getParcelableArrayList<Kendaraan>(MOTOR_ARRAY) ?: arrayListOf()
    }
}