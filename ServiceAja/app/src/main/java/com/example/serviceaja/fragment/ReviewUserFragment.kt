package com.example.serviceaja.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.serviceaja.R
import com.example.serviceaja.recyclerview.RecyclerViewReviewedDetails
import com.example.serviceaja.recyclerview.RecyclerViewUnreviewedDetails
import kotlinx.android.synthetic.main.fragment_review_user.*

class ReviewUserFragment : Fragment() {

    private var layoutManagerUnreviewed : RecyclerView.LayoutManager? = null
    private var adapterUnreviewed : RecyclerView.Adapter<RecyclerViewUnreviewedDetails.ViewHolder>? = null
    private var layoutManagerReviewed : RecyclerView.LayoutManager? = null
    private var adapterReviewed : RecyclerView.Adapter<RecyclerViewReviewedDetails.ViewHolder>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_review_user, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        layoutManagerUnreviewed = LinearLayoutManager (activity)
        recyclerViewUnreviewedDetails.layoutManager = layoutManagerUnreviewed
        adapterUnreviewed = RecyclerViewUnreviewedDetails()
        recyclerViewUnreviewedDetails.adapter = adapterUnreviewed

        layoutManagerReviewed = LinearLayoutManager (activity)
        recyclerViewReviewedDetails.layoutManager = layoutManagerReviewed
        adapterReviewed = RecyclerViewReviewedDetails()
        recyclerViewReviewedDetails.adapter = adapterReviewed

    }

    companion object {

    }
}