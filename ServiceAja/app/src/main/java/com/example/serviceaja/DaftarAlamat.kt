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

class DaftarAlamat : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daftar_alamat)

        findViewById<Toolbar>(R.id.daftarAlamat_toolbar).setNavigationOnClickListener {
            onBackPressed()
        }
    }

    fun deleteAddressDialog(view: View) {
        val root: ViewManager = this.windowManager as ViewManager
        val parent = view.parent as View
        val dialog = AlertDialog.Builder(this)
            .setTitle("Hapus Alamat ini?")
            .setMessage("Apakah Anda yakin ingin menghapus alamat ini? Alamat yang terhapus akan hilang dari daftar alamat Anda.")
            .setPositiveButton("YA", DialogInterface.OnClickListener { dialogInterface: DialogInterface, i: Int ->
                root.removeView(parent)
            })
            .setNegativeButton("BATAL", DialogInterface.OnClickListener { dialog, which ->

            })
        dialog.show()
    }
    fun editAddressDialog(view: View) {
        var parent = view.getParent() as ViewGroup
        val namaAlamat = (parent.getChildAt(0) as TextView).text.toString()
        parent = parent.getChildAt(1) as ViewGroup
        val namaPenerima = ((parent.getChildAt(0) as TableRow).getChildAt(1) as TextView).text.toString()
        val noTelp = ((parent.getChildAt(1) as TableRow).getChildAt(1) as TextView).text.toString()
        val detailAlamat = ((parent.getChildAt(2) as TableRow).getChildAt(1) as TextView).text.toString()
        val daerah = ((parent.getChildAt(3) as TableRow).getChildAt(1) as TextView).text.toString()
        var provinsi = 0
        var kabupatenKota = 0
        var kecamatan = 0
        for (i in resources.getStringArray(R.array.provinsi)) {
            when (i) {
                daerah.substring(0, daerah.indexOf(',')) -> break
            }
            provinsi++
        }
        for (i in resources.getStringArray(R.array.kabupaten_kota)) {
            when (i) {
                daerah.substring(daerah.indexOf(',') + 2, daerah.indexOf(',', daerah.indexOf(',') + 1)) -> break
            }
            kabupatenKota++
        }
        for (i in resources.getStringArray(R.array.kecamatan)) {
            when (i) {
                daerah.substring(daerah.indexOf(',', daerah.indexOf(',') + 1) + 2, daerah.lastIndexOf(',')) -> break
            }
            kecamatan++
        }

        val fragment = DetailAlamat()
        fragment.show(supportFragmentManager, "TAG")
        findViewById<EditText>(R.id.detailAlamat_namaAlamat).setText(namaAlamat)
        findViewById<EditText>(R.id.detailAlamat_namaPenerima).setText(namaPenerima)
        findViewById<EditText>(R.id.detailAlamat_noTelp).setText(noTelp)
        findViewById<EditText>(R.id.detailAlamat_detailAlamat).setText(detailAlamat)
        findViewById<Spinner>(R.id.detailAlamat_provinsi).setSelection(provinsi)
        findViewById<Spinner>(R.id.detailAlamat_kabupatenKota).setSelection(kabupatenKota)
        findViewById<Spinner>(R.id.detailAlamat_kecamatan).setSelection(kecamatan)
    }

    fun addAddressDialog(view: View) {
        val fragment = DetailAlamat()
        fragment.show(supportFragmentManager, "TAG")

        var namaAlamat = ""
        var namaPenerima = ""
        var noTelp = ""
        var detailAlamat = ""
        var daerah = ""

        findViewById<Button>(R.id.detailAlamat_btnTambah).setOnClickListener {
            var change = false
            AlertDialog.Builder(this)
                .setTitle("Tambah Alamat")
                .setMessage("Ingin menambah alamat ini?")
                .setPositiveButton("TAMBAH", DialogInterface.OnClickListener { dialog, which ->
                    change = true
                    namaAlamat = findViewById<EditText>(R.id.detailAlamat_namaAlamat).text.toString()
                    namaPenerima = findViewById<EditText>(R.id.detailAlamat_namaPenerima).text.toString()
                    noTelp = findViewById<EditText>(R.id.detailAlamat_noTelp).text.toString()
                    detailAlamat = findViewById<EditText>(R.id.detailAlamat_detailAlamat).text.toString()
                    daerah = findViewById<Spinner>(R.id.detailAlamat_kecamatan).selectedItem.toString() + ", " +
                            findViewById<Spinner>(R.id.detailAlamat_kabupatenKota).selectedItem.toString() + ", " +
                            findViewById<Spinner>(R.id.detailAlamat_provinsi).selectedItem.toString() + ", " +
                            "12345"
                    fragment.dismiss()
                })
                .setNegativeButton("BATAL", DialogInterface.OnClickListener { dialog, which ->
                    change = false
                    return@OnClickListener
                } )
                .show()

            if (change) {
                fragment.dismiss()
                Toast.makeText(this, "Berhasil Menambahkan Alamat Baru", Toast.LENGTH_SHORT).show()
                // Buat Alamat ke daftar baru
            }
        }
    }
}