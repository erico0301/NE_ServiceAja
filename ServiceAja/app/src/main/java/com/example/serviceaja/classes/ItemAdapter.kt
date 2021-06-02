package com.example.serviceaja.classes

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.serviceaja.R
import com.example.serviceaja.ServiceItem
import com.example.serviceaja.search.ProductServiceDetailActivity
import kotlinx.android.synthetic.main.layout_item_search_result_preview.view.*
import kotlinx.android.synthetic.main.layout_service_product_small_pic.view.*

class ItemAdapter(
    var items: List<ServiceItem>
    ) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context).inflate(R.layout.layout_item_search_result_preview, parent, false)
        return ItemViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.itemView.apply {
            previewItem_fotoItem.setImageResource(items[position].fotoProduk)
            previewItem_namaItem.setText(items[position].namaProduk)
            previewItem_hargaItem.setText(items[position].hargaProduk.toString())
            previewItem_namaMitra.setText(items[position].namaMitra)
            previewItem_kotaMitra.setText(items[position].kotaMitra)

            setOnClickListener {
                context.startActivity(Intent(context, ProductServiceDetailActivity::class.java))
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
}