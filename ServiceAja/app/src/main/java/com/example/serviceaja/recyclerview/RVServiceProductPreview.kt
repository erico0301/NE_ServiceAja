package com.example.serviceaja.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.serviceaja.R
import kotlinx.android.synthetic.main.layout_service_product_small_pic.view.*

class RVServiceProductPreview : RecyclerView.Adapter<RVServiceProductPreview.ViewHolder>() {

    private val itemImgProduct = intArrayOf(R.drawable.fb_aki, R.drawable.service_bmw)
    private val itemNamaProduct = arrayOf("Battery Xpander FB (Kering)", "Car Wash")
    private val itemHargaProduct = arrayOf("650.000", "50.000")
    private val itemNamaBengkel = arrayOf("Mitsubishi", "BMW")
    private val itemKotaBengkel = arrayOf("Medan", "Medan")
    private val itemImgRating = intArrayOf(R.drawable.ic_rating_5, R.drawable.ic_rating_5)
    private val itemRatingTxt = arrayOf("5.0", "5.0")

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_service_product_small_pic, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            layoutNarrow_fotoItem.setImageResource(itemImgProduct[position])
            layoutNarrow_namaItem.text = itemNamaProduct[position]
            layoutNarrow_hargaItem.text = itemHargaProduct[position]
            layoutNarrow_namaMitra.text = itemNamaBengkel[position]
            layoutNarrow_kotaMitra.text = itemKotaBengkel[position]
        }
    }

    override fun getItemCount(): Int {
        return itemNamaProduct.size
    }
}