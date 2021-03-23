package com.example.serviceaja.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.serviceaja.R
import kotlinx.android.synthetic.main.layout_service_product_wide_pic.view.*

class RVWishList : RecyclerView.Adapter<RVWishList.ViewHolder>() {

    private val itemImgProduct = intArrayOf(R.drawable.dashcam, R.drawable.bodykit_x5)
    private val itemNamaProduct = arrayOf("Dashcam BlackVue 60fps FHD (Front)", "BodyKit BMW X5")
    private val itemHargaProduct = arrayOf("3.200.000", "23.500.000")
    private val itemImgBengkel = intArrayOf(R.drawable.mitsubishi_logo, R.drawable.bmw_logo)
    private val itemNamaBengkel = arrayOf("Mitsubishi", "BMW")
    private val itemKotaBengkel = arrayOf("Medan", "Medan")
    private val itemImgRating = intArrayOf(R.drawable.ic_rating_5, R.drawable.ic_rating_5)
    private val itemRatingTxt = arrayOf("5.0", "5.0")

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_service_product_wide_pic, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            layoutWide_fotoItem.setImageResource(itemImgProduct[position])
            layoutWide_namaItem.text = itemNamaProduct[position]
            layoutWide_hargaItem.text = itemHargaProduct[position]
            layoutWide_fotoMitra.setImageResource(itemImgBengkel[position])
            layoutWide_namaMitra.text = itemNamaBengkel[position]
            layoutWide_kotaMitra.text = itemKotaBengkel[position]
        }
    }

    override fun getItemCount(): Int {
        return itemImgProduct.size
    }
}