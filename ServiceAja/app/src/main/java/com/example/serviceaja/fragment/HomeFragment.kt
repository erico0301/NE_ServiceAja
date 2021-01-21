package com.example.serviceaja.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.serviceaja.EXTRA_USER
import com.example.serviceaja.HomeActivity
import com.example.serviceaja.R
import com.example.serviceaja.WishlistActivity
import com.example.serviceaja.classes.User
import com.example.serviceaja.recyclerview.RVBengkelPreview
import com.example.serviceaja.search.SearchActivity
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.recyclerview_review_details.*

class HomeFragment : Fragment() {
    private var layoutManager :RecyclerView.LayoutManager? = null
    private var adapter : RecyclerView.Adapter<RVBengkelPreview.ViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val user = arguments?.getParcelable<User>(EXTRA_USER)!!

        view.hello.text = "Halo, ${user.nama}"
        

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        layoutManager = LinearLayoutManager(activity)
        recyclerViewBengkelDetails.layoutManager = layoutManager

        adapter = RVBengkelPreview()
        recyclerViewBengkelDetails.adapter = adapter

        home_inputSearch.setOnFocusChangeListener { v, hasFocus ->
            if (v == home_inputSearch && hasFocus) startActivity(Intent(activity, SearchActivity::class.java))
        }

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