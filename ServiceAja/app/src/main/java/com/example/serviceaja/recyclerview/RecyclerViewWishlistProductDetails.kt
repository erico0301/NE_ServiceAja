package com.example.serviceaja.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.serviceaja.R

class RecyclerViewWishlistProductDetails : RecyclerView.Adapter<RecyclerViewWishlistProductDetails.ViewHolder>() {

    private val itemImgProduct = intArrayOf(R.drawable.dashcam, R.drawable.bodykit_x5)
    private val itemNamaProduct = arrayOf("Dashcam BlackVue 60fps FHD (Front)", "BodyKit BMW X5")
    private val itemHargaProduct = arrayOf("3.200.000", "23.500.000")
    private val itemImgBengkel = intArrayOf(R.drawable.mitsubishi_logo, R.drawable.bmw_logo)
    private val itemNamaBengkel = arrayOf("Mitsubishi", "BMW")
    private val itemKotaBengkel = arrayOf("Medan", "Medan")
    private val itemImgRating = intArrayOf(R.drawable.ic_rating_5, R.drawable.ic_rating_5)
    private val itemRatingTxt = arrayOf("5.0", "5.0")

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var imgProduct: ImageView
        var namaProduct: TextView
        var hargaProduct: TextView
        var imgBengkel: ImageView
        var namaBengkel: TextView
        var kotaBengkel: TextView
        var imgRating: ImageView
        var ratingTxt: TextView

        init {
            imgProduct = itemView.findViewById(R.id.productImg)
            namaProduct = itemView.findViewById(R.id.productName)
            hargaProduct = itemView.findViewById(R.id.productPrice)
            imgBengkel = itemView.findViewById(R.id.bengkelImg)
            namaBengkel = itemView.findViewById(R.id.namaBengkel)
            kotaBengkel = itemView.findViewById(R.id.kotaBengkel)
            imgRating = itemView.findViewById(R.id.ratingIcon)
            ratingTxt = itemView.findViewById(R.id.ratingTxt)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_wishlist_product_details, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.imgProduct.setImageResource(itemImgProduct[position])
        holder.namaProduct.text = itemNamaProduct[position]
        holder.hargaProduct.text = itemHargaProduct[position]
        holder.imgBengkel.setImageResource(itemImgBengkel[position])
        holder.namaBengkel.text = itemNamaBengkel[position]
        holder.kotaBengkel.text = itemKotaBengkel[position]
        holder.imgRating.setImageResource(itemImgRating[position])
        holder.ratingTxt.text = itemRatingTxt[position]
    }

    override fun getItemCount(): Int {
        return itemImgProduct.size
    }
}