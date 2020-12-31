package com.example.serviceaja.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.serviceaja.R
import com.example.serviceaja.recyclerview.RecyclerViewBengkelDetails
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {
    private var layoutManager :RecyclerView.LayoutManager? = null
    private var adapter : RecyclerView.Adapter<RecyclerViewBengkelDetails.ViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        layoutManager = GridLayoutManager (activity, 2, LinearLayoutManager.VERTICAL, false)
        recyclerViewBengkelDetails.layoutManager = layoutManager

        adapter = RecyclerViewBengkelDetails()
        recyclerViewBengkelDetails.adapter = adapter
    }

    companion object {

    }
}