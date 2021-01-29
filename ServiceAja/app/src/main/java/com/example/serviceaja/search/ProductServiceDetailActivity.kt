package com.example.serviceaja.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.serviceaja.R
import com.example.serviceaja.recyclerview.RecyclerViewReviewDetails
import kotlinx.android.synthetic.main.activity_product_service_detail.*
import kotlinx.android.synthetic.main.dialog_service_booking.view.*
import java.time.LocalDate
import java.util.*

class ProductServiceDetailActivity : AppCompatActivity() {

    private var layoutManager : RecyclerView.LayoutManager? = null
    private var adapter : RecyclerView.Adapter<RecyclerViewReviewDetails.ViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_service_detail)

        detailProduk_toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        layoutManager = LinearLayoutManager (this)
        recyclerViewReviewDetails.layoutManager = layoutManager
        adapter = RecyclerViewReviewDetails()
        recyclerViewReviewDetails.adapter = adapter
    }
}