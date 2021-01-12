package com.example.serviceaja.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.example.serviceaja.R
import com.example.serviceaja.classes.ItemProductInstance
import kotlinx.android.synthetic.main.layout_shopping_cart_product.view.*

class RVShoppingCartProduct(
        var itemProductInstances: MutableList<ItemProductInstance>
): RecyclerView.Adapter<RVShoppingCartProduct.ViewHolder>() {
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RVShoppingCartProduct.ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_shopping_cart_product, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RVShoppingCartProduct.ViewHolder, position: Int) {
        holder.itemView.apply {
            shoppingCartProduct_fotoItem.setImageResource(itemProductInstances[position].fotoItem)
            shoppingCartProduct_namaItem.text = itemProductInstances[position].namaItem
            shoppingCartProduct_hargaItem.text = itemProductInstances[position].hargaItem
            shoppingCartProduct_jumlahItem.text = itemProductInstances[position].jumlahItem.toString()
        }
    }

    override fun getItemCount(): Int {
        return itemProductInstances.size
    }

}