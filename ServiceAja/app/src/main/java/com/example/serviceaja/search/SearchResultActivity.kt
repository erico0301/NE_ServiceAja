package com.example.serviceaja.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.serviceaja.R
import com.example.serviceaja.recyclerview.RVBengkelPreview
import com.example.serviceaja.recyclerview.RecyclerViewProductDetailsSearchResult
import com.example.serviceaja.recyclerview.RecyclerViewServiceDetailsSearchResult
import kotlinx.android.synthetic.main.activity_search_result.*
import kotlinx.android.synthetic.main.activity_search_result.backBtn

class SearchResultActivity : AppCompatActivity() {

    private var layoutManagerService : RecyclerView.LayoutManager? = null
    private var adapterService : RecyclerView.Adapter<RecyclerViewServiceDetailsSearchResult.ViewHolder>? = null
    private var layoutManagerProduct : RecyclerView.LayoutManager? = null
    private var adapterProduct : RecyclerView.Adapter<RecyclerViewProductDetailsSearchResult.ViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)

        backBtn.setOnClickListener {
            onBackPressed()
        }

        hasilSearch_RVBengkel.adapter = RVBengkelPreview()
        hasilSearch_RVBengkel.layoutManager = LinearLayoutManager(this)

        layoutManagerService = GridLayoutManager (this, 1, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewServiceDetailsSR.layoutManager = layoutManagerService
        adapterService = RecyclerViewServiceDetailsSearchResult()
        recyclerViewServiceDetailsSR.adapter = adapterService

        layoutManagerProduct = GridLayoutManager (this, 1, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewProductDetailsSR.layoutManager = layoutManagerProduct
        adapterProduct = RecyclerViewProductDetailsSearchResult()
        recyclerViewProductDetailsSR.adapter = adapterProduct
    }
}