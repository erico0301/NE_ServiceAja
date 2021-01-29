package com.example.serviceaja

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import androidx.viewpager2.widget.ViewPager2
import com.example.serviceaja.classes.PagerAdapterKendaraan
import com.example.serviceaja.classes.PagerAdapterServiceProduct
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ProfilMitra : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profil_mitra)

        findViewById<ImageButton>(R.id.profilMitra_btnBack).setOnClickListener {
            onBackPressed()
        }

        val viewPager = findViewById<ViewPager2>(R.id.profilMitra_viewPager)
        viewPager.adapter = PagerAdapterServiceProduct(this)

        TabLayoutMediator(findViewById(R.id.profilMitra_tabServiceProduk), viewPager) { tab: TabLayout.Tab, i: Int ->
            when(i) {
                0 -> {
                    tab.setText("Service")
                }
                1 -> {
                    tab.setText("Produk")
                }
            }
        }.attach()
    }
}