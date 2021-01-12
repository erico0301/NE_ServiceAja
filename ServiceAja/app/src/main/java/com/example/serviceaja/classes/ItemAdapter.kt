package com.example.serviceaja.classes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.serviceaja.R
import com.example.serviceaja.ServiceItem
import kotlinx.android.synthetic.main.layout_service_product_small_pic.view.*

class ItemAdapter(
    var items: List<ServiceItem>
    ) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.layout_service_product_small_pic, parent, false)
        return ItemViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.itemView.apply {
            layoutNarrow_fotoItem.setImageResource(items[position].fotoProduk)
            layoutNarrow_namaItem.setText(items[position].namaProduk)
            layoutNarrow_hargaItem.setText("Rp ${items[position].hargaProduk}")
            layoutNarrow_namaMitra.setText(items[position].idMitra)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}