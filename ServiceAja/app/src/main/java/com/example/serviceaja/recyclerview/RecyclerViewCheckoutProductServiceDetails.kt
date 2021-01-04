package com.example.serviceaja.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.serviceaja.R

class RecyclerViewCheckoutProductServiceDetails : RecyclerView.Adapter<RecyclerViewCheckoutProductServiceDetails.ViewHolder>() {

    private val itemImgProduct = intArrayOf(R.drawable.ban_rft, R.drawable.wiper_bmw)
    private val itemNamaProduct = arrayOf("Ban X5 RFT", "Wiper X5")
    private val itemHargaProduct = arrayOf("0", "750.000")
    private val itemQtyProduct = arrayOf("2", "1")

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var imgProduct: ImageView
        var namaProduct: TextView
        var hargaProduct: TextView
        var quantityProduct: TextView

        init {
            imgProduct = itemView.findViewById(R.id.serviceImg)
            namaProduct = itemView.findViewById(R.id.namaService)
            hargaProduct = itemView.findViewById(R.id.servicePrice)
            quantityProduct = itemView.findViewById(R.id.quantityService)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_productservice_detail_transaction, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.imgProduct.setImageResource(itemImgProduct[position])
        holder.namaProduct.text = itemNamaProduct[position]
        holder.hargaProduct.text = itemHargaProduct[position]
        holder.quantityProduct.text = itemQtyProduct[position]
    }

    override fun getItemCount(): Int {
        return itemNamaProduct.size
    }
}