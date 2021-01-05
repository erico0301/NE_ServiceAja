package com.example.serviceaja.checkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.serviceaja.R
import com.example.serviceaja.recyclerview.RecyclerViewCheckoutProductServiceDetails
import kotlinx.android.synthetic.main.activity_checkout.*
import kotlinx.android.synthetic.main.activity_checkout.backBtn

class CheckoutActivity : AppCompatActivity() {

    private var layoutManager : RecyclerView.LayoutManager? = null
    private var adapter : RecyclerView.Adapter<RecyclerViewCheckoutProductServiceDetails.ViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        backBtn.setOnClickListener {
            onBackPressed()
        }

        layoutManager = LinearLayoutManager (this)
        recyclerViewServiceProductDetails.layoutManager = layoutManager
        adapter = RecyclerViewCheckoutProductServiceDetails()
        recyclerViewServiceProductDetails.adapter = adapter

        val adapter = ArrayAdapter.createFromResource(this, R.array.lokasispinner,
            android.R.layout.simple_spinner_dropdown_item)
        lokasiSpinner.adapter = adapter

        paymentBtn.setOnClickListener {
            var confirmPaymentIntent = Intent(this, ConfirmPaymentActivity::class.java)
            startActivity(confirmPaymentIntent)
        }
    }
}