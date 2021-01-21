package com.example.serviceaja.recyclerview

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.serviceaja.R
import com.example.serviceaja.chatreview.ChatActivity
import com.example.serviceaja.chatreview.ReviewActivity
import kotlinx.android.synthetic.main.recyclerview_unreviewed_details.view.*

class RecyclerViewUnreviewedDetails : RecyclerView.Adapter<RecyclerViewUnreviewedDetails.ViewHolder>() {

    private val itemImgService = intArrayOf(R.drawable.service_xpander, R.drawable.sill_plate_xpander)
    private val itemNamaService = arrayOf("Service 1 tahun", "Sill Plate Xpander full")

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_unreviewed_details, parent, false)
        view.setOnClickListener {
            parent.context.startActivity(Intent(parent.context, ReviewActivity::class.java))
        }

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            serviceImg.setImageResource(itemImgService[position])
            namaService.text = itemNamaService[position]
        }

    }

    override fun getItemCount(): Int {
        return itemImgService.size
    }
}