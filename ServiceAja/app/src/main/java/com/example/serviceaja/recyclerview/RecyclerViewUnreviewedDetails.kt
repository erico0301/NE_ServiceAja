package com.example.serviceaja.recyclerview

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.serviceaja.R
import com.example.serviceaja.chatreview.ReviewActivity

class RecyclerViewUnreviewedDetails : RecyclerView.Adapter<RecyclerViewUnreviewedDetails.ViewHolder>() {

    private val itemImgService = intArrayOf(R.drawable.service_xpander, R.drawable.sill_plate_xpander)
    private val itemNamaService = arrayOf("Service 1 tahun", "Sill Plate Xpander full")

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var imgService: ImageView
        var namaService: TextView

        init {
            imgService = itemView.findViewById(R.id.serviceImg)
            namaService = itemView.findViewById(R.id.namaService)

            itemView.setOnClickListener {
                if (adapterPosition == 1) {
                    val reviewIntent = Intent(itemView.context, ReviewActivity::class.java)
                    itemView.context.startActivity(reviewIntent)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_unreviewed_details, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.imgService.setImageResource(itemImgService[position])
        holder.namaService.text = itemNamaService[position]
    }

    override fun getItemCount(): Int {
        return itemImgService.size
    }
}