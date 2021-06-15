package com.example.serviceaja

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.example.serviceaja.classes.DBHelper
import com.example.serviceaja.classes.Kendaraan
import com.example.serviceaja.classes.PagerAdapterKendaraan
import com.example.serviceaja.classes.User
import com.example.serviceaja.fragment.DaftarKendaraan
import com.example.serviceaja.fragment.DetailKendaraan
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_daftar_kendaraan.*

class KendaraanActivity : AppCompatActivity() {
    var daftarMobil = arrayListOf<Kendaraan>()
    var daftarMotor = arrayListOf<Kendaraan>()
    lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daftar_kendaraan)
        user = intent.getParcelableExtra<User>(EXTRA_USER)!!
        updateListKendaraan()

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

    fun updateListKendaraan() {
        val db = DBHelper(this)
        db.beginTransaction
        daftarMotor.clear()
        daftarMobil.clear()
        db.getAllKendaraan(user.noTelp).map {
            if (it.jenis == "Mobil") daftarMobil.add(it)
            else daftarMotor.add(it)
        }
        db.successTransaction
        db.endTransaction
        updateWidget()
    }

    private fun updateWidget() {
        val appWidgetManager = AppWidgetManager.getInstance(this)
        val ids = appWidgetManager.getAppWidgetIds(ComponentName(this, InfoKendaraanWidget::class.java))
        val intent = Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE)
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
        sendBroadcast(intent)
    }
}