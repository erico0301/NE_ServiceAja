package com.example.serviceaja.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.serviceaja.R

class RecyclerViewProductServiceTransactionDoneDetail : RecyclerView.Adapter<RecyclerViewProductServiceTransactionDoneDetail.ViewHolder>() {

    private val itemImgProduct = intArrayOf(R.drawable.bodykit_x5, R.drawable.x5_oil)
    private val itemNamaProduct = arrayOf("Bodykit X5 M Performance", "Oil 4L X5")
    private val itemHargaProduct = arrayOf("23.120.000", "1.230.000")
    private val itemQtyProduct = arrayOf("1", "1")

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
        return itemImgProduct.size
    }

}