package com.example.serviceaja.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.serviceaja.R

class RecyclerViewReviewDetails : RecyclerView.Adapter<RecyclerViewReviewDetails.ViewHolder>() {

    private val itemImgUser = intArrayOf(R.drawable.profile_2, R.drawable.profile_2)
    private val itemNamaUser = arrayOf("Jessica", "Romy")
    private val itemReview = arrayOf("Service bagus, ramah. Pelanggan dilayani dengan baik dan penjelasan yang detail", " ")
    private val itemImgRating = intArrayOf(R.drawable.ic_rating_5, R.drawable.ic_rating_5)
    private val itemRatingTxt = arrayOf("5.0", "5.0")

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var imgUser: ImageView
        var namaUser: TextView
        var review: TextView
        var imgRating: ImageView
        var ratingTxt: TextView

        init {
            imgUser = itemView.findViewById(R.id.userProfile)
            namaUser = itemView.findViewById(R.id.userName)
            review = itemView.findViewById(R.id.review)
            imgRating = itemView.findViewById(R.id.ratingIcon)
            ratingTxt = itemView.findViewById(R.id.ratingTxt)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_review_details, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.imgUser.setImageResource(itemImgUser[position])
        holder.namaUser.text = itemNamaUser[position]
        holder.review.text = itemReview[position]
        holder.imgRating.setImageResource(itemImgRating[position])
        holder.ratingTxt.text = itemRatingTxt[position]

    }

    override fun getItemCount(): Int {
        return itemNamaUser.size
    }
}