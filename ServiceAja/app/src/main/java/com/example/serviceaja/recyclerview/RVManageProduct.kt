package com.example.serviceaja.recyclerview

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.serviceaja.ManageProduct
import com.example.serviceaja.R
import com.example.serviceaja.classes.Produk
import kotlinx.android.synthetic.main.layout_manage_product.view.*

class RVManageProduct(val context: Context, val type: String, val daftarProduk: ArrayList<Produk>): RecyclerView.Adapter<RVManageProduct.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_manage_product, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            layoutManageProduct_no.text = (position + 1).toString()
            layoutmanageProduct_namaProduk.setText(daftarProduk[position].nama)
            layoutManageProduct_hargaProduk.setText(daftarProduk[position].harga.toString())

            if (type == "Edit") {
                layoutmanageProduct_namaProduk.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                    override fun afterTextChanged(s: Editable?) {
                        (context as ManageProduct).apply {
                            if (position in editedProducts) {
                                for (i in 0 until editedProducts.size)
                                    if (editedProducts[i] == position) {
                                        editedProductsResult[i] = Produk(layoutmanageProduct_namaProduk.text.toString(), layoutManageProduct_hargaProduk.text.toString().toLong())
                                        break
                                    }
                                return
                            }
                            editedProducts.add(position)
                            editedProductsResult.add(Produk(layoutmanageProduct_namaProduk.text.toString(), layoutManageProduct_hargaProduk.text.toString().toLong()))
                        }
                    }
                })
                layoutManageProduct_hargaProduk.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
                    override fun afterTextChanged(s: Editable?) {
                        (context as ManageProduct).apply {
                            if (position in editedProducts) {
                                for (i in 0 until editedProducts.size)
                                    if (editedProducts[i] == position) {
                                        editedProductsResult[i] = Produk(layoutmanageProduct_namaProduk.text.toString(), layoutManageProduct_hargaProduk.text.toString().toLong())
                                        break
                                    }
                                return
                            }
                            editedProducts.add(position)
                            editedProductsResult.add(Produk(layoutmanageProduct_namaProduk.text.toString(), layoutManageProduct_hargaProduk.text.toString().toLong()))
                        }
                    }
                })
            }
        }
    }

    override fun getItemCount(): Int {
        return daftarProduk.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
}