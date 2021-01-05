package com.example.serviceaja.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.serviceaja.R

class RecyclerViewReviewedDetails : RecyclerView.Adapter<RecyclerViewReviewedDetails.ViewHolder>() {

    private val itemImgProduk = intArrayOf(R.drawable.bodykit_x5, R.drawable.x5_oil)
    private val itemNamaProduk = arrayOf("Bodykit X5 M Perfomance", "Oil 4L X5")
    private val itemNamaBengkel = arrayOf("BMW", "BMW")
    private val itemImgRating = intArrayOf(R.drawable.ic_rating_5, R.drawable.ic_rating_5)
    private val itemTanggalReview = arrayOf("28 Des 2020", "28 Des 2020")
    private val itemReview = arrayOf("Pelayanan ramah dan memuaskan. Meskipun dipasang dirumah tetap" +
            "memuaskan dan rapi. Recommended seller.", "")

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var imgProduk: ImageView
        var namaProduk: TextView
        var namaBengkel: TextView
        var ratingImg: ImageView
        var tanggalReview: TextView
        var review: TextView

        init {
            imgProduk = itemView.findViewById(R.id.produkImg)
            namaProduk = itemView.findViewById(R.id.namaProduk)
            namaBengkel = itemView.findViewById(R.id.namaBengkel)
            ratingImg = itemView.findViewById(R.id.ratingIcon)
            tanggalReview = itemView.findViewById(R.id.tanggalReview)
            review = itemView.findViewById(R.id.review)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_reviewed_details, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.imgProduk.setImageResource(itemImgProduk[position])
        holder.namaProduk.text = itemNamaProduk[position]
        holder.namaBengkel.text = itemNamaBengkel[position]
        holder.ratingImg.setImageResource(itemImgRating[position])
        holder.tanggalReview.text = itemTanggalReview[position]
        holder.review.text = itemReview[position]
    }

    override fun getItemCount(): Int {
        return itemNamaProduk.size
    }
}