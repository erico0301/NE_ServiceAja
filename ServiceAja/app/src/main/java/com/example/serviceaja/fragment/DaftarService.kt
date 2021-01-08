package com.example.serviceaja.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.serviceaja.ItemAdapter
import com.example.serviceaja.R
import com.example.serviceaja.ServiceItem
import kotlinx.android.synthetic.main.fragment_daftar_service.*
import kotlinx.android.synthetic.main.fragment_profil_mitra.*

class DaftarService : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_daftar_service, container, false)

        val items = mutableListOf<ServiceItem>(
                ServiceItem(R.drawable.car_wash, "Cuci Mobil Segala Jenis", 50000, getString(R.string.contohID)),
                ServiceItem(R.drawable.cat_mobil, "Cat Mobil Segala Jenis", 120000, getString(R.string.contohID)),
                ServiceItem(R.drawable.cek_ban_mobil, "Cek Kesehatan Ban Mobil", 20000, getString(R.string.contohID)),
                ServiceItem(R.drawable.dempul_mobil, "Dempul Mobil", 100000, getString(R.string.contohID))
        )

        val adapter = ItemAdapter(items)
        view.findViewById<RecyclerView>(R.id.daftarService_recyclerView).apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(context)
        }

        return view
    }

}