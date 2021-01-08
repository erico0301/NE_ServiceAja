package com.example.serviceaja.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.serviceaja.R
import kotlinx.android.synthetic.main.layout_service_product_preview.view.*

class RVTransactionServiceProductPreview : RecyclerView.Adapter<RVTransactionServiceProductPreview.ViewHolder>() {

    private val itemImgService = intArrayOf(R.drawable.service_xpander, R.drawable.sill_plate_xpander)
    private val itemNamaService = arrayOf("Service 1 tahun", "Sill Plate Xpander")
    private val itemHargaService = arrayOf("0", "820.000")
    private val itemQtyService = arrayOf("1", "1")

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_service_product_preview, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            layoutItemPreview_fotoItem.setImageResource(itemImgService[position])
            layoutItemPreview_namaItem.text = itemNamaService[position]
            layoutItemPreview_hargaItem.text = itemHargaService[position]
            layoutItemPreview_jumlahItem.text = itemQtyService[position]
        }
    }

    override fun getItemCount(): Int {
        return itemImgService.size
    }
}