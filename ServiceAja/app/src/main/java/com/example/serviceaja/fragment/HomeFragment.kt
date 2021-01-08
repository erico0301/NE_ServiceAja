package com.example.serviceaja.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.serviceaja.HomeActivity
import com.example.serviceaja.R
import com.example.serviceaja.WishlistActivity
import com.example.serviceaja.recyclerview.RecyclerViewBengkelDetails
import com.example.serviceaja.search.SearchActivity
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.recyclerview_wishlist_product_details.*

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

        layoutManager = LinearLayoutManager(activity)
        recyclerViewBengkelDetails.layoutManager = layoutManager

        adapter = RecyclerViewBengkelDetails()
        recyclerViewBengkelDetails.adapter = adapter

        home_inputSearch.setOnClickListener {
            var searchIntent = Intent(activity, SearchActivity::class.java)
            startActivity(searchIntent)
        }

        wishlistIcon.setOnClickListener {
            var wishlistIntent = Intent(activity, WishlistActivity::class.java)
            startActivity(wishlistIntent)
        }
    }

    companion object {

    }
}