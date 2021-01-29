package com.example.serviceaja.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.serviceaja.R
import com.example.serviceaja.ServiceItem
import com.example.serviceaja.recyclerview.RVDaftarProduk

class DaftarProduk : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_daftar_produk, container, false)

        var daftarProduk = arrayListOf(
            ServiceItem(R.drawable.filter_udara, "Filter Udara Xpander", 250000, "Mitsubishi", "Medan"),
            ServiceItem(R.drawable.kaca_spion, "Kaca Spion Xpander", 150000, "Mitsubishi", "Medan"),
            ServiceItem(R.drawable.cover_foglamp, "Cover Foglamp Xpander", 150000, "Mitsubishi", "Medan"),
            ServiceItem(R.drawable.lampu_sen, "Lampu Sen Xpander", 450000, "Mitsubishi", "Medan")
        )

        val rv = view.findViewById<RecyclerView>(R.id.daftarProduk_rv)
        rv.adapter = RVDaftarProduk(daftarProduk)
        rv.layoutManager = GridLayoutManager(context, 2)

        return view
    }
}