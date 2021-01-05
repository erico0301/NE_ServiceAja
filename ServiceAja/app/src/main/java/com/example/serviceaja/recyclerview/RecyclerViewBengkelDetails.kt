package com.example.serviceaja.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.serviceaja.R

class RecyclerViewBengkelDetails : RecyclerView.Adapter<RecyclerViewBengkelDetails.ViewHolder>() {

    private val itemImgBengkel = intArrayOf(R.drawable.service_xpander, R.drawable.service_bmw)
    private val itemNamaBengkel = arrayOf("Mitsubishi", "BMW")
    private val itemAlamatBengkel = arrayOf("Jl. Gatot Subroto", "Jl. Adam Malik")
    private val itemKotaBengkel = arrayOf("Medan", "Medan")
    private val itemImgRating = intArrayOf(R.drawable.ic_rating_5, R.drawable.ic_rating_5)
    private val itemRatingTxt = arrayOf("5.0", "5.0")

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        var imgBengkel: ImageView
        var namaBengkel: TextView
        var alamatBengkel: TextView
        var kotaBengkel: TextView
        var imgRating: ImageView
        var ratingTxt: TextView

        init {
            imgBengkel = itemView.findViewById(R.id.bengkelImg)
            namaBengkel = itemView.findViewById(R.id.namaBengkel)
            alamatBengkel = itemView.findViewById(R.id.alamatBengkel)
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
        holder.imgBengkel.setImageResource(itemImgBengkel[position])
        holder.namaBengkel.text = itemNamaBengkel[position]
        holder.alamatBengkel.text = itemAlamatBengkel[position]
        holder.kotaBengkel.text = itemKotaBengkel[position]
        holder.imgRating.setImageResource(itemImgRating[position])
        holder.ratingTxt.text = itemRatingTxt[position]
    }

    override fun getItemCount(): Int {
        return itemImgBengkel.size
    }
}