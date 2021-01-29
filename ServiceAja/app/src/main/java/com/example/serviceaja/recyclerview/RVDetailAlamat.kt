package com.example.serviceaja.recyclerview

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.serviceaja.*
import com.example.serviceaja.classes.Alamat
import com.example.serviceaja.fragment.DaftarAlamat
import com.example.serviceaja.fragment.DaftarKendaraan
import com.example.serviceaja.fragment.DetailAlamat
import kotlinx.android.synthetic.main.layout_detail_alamat.view.*

class RVDetailAlamat(val fragment: FragmentActivity, val daftarAlamat: ArrayList<Alamat>)
    : RecyclerView.Adapter<RVDetailAlamat.ViewHolder>() {
    inner class ViewHolder(itemview: View) : RecyclerView.ViewHolder(itemview) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_detail_alamat, parent, false)

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

            instanceAlamat_btnEdit.setOnClickListener {
                val editFragment = DetailAlamat()
                val daerah = instanceAlamat_daerah.text.toString()

                val bundle = Bundle()

                bundle.putInt(ALAMAT_POSITION, position)
                bundle.putString(ALAMAT_NAMA_ALAMAT, instanceAlamat_namaAlamat.text.toString())
                bundle.putString(ALAMAT_NAMA_PENERIMA, instanceAlamat_namaPenerima.text.toString())
                bundle.putString(ALAMAT_NO_TELP, instanceAlamat_noTelp.text.toString())
                bundle.putString(ALAMAT_PROVINSI, daerah.substring(0, daerah.indexOf(',')))
                bundle.putString(ALAMAT_KABUPATEN_KOTA, daerah.substring(daerah.indexOf(',') + 2, daerah.indexOf(',', daerah.indexOf(',') + 1)))
                bundle.putString(ALAMAT_KECAMATAN, daerah.substring(daerah.indexOf(',', daerah.indexOf(',') + 1) + 2, daerah.lastIndexOf(',')))
                bundle.putString(ALAMAT_DETAIL_ALAMAT, instanceAlamat_detailAlamat.text.toString())

                editFragment.arguments = bundle
                fragment.supportFragmentManager.beginTransaction().apply {
                    replace(R.id.alamat_fragmentContainer, editFragment)
                    commit()
                }
            }

            instanceAlamat_btnDelete.setOnClickListener {
                val dialog = AlertDialog.Builder(context)
                        .setTitle("Hapus Alamat")
                        .setMessage("Apakah Anda yakin ingin menghapus alamat ini? Kendaraan yang terhapus akan hilang dari daftar kendaraan Anda.")
                        .setPositiveButton("HAPUS", DialogInterface.OnClickListener { dialogInterface: DialogInterface, i: Int ->
                            daftarAlamat.removeAt(position)
                            notifyItemRemoved(position)
                            fragment.supportFragmentManager.beginTransaction().apply {
                                replace(R.id.alamat_fragmentContainer, DaftarAlamat())
                                commit()
                            }
                        })
                        .setNegativeButton("BATAL", DialogInterface.OnClickListener { dialog, which ->

                        })
                dialog.show()
            }
        }
    }

    override fun getItemCount(): Int {
        return daftarAlamat.size
    }
}