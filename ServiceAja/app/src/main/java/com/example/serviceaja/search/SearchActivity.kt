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
import com.example.serviceaja.recyclerview.RecyclerViewServiceDetailsSearchResult
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity() {
    var p1: MyProgressTask? = null
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

            p1 = MyProgressTask(this)
            p1!!.execute()
        }

        recyclerViewServiceProductDetails.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewBengkelDetailsPeview.layoutManager = LinearLayoutManager (this)

        recyclerViewServiceProductDetails.adapter = RecyclerViewServiceDetailsSearchResult()
        recyclerViewBengkelDetailsPeview.adapter = RecyclerViewBengkelDetailsPreview()
    }
}