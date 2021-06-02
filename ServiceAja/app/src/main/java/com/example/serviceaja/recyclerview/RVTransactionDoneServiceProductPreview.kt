package com.example.serviceaja.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.serviceaja.R
import kotlinx.android.synthetic.main.layout_transaction_product_preview.view.*

class RVTransactionDoneServiceProductPreview : RecyclerView.Adapter<RVTransactionDoneServiceProductPreview.ViewHolder>() {

    private val itemImgProduct = intArrayOf(R.drawable.bodykit_x5, R.drawable.x5_oil)
    private val itemNamaProduct = arrayOf("Bodykit X5 M Performance", "Oil 4L X5")
    private val itemHargaProduct = arrayOf("23.120.000", "1.230.000")
    private val itemQtyProduct = arrayOf("1", "1")

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_transaction_product_preview, parent, false)
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
        return itemImgProduct.size
    }

}