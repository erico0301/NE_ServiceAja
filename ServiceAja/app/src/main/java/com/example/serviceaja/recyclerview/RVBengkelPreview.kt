package com.example.serviceaja.recyclerview

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.serviceaja.ProfilMitra
import com.example.serviceaja.R
import kotlinx.android.synthetic.main.layout_mitra_wide_pic.view.*

class RVBengkelPreview : RecyclerView.Adapter<RVBengkelPreview.ViewHolder>() {

    private val itemImgBengkel = intArrayOf(R.drawable.service_xpander, R.drawable.service_bmw)
    private val itemNamaBengkel = arrayOf("Mitsubishi", "BMW")
    private val itemAlamatBengkel = arrayOf("Jl. Gatot Subroto", "Jl. Adam Malik")
    private val itemKotaBengkel = arrayOf("Medan", "Medan")
    private val itemImgRating = intArrayOf(R.drawable.ic_rating_5, R.drawable.ic_rating_5)
    private val itemRatingTxt = arrayOf("5.0", "5.0")

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_mitra_wide_pic, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            layoutMitra_fotoBengkel.setImageResource(itemImgBengkel[position])
            layoutMitra_namaBengkel.text = itemNamaBengkel[position]
            layoutMitra_alamatMitra.text = itemAlamatBengkel[position]
            layoutMitra_kotaMitra.text = itemKotaBengkel[position]

            setOnClickListener {
                context.startActivity(Intent(context, ProfilMitra::class.java))
            }
        }
    }

    override fun getItemCount(): Int {
        return itemImgBengkel.size
    }
}