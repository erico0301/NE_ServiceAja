package com.example.serviceaja

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.service_and_product_layout.view.*

class ItemAdapter(
    var items: List<ServiceItem>
    ) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.service_and_product_layout, parent, false)
        return ItemViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.itemView.apply {
            serviceproductLayout_fotoItem.setImageResource(items[position].fotoProduk)
            serviceproductLayout_namaItem.setText(items[position].namaProduk)
            serviceproductLayout_hargaItem.setText("Rp ${items[position].hargaProduk}")
            serviceproductLayout_namaMitra.setText(items[position].idMitra)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}