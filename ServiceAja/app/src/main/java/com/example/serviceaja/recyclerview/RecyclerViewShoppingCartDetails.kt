package com.example.serviceaja.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.serviceaja.R

class RecyclerViewShoppingCartDetails : RecyclerView.Adapter<RecyclerViewShoppingCartDetails.ViewHolder>() {

    private val itemImgBengkel = intArrayOf(R.drawable.mitsubishi_logo, R.drawable.bmw_logo)
    private val itemNamaBengkel = arrayOf("Mitsubishi", "BMW")
    private val itemImgService = arrayOf(R.drawable.wash_xpander, R.drawable.ban_rft)
    private val itemNamaService = arrayOf("Car Wash", "Ban X5 RFT")
    private val itemQuantityService = arrayOf("1", "2")
    private val itemHargaService = arrayOf("50.000", "0")
    private val itemImgProduk = arrayOf(R.drawable.fb_aki, R.drawable.wiper_bmw)
    private val itemNamaProduk = arrayOf("Aki Xpander", "Wiper X5 (Rear)")
    private val itemQuantityProduk = arrayOf("1", "1")
    private val itemHargaProduk = arrayOf("650.000", "750.000")

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgBengkel: ImageView
        var namaBengkel: TextView
        var imgService : ImageView
        var namaService: TextView
        var quantityService: TextView
        var hargaService: TextView
        var imgProduk : ImageView
        var namaProduk: TextView
        var quantityProduk: TextView
        var hargaProduk: TextView

        init {
            imgBengkel = itemView.findViewById(R.id.profileIcon)
            namaBengkel = itemView.findViewById(R.id.namaMitra)
            imgService = itemView.findViewById(R.id.serviceImg)
            namaService = itemView.findViewById(R.id.namaService)
            quantityService = itemView.findViewById(R.id.quantityService)
            hargaService = itemView.findViewById(R.id.servicePrice)
            imgProduk = itemView.findViewById(R.id.produkImg)
            namaProduk = itemView.findViewById(R.id.namaProduk)
            quantityProduk = itemView.findViewById(R.id.quantityProduk)
            hargaProduk = itemView.findViewById(R.id.produkPrice)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context)
                .inflate(R.layout.recyclerview_shoppingcart_details, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.imgBengkel.setImageResource(itemImgBengkel[position])
        holder.namaBengkel.text = itemNamaBengkel[position]
        holder.imgService.setImageResource(itemImgService[position])
        holder.namaService.text = itemNamaService[position]
        holder.quantityService.text = itemQuantityService[position]
        holder.hargaService.text = itemHargaService[position]
        holder.imgProduk.setImageResource(itemImgProduk[position])
        holder.namaProduk.text = itemNamaProduk[position]
        holder.quantityProduk.text = itemQuantityProduk[position]
        holder.hargaProduk.text = itemHargaProduk[position]
    }

    override fun getItemCount(): Int {
        return itemNamaBengkel.size
    }

}