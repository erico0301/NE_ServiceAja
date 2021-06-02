package com.example.serviceaja.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.serviceaja.R

class RecyclerViewBengkelDetailsPreview : RecyclerView.Adapter<RecyclerViewBengkelDetailsPreview.ViewHolder>() {

    private val itemImgBengkel = intArrayOf(R.drawable.rolls_royce_logo, R.drawable.bmw_logo)
    private val itemNamaBengkel = arrayOf("Rolls Royce", "BMW")
    private val itemKotaBengkel = arrayOf("Medan", "Medan")

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var imgBengkel : ImageView
        var namaBengkel : TextView
        var kotaBengkel : TextView

        init {
            imgBengkel = itemView.findViewById(R.id.bengkelImg)
            namaBengkel = itemView.findViewById(R.id.namaBengkel)
            kotaBengkel = itemView.findViewById(R.id.kotaBengkel)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recylerview_bengkel_details_preview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.imgBengkel.setImageResource(itemImgBengkel[position])
        holder.namaBengkel.text = itemNamaBengkel[position]
        holder.kotaBengkel.text = itemKotaBengkel[position]
    }

    override fun getItemCount(): Int {
        return itemKotaBengkel.size
    }
}
