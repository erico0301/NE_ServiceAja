package com.example.serviceaja.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.serviceaja.R
import kotlinx.android.synthetic.main.activity_product_service_detail.*
import java.time.LocalDate
import java.util.*

class ProductServiceDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_service_detail)

        detailProduk_toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        detailProduk_btnPesan.setOnClickListener {
            val inflater = layoutInflater.inflate(R.layout.dialog_booking_service, null, false)


            val dialog = AlertDialog.Builder(this)
                    .setView(inflater)
        }
    }
}