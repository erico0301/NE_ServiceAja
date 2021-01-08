package com.example.serviceaja.chatreview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.serviceaja.R
import kotlinx.android.synthetic.main.activity_review.*

class ReviewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)

        backBtn.setOnClickListener {
            onBackPressed()
        }

        ratingIcon.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            ratingTxt.setText(rating.toString())
        }

        reviewBtn.setOnClickListener {
            finish()
        }
    }
}