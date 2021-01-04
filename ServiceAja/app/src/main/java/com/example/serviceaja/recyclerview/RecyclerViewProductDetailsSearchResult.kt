package com.example.serviceaja.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.serviceaja.R

class RecyclerViewProductDetailsSearchResult : RecyclerView.Adapter<RecyclerViewProductDetailsSearchResult.ViewHolder>() {

    private val itemImgProduct = intArrayOf(R.drawable.fb_aki, R.drawable.ban_rft)
    private val itemNamaProduct = arrayOf("Battery Furukawa Battery (Kering)", "Ban RFT 18")
    private val itemNamaBengkel = arrayOf("Mitsubishi", "BMW")
    private val itemKotaBengkel = arrayOf("Medan", "Medan")
    private val itemImgRating = intArrayOf(R.drawable.ic_rating_5, R.drawable.ic_rating_5)
    private val itemRatingTxt = arrayOf("5.0", "5.0")

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var imgProduct: ImageView
        var namaProduct: TextView
        var namaBengkel: TextView
        var kotaBengkel: TextView
        var imgRating: ImageView
        var ratingTxt: TextView

        init {
            imgProduct = itemView.findViewById(R.id.bengkelImg)
            namaProduct = itemView.findViewById(R.id.namaBengkel)
            namaBengkel = itemView.findViewById(R.id.alamatBengkel)
            kotaBengkel = itemView.findViewById(R.id.kotaBengkel)
            imgRating = itemView.findViewById(R.id.ratingIcon)
            ratingTxt = itemView.findViewById(R.id.ratingTxt)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_bengkel_details, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.imgProduct.setImageResource(itemImgProduct[position])
        holder.namaProduct.text = itemNamaProduct[position]
        holder.namaBengkel.text = itemNamaBengkel[position]
        holder.kotaBengkel.text = itemKotaBengkel[position]
        holder.imgRating.setImageResource(itemImgRating[position])
        holder.ratingTxt.text = itemRatingTxt[position]
    }

    override fun getItemCount(): Int {
        return itemNamaProduct.size
    }
}