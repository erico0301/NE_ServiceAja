package com.example.serviceaja

import android.content.Intent
import android.database.sqlite.SQLiteException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.serviceaja.classes.DBHelper
import com.example.serviceaja.classes.Produk
import com.example.serviceaja.recyclerview.RVDaftarProdukDownload
import com.example.serviceaja.recyclerview.RVManageProduct
import kotlinx.android.synthetic.main.activity_manage_product.*
import org.jetbrains.anko.doAsync
import java.sql.SQLException

class ManageProduct : AppCompatActivity() {
    lateinit var editedProducts: ArrayList<Int>
    lateinit var editedProductsResult: ArrayList<Produk>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_product)
        val db = DBHelper(this)
        val type = intent.getStringExtra("MANAGE_PRODUCT")
        if (type == "Edit") {
            manageProduct_fabAddProduct.visibility = View.GONE
            val products = intent.getSerializableExtra(DAFTAR_PRODUK) as ArrayList<Produk>
            editedProducts = arrayListOf()
            editedProductsResult = arrayListOf()
            manageProduct_daftarProduk.apply {
                adapter = RVManageProduct(this@ManageProduct, type, products)
                layoutManager = LinearLayoutManager(this@ManageProduct)
            }
            manageProduct_btnSelesai.setOnClickListener {
                db.beginTransaction
                try {
                    doAsync {
                        for (i in 0 until editedProducts.size) {
                            db.updateProduct(products[editedProducts[i]], editedProductsResult[i])
                        }
                    }
                    db.successTransaction
                } catch (e: SQLiteException) {
                    Log.e("Error while running update query in database", e.toString())
                } finally {
                    db.endTransaction
                }
                setResult(RESULT_OK)
                finish()
            }
        }
        else {
            val products = arrayListOf(Produk("", 0))
            manageProduct_daftarProduk.apply {
                adapter = RVManageProduct(this@ManageProduct, type!!, products)
                layoutManager = LinearLayoutManager(this@ManageProduct)
            }
            manageProduct_fabAddProduct.setOnClickListener {
                products.add(Produk("", 0))
                manageProduct_daftarProduk.adapter!!.notifyItemInserted(manageProduct_daftarProduk.adapter!!.itemCount)
            }
        }

    }
}