package com.example.serviceaja.recyclerview

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.serviceaja.R
import com.example.serviceaja.ServiceItem
import com.example.serviceaja.classes.ItemAdapter
import com.example.serviceaja.search.ProductServiceDetailActivity
import kotlinx.android.synthetic.main.layout_item_search_result_preview.view.*

class RVDaftarProduk(var daftarProduk: ArrayList<ServiceItem>): RecyclerView.Adapter<RVDaftarProduk.ViewHolder>() {
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_item_search_result_preview, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            previewItem_fotoItem.setImageResource(daftarProduk[position].fotoProduk)
            previewItem_namaItem.setText(daftarProduk[position].namaProduk)
            previewItem_hargaItem.setText(daftarProduk[position].hargaProduk.toString())
            previewItem_namaMitra.setText(daftarProduk[position].namaMitra)
            previewItem_kotaMitra.setText(daftarProduk[position].kotaMitra)

            setOnClickListener {
                context.startActivity(Intent(context, ProductServiceDetailActivity::class.java))
            }
        }
    }

    override fun getItemCount(): Int {
        return daftarProduk.size
    }
}