package com.example.serviceaja.transaction

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.serviceaja.R
import com.example.serviceaja.recyclerview.RVTransactionServiceProductPreview
import kotlinx.android.synthetic.main.activity_on_going_transaction.*

class OnGoingTransactionActivity : AppCompatActivity() {

    private var layoutManager : RecyclerView.LayoutManager? = null
    private var adapter : RecyclerView.Adapter<RVTransactionServiceProductPreview.ViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_going_transaction)

        detailTransaksi_toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        layoutManager = LinearLayoutManager (this)
        detailTransaksi_rvItem.layoutManager = layoutManager
        adapter = RVTransactionServiceProductPreview()
        detailTransaksi_rvItem.adapter = adapter
    }
}