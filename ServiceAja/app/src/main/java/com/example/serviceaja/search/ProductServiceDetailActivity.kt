package com.example.serviceaja.search

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
<<<<<<< HEAD
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.serviceaja.*
import com.example.serviceaja.recyclerview.RecyclerViewReviewDetails
=======
import com.example.serviceaja.R
>>>>>>> 05b7be2e5f1cdf3f336448daf87306ca51c02c19
import kotlinx.android.synthetic.main.activity_product_service_detail.*
import java.time.LocalDate
import java.util.*

class ProductServiceDetailActivity : AppCompatActivity() {

<<<<<<< HEAD
    private var layoutManager : RecyclerView.LayoutManager? = null
    private var adapter : RecyclerView.Adapter<RecyclerViewReviewDetails.ViewHolder>? = null
    var testerIntentService : Intent? = null

=======
>>>>>>> 05b7be2e5f1cdf3f336448daf87306ca51c02c19
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_service_detail)

        detailProduk_toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

<<<<<<< HEAD
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
=======
        detailProduk_btnPesan.setOnClickListener {
            val inflater = layoutInflater.inflate(R.layout.dialog_booking_service, null, false)


            val dialog = AlertDialog.Builder(this)
                    .setView(inflater)
        }
>>>>>>> 05b7be2e5f1cdf3f336448daf87306ca51c02c19
    }
}