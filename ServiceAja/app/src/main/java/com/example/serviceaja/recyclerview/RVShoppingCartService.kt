package com.example.serviceaja.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.serviceaja.R
import com.example.serviceaja.classes.ItemServiceInstance
import kotlinx.android.synthetic.main.layout_shopping_cart_service.view.*

class RVShoppingCartService(
        var itemServiceInstances: MutableList<ItemServiceInstance>
): RecyclerView.Adapter<RVShoppingCartService.ViewHolder>() {

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) { }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_shopping_cart_service, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            shoppingCartService_fotoItem.setImageResource(itemServiceInstances[position].fotoItem)
            shoppingCartService_namaItem.text = itemServiceInstances[position].namaItem
            shoppingCartService_hargaItem.text = itemServiceInstances[position].hargaItem
            shoppingCartService_namaKendaraan.text = itemServiceInstances[position].namaMobil
            shoppingCartService_platKendaraan.text = itemServiceInstances[position].platMobil
        }
    }

    override fun getItemCount(): Int {
        return itemServiceInstances.size
    }

}