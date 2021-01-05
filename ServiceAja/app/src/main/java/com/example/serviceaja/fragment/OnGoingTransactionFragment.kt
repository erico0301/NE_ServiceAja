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
import com.example.serviceaja.recyclerview.RecyclerViewTransactionDetails
import com.example.serviceaja.transaction.OnGoingTransactionActivity
import kotlinx.android.synthetic.main.fragment_on_going_transaction.*

class OnGoingTransactionFragment : Fragment() {

    private var layoutManager : RecyclerView.LayoutManager? = null
    private var adapter : RecyclerView.Adapter<RecyclerViewTransactionDetails.ViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_on_going_transaction, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        layoutManager = LinearLayoutManager (activity)
        recyclerViewOnGoingTransaction.layoutManager = layoutManager

        adapter = RecyclerViewTransactionDetails()
        recyclerViewOnGoingTransaction.adapter = adapter

    }

    companion object {

    }
}