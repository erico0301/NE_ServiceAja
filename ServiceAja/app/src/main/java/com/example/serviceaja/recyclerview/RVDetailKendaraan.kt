package com.example.serviceaja.recyclerview

import android.app.AlertDialog
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.DialogInterface
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.serviceaja.*
import com.example.serviceaja.classes.DBHelper
import com.example.serviceaja.classes.Kendaraan
import com.example.serviceaja.fragment.DaftarKendaraan
import com.example.serviceaja.fragment.DaftarMobil
import com.example.serviceaja.fragment.DetailKendaraan
import kotlinx.android.synthetic.main.fragment_detail_kendaraan.view.*
import kotlinx.android.synthetic.main.layout_detail_kendaraan.view.*
import java.time.format.DateTimeFormatter

class RVDetailKendaraan(val fragment: FragmentActivity, val daftarKendaraan: ArrayList<Kendaraan>)
    : RecyclerView.Adapter<RVDetailKendaraan.ViewHolder>() {
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_detail_kendaraan, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            val bitmap = context.openFileInput(daftarKendaraan[position].uri_photo + ".jpg").use {
                BitmapFactory.decodeStream(it)
            }

            detailKendaraan_noPlatKendaraan.text = daftarKendaraan[position].plat
            detailKendaraan_merkKendaraan.text = daftarKendaraan[position].merk
            detailKendaraan_namaKendaraan.text = daftarKendaraan[position].nama
            detailKendaraan_tahunKendaraan.text = daftarKendaraan[position].tahun.toString()
            detailKendaraan_bahanBakarKendaraan.text = daftarKendaraan[position].bahanBakar
            detailKendaraan_warnaKendaraan.text = daftarKendaraan[position].warna
            detailKendaraan_noRangkaKendaraan.text = daftarKendaraan[position].noRangka
            detailKendaraan_noMesinKendaraan.text = daftarKendaraan[position].noMesin
            detailKendaraan_noBPKBKendaraan.text = daftarKendaraan[position].noBPKB
            detailKendaraan_serviceTerakhirKendaraan.text = daftarKendaraan[position].serviceTerakhir
            detailKendaraan_fotoKendaraan.setImageBitmap(bitmap)

            detailKendaraan_btnDelete.setOnClickListener {
                val dialog = AlertDialog.Builder(context)
                    .setTitle("Hapus Kendaraan")
                    .setMessage("Apakah Anda yakin ingin menghapus kendaraan ini? Kendaraan yang terhapus akan hilang dari daftar kendaraan Anda.")
                    .setPositiveButton("HAPUS", DialogInterface.OnClickListener { dialogInterface: DialogInterface, i: Int ->
                        DBHelper(context).apply {
                            beginTransaction
                            deleteKendaraanByPlat(daftarKendaraan[position].plat)
                            successTransaction
                            endTransaction
                        }
                        context.deleteFile(daftarKendaraan[position].uri_photo + ".jpg")
                        daftarKendaraan.removeAt(position)
                        notifyItemRemoved(position)

                        (context as KendaraanActivity).updateListKendaraan()

                        fragment.supportFragmentManager.beginTransaction().apply {
                            replace(R.id.kendaraan_fragmentContainer, DaftarKendaraan())
                            commit()
                        }
                    })
                    .setNegativeButton("BATAL", DialogInterface.OnClickListener { dialog, which ->

                    })
                dialog.show()
            }

            detailKendaraan_btnEdit.setOnClickListener {
                val editFragment = DetailKendaraan()

                val bundle = Bundle()

                bundle.putInt(KENDARAAN_POSITION, position)
                bundle.putString(KENDARAAN_TIPE, "Mobil")
                bundle.putString(KENDARAAN_NO_PLAT, detailKendaraan_noPlatKendaraan.text.toString())
                bundle.putString(KENDARAAN_MERK, detailKendaraan_merkKendaraan.text.toString())
                bundle.putString(KENDARAAN_NAMA, detailKendaraan_namaKendaraan.text.toString())
                bundle.putString(KENDARAAN_TAHUN, detailKendaraan_tahunKendaraan.text.toString())
                bundle.putString(KENDARAAN_BAHAN_BAKAR, detailKendaraan_bahanBakarKendaraan.text.toString())
                bundle.putString(KENDARAAN_WARNA, detailKendaraan_warnaKendaraan.text.toString())
                bundle.putString(KENDARAAN_NO_RANGKA, detailKendaraan_noRangkaKendaraan.text.toString())
                bundle.putString(KENDARAAN_NO_MESIN, detailKendaraan_noMesinKendaraan.text.toString())
                bundle.putString(KENDARAAN_NO_BPKB, detailKendaraan_noBPKBKendaraan.text.toString())

                editFragment.arguments = bundle
                fragment.supportFragmentManager.beginTransaction().apply {
                    replace(R.id.kendaraan_fragmentContainer, editFragment)
                    commit()
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return daftarKendaraan.size
    }

}