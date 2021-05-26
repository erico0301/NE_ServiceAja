package com.example.serviceaja.recyclerview

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.serviceaja.R
import com.example.serviceaja.search.ProductServiceDetailActivity
import kotlinx.android.synthetic.main.layout_item_search_result_preview.view.*

class RecyclerViewServiceDetailsSearchResult : RecyclerView.Adapter<RecyclerViewServiceDetailsSearchResult.ViewHolder> () {

    private val itemImgService = intArrayOf(R.drawable.wash_xpander, R.drawable.xpander_sound_system, R.drawable.service_bmw)
    private val itemNamaService = arrayOf("Car Wash", "Xpander Sound System Package", "BMW")
    private val itemFotoBengkel = intArrayOf(R.drawable.mitsubishi_logo, R.drawable.mitsubishi_logo, R.drawable.bmw_logo)
    private val itemNamaBengkel = arrayOf("Mitsubishi", "Mitsubishi", "BMW")
    private val itemKotaBengkel = arrayOf("Medan", "Medan", "Medan")
    private val itemHargaService = arrayOf("75.000", "5.230.000", "300.000")

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {

            itemView.setOnClickListener {
                if (adapterPosition == 1) {
                    val productServiceDetailsIntent = Intent(itemView.context, ProductServiceDetailActivity::class.java)
                    itemView.context.startActivity(productServiceDetailsIntent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_item_search_result_preview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            previewItem_fotoItem.setImageResource(itemImgService[position])
            previewItem_namaItem.text = itemNamaService[position]
            previewItem_namaMitra.text = itemNamaBengkel[position]
            previewItem_kotaMitra.text = itemKotaBengkel[position]
            previewItem_fotoMitra.setImageResource(itemFotoBengkel[position])
            previewItem_hargaItem.text = itemHargaService[position]

            setOnClickListener {
                val productDetails = Intent(context, ProductServiceDetailActivity::class.java)
                context.startActivity(productDetails)
            }
        }
    }

    override fun getItemCount(): Int {
        return itemNamaService.size
    }
}