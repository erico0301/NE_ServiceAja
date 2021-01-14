package com.example.serviceaja.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.serviceaja.R
import kotlinx.android.synthetic.main.layout_item_search_result_preview.view.*

class RecyclerViewProductDetailsSearchResult : RecyclerView.Adapter<RecyclerViewProductDetailsSearchResult.ViewHolder>() {

    private val itemImgProduct = intArrayOf(R.drawable.fb_aki, R.drawable.ban_rft)
    private val itemNamaProduct = arrayOf("Battery Furukawa Battery (Kering)", "Ban RFT 18")
    private val itemFotoBengkel = intArrayOf(R.drawable.mitsubishi_logo, R.drawable.bmw_logo)
    private val itemNamaBengkel = arrayOf("Mitsubishi", "BMW")
    private val itemKotaBengkel = arrayOf("Medan", "Medan")
    private val itemHargaService = arrayOf("800.000", "2.000.000")

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_item_search_result_preview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            previewItem_fotoItem.setImageResource(itemImgProduct[position])
            previewItem_namaItem.text = itemNamaProduct[position]
            previewItem_namaMitra.text = itemNamaBengkel[position]
            previewItem_kotaMitra.text = itemKotaBengkel[position]
            previewItem_fotoMitra.setImageResource(itemFotoBengkel[position])
            previewItem_hargaItem.text = itemHargaService[position]
        }
    }

    override fun getItemCount(): Int {
        return itemNamaProduct.size
    }
}