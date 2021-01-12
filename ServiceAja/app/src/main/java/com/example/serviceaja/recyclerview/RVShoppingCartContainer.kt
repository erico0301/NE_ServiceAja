package com.example.serviceaja.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.serviceaja.R
import com.example.serviceaja.classes.ShoppingCartInstance
import kotlinx.android.synthetic.main.layout_shopping_cart_container.view.*

class RVShoppingCartContainer(
        var shoppingCartInstance: List<ShoppingCartInstance>
) : RecyclerView.Adapter<RVShoppingCartContainer.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_shopping_cart_container, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            shoppingCartContainer_fotoMitra.setImageResource(shoppingCartInstance[position].fotoMitra)
            shoppingCartContainer_namaMitra.text = shoppingCartInstance[position].namaMitra
            shoppingCartContainer_kotaMitra.text = shoppingCartInstance[position].kotaMitra

            shoppingCartContainer_itemService.adapter = RVShoppingCartService(shoppingCartInstance[position].itemServiceInstances)
            shoppingCartContainer_itemService.layoutManager = LinearLayoutManager(context)
            shoppingCartContainer_itemService.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))

            shoppingCartContainer_itemProduk.adapter = RVShoppingCartProduct(shoppingCartInstance[position].itemProductInstances)
            shoppingCartContainer_itemProduk.layoutManager = LinearLayoutManager(context)
            shoppingCartContainer_itemProduk.addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }
    }

    override fun getItemCount(): Int {
        return shoppingCartInstance.size
    }

}