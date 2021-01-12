package com.example.serviceaja

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.serviceaja.recyclerview.RVWishList
import kotlinx.android.synthetic.main.activity_wishlist.*

class WishlistActivity : AppCompatActivity() {

    private var layoutManager : RecyclerView.LayoutManager? = null
    private var adapter : RecyclerView.Adapter<RVWishList.ViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wishlist)

        backBtn.setOnClickListener {
            onBackPressed()
        }

        layoutManager = LinearLayoutManager (this)
        recyclerViewDetailsWishlist.layoutManager = layoutManager

        adapter = RVWishList()
        recyclerViewDetailsWishlist.adapter = adapter

        wishTxt.setOnClickListener {
            var dialog = AlertDialog.Builder(this)
                .setTitle("Konfirmasi Hapus")
                .setMessage("Apakah Anda yakin ingin menghapus barang ini dari Wishlist ?")
                .setPositiveButton("Ya, hapus saja", DialogInterface.OnClickListener { dialog, which ->
                    dialog.cancel()
                })
                .setNegativeButton("Tidak, tinggalkan di Wishlist",
                    DialogInterface.OnClickListener { dialog, which ->
                    dialog.cancel()
                })
            dialog.show()
        }

    }
}