package com.example.serviceaja

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.ViewManager
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.example.serviceaja.fragment.DetailAlamat
import com.example.serviceaja.fragment.DetailKendaraan

class DaftarKendaraan : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daftar_kendaraan)

        findViewById<androidx.appcompat.widget.Toolbar>(R.id.daftarKendaraan_toolbar).setNavigationOnClickListener {
            onBackPressed()
        }
    }

    fun tambahKendaraan(view: View) {
        val fragment = DetailKendaraan()
        fragment.show(supportFragmentManager, "${fragment} inside ${this}")
        /*
        var noPlat = ""
        var merk = ""
        var nama = ""
        var tahun = ""
        var warna = ""
        var bahanBakar = ""
        var noRangka = ""
        var noMesin = ""
        var noBPKB = ""

        findViewById<Button>(R.id.detailKendaraan_btnTambah).setOnClickListener {
            var change = false
            AlertDialog.Builder(this)
                    .setTitle("Tambah Alamat")
                    .setMessage("Ingin menambah alamat ini?")
                    .setPositiveButton("TAMBAH", DialogInterface.OnClickListener { dialog, which ->
                        change = true
                        noPlat = findViewById<EditText>(R.id.detailKendaraan_noPlat).text.toString()
                        merk = findViewById<Spinner>(R.id.detailKendaraan_merk).selectedItem.toString()
                        nama = findViewById<Spinner>(R.id.detailKendaraan_nama).selectedItem.toString()
                        tahun = findViewById<EditText>(R.id.detailKendaraan_tahun).text.toString()
                        bahanBakar = findViewById<RadioButton>(findViewById<RadioGroup>(R.id.detailKendaraan_bahanBakar).checkedRadioButtonId)
                                .text.toString()
                        noRangka = findViewById<EditText>(R.id.detailKendaraan_noRangka).text.toString()
                        noMesin = findViewById<EditText>(R.id.detailKendaraan_noMesin).text.toString()
                        noBPKB = findViewById<EditText>(R.id.detailKendaraan_noBPKB).text.toString()

                        fragment.dismiss()
                    })
                    .setNegativeButton("BATAL", DialogInterface.OnClickListener { dialog, which ->
                        change = false
                        return@OnClickListener
                    } )
                    .show()

            if (change) {
                fragment.dismiss()
                Toast.makeText(this, "Berhasil Menambahkan Kendaraan Baru", Toast.LENGTH_SHORT).show()
                // Buat Alamat ke daftar baru
            }
        }*/
    }

    fun editKendaraan(view: View) {
        val parent = view.parent as ViewGroup
        val noPlat = (parent.getChildAt(1) as TextView).text.toString()
        var merk = 0
        var nama = 0
        val tahun = (parent.getChildAt(7) as TextView).text.toString().toInt()
        val warna = (parent.getChildAt(10) as TextView).text.toString()
        var bahanBakar = 0
        val noRangka = (parent.getChildAt(15) as TextView).text.toString()
        val noMesin = (parent.getChildAt(16) as TextView).text.toString()
        val noBPKB = (parent.getChildAt(17) as TextView).text.toString()

        for (i in resources.getStringArray(R.array.merkMobil)) {
            when (i) {
                (parent.getChildAt(5) as TextView).text.toString() -> break
            }
            merk++
        }
        for (i in resources.getStringArray(R.array.namaMobil)) {
            when (i) {
                (parent.getChildAt(6) as TextView).text.toString() -> break
            }
            nama++
        }
        for (i in resources.getStringArray(R.array.bahanBakar)) {
            when (i) {
                (parent.getChildAt(11) as TextView).text.toString() -> break
            }
            bahanBakar++
        }

        val fragment = DetailKendaraan()
        fragment.show(supportFragmentManager, "Detail Kendaraan")

    }

    fun hapusKendaraan(view: View) {
        val root: ViewManager = this.windowManager as ViewManager
        val parent = view.parent as View
        val dialog = AlertDialog.Builder(this)
                .setTitle("Hapus Kendaraan")
                .setMessage("Apakah Anda yakin ingin menghapus kendaraan ini? Kendaraan yang terhapus akan hilang dari daftar kendaraan Anda.")
                .setPositiveButton("HAPUS", DialogInterface.OnClickListener { dialogInterface: DialogInterface, i: Int ->
                    root.removeView(parent)
                })
                .setNegativeButton("BATAL", DialogInterface.OnClickListener { dialog, which ->

                })
        dialog.show()
    }
}