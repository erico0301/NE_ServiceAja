package com.example.serviceaja.search

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.serviceaja.*
import com.example.serviceaja.recyclerview.RecyclerViewReviewDetails
import kotlinx.android.synthetic.main.activity_product_service_detail.*
import kotlinx.android.synthetic.main.dialog_service_booking.view.*
import java.time.LocalDate
import java.util.*

class ProductServiceDetailActivity : AppCompatActivity() {

    private var layoutManager : RecyclerView.LayoutManager? = null
    private var adapter : RecyclerView.Adapter<RecyclerViewReviewDetails.ViewHolder>? = null
    var testerIntentService : Intent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_service_detail)

        detailProduk_toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        layoutManager = LinearLayoutManager (this)
        recyclerViewReviewDetails.layoutManager = layoutManager
        adapter = RecyclerViewReviewDetails()
        recyclerViewReviewDetails.adapter = adapter

        testSoundBtn.setOnClickListener {
            if(testSoundBtn.text.toString().toUpperCase().equals("TEST")) {
                testSoundBtn.text = "STOP"
                testerIntentService?.setAction(ACTION_PLAY)
                startService(testerIntentService)
            }
            else {
                testSoundBtn.text = "TEST"
                testerIntentService?.setAction(ACTION_STOP)
                startService(testerIntentService)
            }
        }

        productImg.setOnClickListener {
            var productImageIntent = Intent(this, ProductImageActivity::class.java)
            startActivity(productImageIntent)
        }
    }

    override fun onStart() {
        super.onStart()
        if(testerIntentService==null) {
            testerIntentService = Intent(this, TestSoundService::class.java)
            testerIntentService?.setAction(ACTION_CREATE)
            startService(testerIntentService)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopService(testerIntentService)
    }
}