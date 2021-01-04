package com.example.serviceaja.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.serviceaja.R

class RecyclerViewProductServiceTransactionDetail : RecyclerView.Adapter<RecyclerViewProductServiceTransactionDetail.ViewHolder>() {

    private val itemImgService = intArrayOf(R.drawable.service_xpander, R.drawable.sill_plate_xpander)
    private val itemNamaService = arrayOf("Service 1 tahun", "Sill Plate Xpander")
    private val itemHargaService = arrayOf("0", "820.000")
    private val itemQtyService = arrayOf("1", "1")

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var imgService: ImageView
        var namaService: TextView
        var hargaService: TextView
        var quantityService: TextView

        init {
            imgService = itemView.findViewById(R.id.serviceImg)
            namaService = itemView.findViewById(R.id.namaService)
            hargaService = itemView.findViewById(R.id.servicePrice)
            quantityService = itemView.findViewById(R.id.quantityService)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_productservice_detail_transaction, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.imgService.setImageResource(itemImgService[position])
        holder.namaService.text = itemNamaService[position]
        holder.hargaService.text = itemHargaService[position]
        holder.quantityService.text = itemQtyService[position]
    }

    override fun getItemCount(): Int {
        return itemImgService.size
    }
}