package com.example.serviceaja.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.serviceaja.R

class RecyclerViewProductServiceDetailsPreview : RecyclerView.Adapter<RecyclerViewProductServiceDetailsPreview.ViewHolder>() {

    private val itemImgProduct = intArrayOf(R.drawable.fb_aki, R.drawable.service_bmw)
    private val itemNamaProduct = arrayOf("Battery Xpander FB (Kering)", "Car Wash")
    private val itemHargaProduct = arrayOf("650.000", "50.000")
    private val itemNamaBengkel = arrayOf("Mitsubishi", "BMW")
    private val itemImgRating = intArrayOf(R.drawable.ic_rating_5, R.drawable.ic_rating_5)
    private val itemRatingTxt = arrayOf("5.0", "5.0")

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var imgProduct: ImageView
        var namaProduct: TextView
        var hargaProduct: TextView
        var namaBengkel: TextView
        var imgRating: ImageView
        var ratingTxt: TextView

        init {
            imgProduct = itemView.findViewById(R.id.productImg)
            namaProduct = itemView.findViewById(R.id.namaProduk)
            hargaProduct = itemView.findViewById(R.id.productPrice)
            namaBengkel = itemView.findViewById(R.id.namaMitra)
            imgRating = itemView.findViewById(R.id.ratingIcon)
            ratingTxt = itemView.findViewById(R.id.ratingTxt)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_service_product_details_search_preview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.imgProduct.setImageResource(itemImgProduct[position])
        holder.namaProduct.text = itemNamaProduct[position]
        holder.hargaProduct.text = itemHargaProduct[position]
        holder.namaBengkel.text = itemNamaBengkel[position]
        holder.imgRating.setImageResource(itemImgRating[position])
        holder.ratingTxt.text = itemRatingTxt[position]
    }

    override fun getItemCount(): Int {
        return itemNamaProduct.size
    }
}