package com.example.serviceaja.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.serviceaja.R
import com.example.serviceaja.recyclerview.RecyclerViewTransactionDoneDetails
import com.example.serviceaja.transaction.DoneTransactionActivity
import kotlinx.android.synthetic.main.fragment_done_transaction.*

class DoneTransactionFragment : Fragment() {

    private var layoutManager : RecyclerView.LayoutManager? = null
    private var adapter : RecyclerView.Adapter<RecyclerViewTransactionDoneDetails.ViewHolder>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_done_transaction, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        layoutManager = LinearLayoutManager (activity)
        recyclerViewDoneTransaction.layoutManager = layoutManager

        adapter = RecyclerViewTransactionDoneDetails()
        recyclerViewDoneTransaction.adapter = adapter
    }

    companion object {

    }
}