package com.example.serviceaja.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.serviceaja.R
import com.example.serviceaja.classes.Produk
import kotlinx.android.synthetic.main.layout_daftar_produk.view.*

class RVDaftarProdukDownload(val daftarProduk: ArrayList<Produk>): RecyclerView.Adapter<RVDaftarProdukDownload.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_daftar_produk, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position == 0) {
            holder.itemView.apply {
                layoutDaftarProduk_no.text = "No."
                layoutDaftarProduk_namaProduk.text = "Nama Produk"
                layoutDaftarProduk_hargaProduk.text = "Harga"
            }
        }
        else {
            holder.itemView.apply {
                layoutDaftarProduk_no.text = position.toString()
                layoutDaftarProduk_namaProduk.text = daftarProduk[position].nama
                layoutDaftarProduk_hargaProduk.text = daftarProduk[position].harga.toString()
            }
        }
    }

    override fun getItemCount(): Int {
        return daftarProduk.size
    }
}