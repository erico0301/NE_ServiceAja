package com.example.serviceaja.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.serviceaja.R
import kotlinx.android.synthetic.main.layout_service_product_preview.view.*

class RecyclerViewCheckoutProductServiceDetails : RecyclerView.Adapter<RecyclerViewCheckoutProductServiceDetails.ViewHolder>() {

    private val itemImgProduct = intArrayOf(R.drawable.ban_rft, R.drawable.wiper_bmw)
    private val itemNamaProduct = arrayOf("Ban X5 RFT", "Wiper X5")
    private val itemHargaProduct = arrayOf("0", "750.000")
    private val itemQtyProduct = arrayOf("2", "1")

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_service_product_preview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            layoutItemPreview_fotoItem.setImageResource(itemImgProduct[position])
            layoutItemPreview_namaItem.text = itemNamaProduct[position]
            layoutItemPreview_hargaItem.text = itemHargaProduct[position]
            layoutItemPreview_jumlahItem.text = itemQtyProduct[position]
        }
    }

    override fun getItemCount(): Int {
        return itemNamaProduct.size
    }
}