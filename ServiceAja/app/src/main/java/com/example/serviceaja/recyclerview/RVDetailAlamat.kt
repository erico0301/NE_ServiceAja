package com.example.serviceaja.recyclerview

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.serviceaja.*
import com.example.serviceaja.classes.Alamat
import com.example.serviceaja.fragment.DetailAlamat
import kotlinx.android.synthetic.main.layout_detail_alamat.view.*

class RVDetailAlamat(val daftarAlamat: ArrayList<Alamat>): RecyclerView.Adapter<RVDetailAlamat.ViewHolder>() {
    inner class ViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_detail_alamat, parent, false)

        view.instanceAlamat_btnEdit.setOnClickListener {
            val daerah = view.instanceAlamat_daerah.text.toString()
            var provinsi = 0
            var kabupatenKota = 0
            var kecamatan = 0

            for (i in parent.resources.getStringArray(R.array.provinsi)) {
                when (i) {
                    daerah.substring(0, daerah.indexOf(',')) -> break
                }
                provinsi++
            }
            for (i in parent.resources.getStringArray(R.array.kabupaten_kota)) {
                when (i) {
                    daerah.substring(daerah.indexOf(',') + 2, daerah.indexOf(',', daerah.indexOf(',') + 1)) -> break
                }
                kabupatenKota++
            }
            for (i in parent.resources.getStringArray(R.array.kecamatan)) {
                when (i) {
                    daerah.substring(daerah.indexOf(',', daerah.indexOf(',') + 1) + 2, daerah.lastIndexOf(',')) -> break
                }
                kecamatan++
            }

            val bundle = Bundle()
            bundle.putString(ALAMAT_TIPE, "EDIT")
            bundle.putString(ALAMAT_NAMA_ALAMAT, view.instanceAlamat_namaAlamat.text.toString())
            bundle.putString(ALAMAT_NAMA_PENERIMA, view.instanceAlamat_namaPenerima.text.toString())
            bundle.putString(ALAMAT_NO_TELP, view.instanceAlamat_noTelp.text.toString())
            bundle.putInt(ALAMAT_PROVINSI, provinsi)
            bundle.putInt(ALAMAT_KABUPATEN_KOTA, kabupatenKota)
            bundle.putInt(ALAMAT_KECAMATAN, kecamatan)
            bundle.putString(ALAMAT_DETAIL_ALAMAT, view.instanceAlamat_detailAlamat.text.toString())

            val fragment = DetailAlamat()
            fragment.arguments = bundle
            fragment.show((parent.context as AppCompatActivity).supportFragmentManager, "EDIT ALAMAT")
        }



        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            instanceAlamat_namaAlamat.text = daftarAlamat[position].namaAlamat
            instanceAlamat_namaPenerima.text = daftarAlamat[position].namaPenerima
            instanceAlamat_noTelp.text = daftarAlamat[position].noTelepon
            instanceAlamat_daerah.text = "${daftarAlamat[position].kecamatan}, ${daftarAlamat[position].kabupatenKota}, " +
                    "${daftarAlamat[position].provinsi}, Indonesia 12345"
            instanceAlamat_detailAlamat.text = daftarAlamat[position].detailAlamat
        }

        holder.itemView.instanceAlamat_btnDelete.setOnClickListener {
            (holder.itemView.context as DaftarAlamat).hapusAlamat(position)
        }
    }

    override fun getItemCount(): Int {
        return daftarAlamat.size
    }
}