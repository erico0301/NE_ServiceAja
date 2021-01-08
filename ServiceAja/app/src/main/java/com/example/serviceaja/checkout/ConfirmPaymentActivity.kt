package com.example.serviceaja.checkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.serviceaja.HomeActivity
import com.example.serviceaja.R
import com.example.serviceaja.recyclerview.RecyclerViewCheckoutProductServiceDetails
import kotlinx.android.synthetic.main.activity_checkout.recyclerViewServiceProductDetails
import kotlinx.android.synthetic.main.activity_confirm_payment.*

class ConfirmPaymentActivity : AppCompatActivity() {

    private var layoutManager : RecyclerView.LayoutManager? = null
    private var adapter : RecyclerView.Adapter<RecyclerViewCheckoutProductServiceDetails.ViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_payment)

        backBtn.setOnClickListener {
            onBackPressed()
        }

        layoutManager = LinearLayoutManager (this)
        recyclerViewServiceProductDetails.layoutManager = layoutManager
        adapter = RecyclerViewCheckoutProductServiceDetails()
        recyclerViewServiceProductDetails.adapter = adapter

        val adapter = ArrayAdapter.createFromResource(this, R.array.metodePembayaran,
            android.R.layout.simple_spinner_dropdown_item)
        paymentMethodSpinner.adapter = adapter

        payBtn.setOnClickListener {
            var homeIntent = Intent(this, HomeActivity::class.java)
            startActivity(homeIntent )
            finishAffinity()
        }
    }
}