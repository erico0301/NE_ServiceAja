package com.example.serviceaja.search

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.serviceaja.R
import com.example.serviceaja.recyclerview.RVTransactionServiceProductPreview
import com.example.serviceaja.recyclerview.RecyclerViewBengkelDetailsPreview
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity() {

    private var layoutManagerProduct : RecyclerView.LayoutManager? = null
    private var layoutManagerBengkel : RecyclerView.LayoutManager? = null
    private var adapterProduct : RecyclerView.Adapter<RVTransactionServiceProductPreview.ViewHolder>? = null
    private var adapterBengkel : RecyclerView.Adapter<RecyclerViewBengkelDetailsPreview.ViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        backBtn.setOnClickListener {
            onBackPressed()
        }

        searchEditText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                var searchResultIntent = Intent(this, SearchResultActivity::class.java)
                startActivity(searchResultIntent)
            }
            return@setOnEditorActionListener true
        }

        searchIcon.setOnClickListener {
            var searchResultIntent = Intent(this, SearchResultActivity::class.java)
            startActivity(searchResultIntent)
        }

        layoutManagerProduct = LinearLayoutManager (this)
        recyclerViewServiceProductDetails.layoutManager = layoutManagerProduct
        layoutManagerBengkel = LinearLayoutManager (this)
        recyclerViewBengkelDetailsPeview.layoutManager = layoutManagerBengkel

        adapterProduct = RVTransactionServiceProductPreview()
        recyclerViewServiceProductDetails.adapter = adapterProduct
        adapterBengkel = RecyclerViewBengkelDetailsPreview()
        recyclerViewBengkelDetailsPeview.adapter = adapterBengkel
    }
}