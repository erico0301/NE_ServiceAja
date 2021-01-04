package com.example.serviceaja.transaction

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.serviceaja.R
import com.example.serviceaja.recyclerview.RecyclerViewProductServiceTransactionDetail
import kotlinx.android.synthetic.main.activity_on_going_transaction.*

class OnGoingTransactionActivity : AppCompatActivity() {

    private var layoutManager : RecyclerView.LayoutManager? = null
    private var adapter : RecyclerView.Adapter<RecyclerViewProductServiceTransactionDetail.ViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_going_transaction)

        backBtn.setOnClickListener {
            onBackPressed()
        }

        layoutManager = LinearLayoutManager (this)
        recyclerViewOnGoingTransactionDetails.layoutManager = layoutManager
        adapter = RecyclerViewProductServiceTransactionDetail()
        recyclerViewOnGoingTransactionDetails.adapter = adapter
    }
}