package com.example.serviceaja.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.serviceaja.R

class RecyclerViewServiceDetailsSearchResult : RecyclerView.Adapter<RecyclerViewServiceDetailsSearchResult.ViewHolder> () {

    private val itemImgService = intArrayOf(R.drawable.wash_xpander, R.drawable.balancing_spooring, R.drawable.service_bmw)
    private val itemNamaService = arrayOf("Car Wash", "Balancing Spooring 4D", "BMW")
    private val itemNamaBengkel = arrayOf("Mitsubishi", "BMW", "Peugout")
    private val itemKotaBengkel = arrayOf("Medan", "Medan", "Medan")
    private val itemImgRating = intArrayOf(R.drawable.ic_rating_5, R.drawable.ic_rating_5, R.drawable.ic_rating_5)
    private val itemRatingTxt = arrayOf("5.0", "5.0", "5.0")

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var imgService: ImageView
        var namaService: TextView
        var namaBengkel: TextView
        var kotaBengkel: TextView
        var imgRating: ImageView
        var ratingTxt: TextView

        init {
            imgService = itemView.findViewById(R.id.bengkelImg)
            namaService = itemView.findViewById(R.id.namaBengkel)
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
        holder.imgService.setImageResource(itemImgService[position])
        holder.namaService.text = itemNamaService[position]
        holder.namaBengkel.text = itemNamaBengkel[position]
        holder.kotaBengkel.text = itemKotaBengkel[position]
        holder.imgRating.setImageResource(itemImgRating[position])
        holder.ratingTxt.text = itemRatingTxt[position]
    }

    override fun getItemCount(): Int {
        return itemNamaService.size
    }
}