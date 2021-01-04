package com.example.serviceaja.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.serviceaja.R

class RecyclerViewTransactionDetails : RecyclerView.Adapter<RecyclerViewTransactionDetails.ViewHolder>() {

    private val itemImgBengkel = intArrayOf(R.drawable.mitsubishi_logo, R.drawable.bmw_logo)
    private val itemNamaBengkel = arrayOf("Mitsubishi", "BMW")
    private val itemNamaService = arrayOf("Service 1 tahun", "Bodykit X5 M Performance")
    private val itemQuantityService = arrayOf("1", "1")
    private val itemHargaService = arrayOf("0", "23.120.000")
    private val itemNamaProduk = arrayOf("Sill Plate Xpander", "Oil 4L")
    private val itemQuantityProduk = arrayOf("1", "1")
    private val itemHargaProduk = arrayOf("820.000", "1.230.000")
    private val itemTotalHarga = arrayOf("820.000", "24.350.000")
    private val itemStatus = arrayOf("Sedang Dikirim", "Belum Dibayar")

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var imgBengkel: ImageView
        var namaBengkel: TextView
        var namaService: TextView
        var quantityService: TextView
        var hargaService: TextView
        var namaProduk: TextView
        var quantityProduk: TextView
        var hargaProduk: TextView
        var totalHarga: TextView
        var status: TextView

        init {
            imgBengkel = itemView.findViewById(R.id.profileIcon)
            namaBengkel = itemView.findViewById(R.id.namaMitra)
            namaService = itemView.findViewById(R.id.namaService)
            quantityService = itemView.findViewById(R.id.quantityService)
            hargaService = itemView.findViewById(R.id.hargaService)
            namaProduk = itemView.findViewById(R.id.namaProduk)
            quantityProduk = itemView.findViewById(R.id.quantityProduk)
            hargaProduk = itemView.findViewById(R.id.hargaProduk)
            totalHarga = itemView.findViewById(R.id.totalHarga)
            status = itemView.findViewById(R.id.status)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recylerview_transaction_details, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.imgBengkel.setImageResource(itemImgBengkel[position])
        holder.namaBengkel.text = itemNamaBengkel[position]
        holder.namaService.text = itemNamaService[position]
        holder.quantityService.text = itemQuantityService[position]
        holder.hargaService.text = itemHargaService[position]
        holder.namaProduk.text = itemNamaProduk[position]
        holder.quantityProduk.text = itemQuantityProduk[position]
        holder.hargaProduk.text = itemHargaProduk[position]
        holder.totalHarga.text = itemTotalHarga[position]
        holder.status.text = itemStatus[position]
    }

    override fun getItemCount(): Int {
        return itemNamaBengkel.size
    }

}