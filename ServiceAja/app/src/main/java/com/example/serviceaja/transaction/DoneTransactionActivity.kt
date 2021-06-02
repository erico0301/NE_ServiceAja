package com.example.serviceaja.transaction

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.serviceaja.R
import com.example.serviceaja.recyclerview.RVTransactionDoneServiceProductPreview
import kotlinx.android.synthetic.main.activity_done_transaction.*
import kotlinx.android.synthetic.main.activity_done_transaction.backBtn

class DoneTransactionActivity : AppCompatActivity() {

    private var layoutManager : RecyclerView.LayoutManager? = null
    private var adapter : RecyclerView.Adapter<RVTransactionDoneServiceProductPreview.ViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_done_transaction)

        backBtn.setOnClickListener {
            onBackPressed()
        }

        layoutManager = LinearLayoutManager (this)
        recyclerViewDoneTransactionDetails.layoutManager = layoutManager
        adapter = RVTransactionDoneServiceProductPreview()
        recyclerViewDoneTransactionDetails.adapter = adapter
    }
}