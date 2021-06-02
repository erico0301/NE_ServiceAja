package com.example.serviceaja.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.serviceaja.R
import com.example.serviceaja.classes.PagerAdapterKendaraan
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_daftar_kendaraan.view.*

class DaftarKendaraan : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_daftar_kendaraan, container, false)

        view.findViewById<androidx.appcompat.widget.Toolbar>(R.id.daftarKendaraan_toolbar).setNavigationOnClickListener {
            activity?.onBackPressed()
        }

        view.daftarKendaraan_btnTambah.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.kendaraan_fragmentContainer, DetailKendaraan())
                commit()
            }
        }

        val viewPager = view.findViewById<ViewPager2>(R.id.daftarKendaraan_viewPager)
        viewPager.adapter = PagerAdapterKendaraan(activity!!)

        TabLayoutMediator(view.findViewById(R.id.daftarKendaraan_tabLayout), viewPager) { tab: TabLayout.Tab, i: Int ->
            when(i) {
                0 -> {
                    tab.setText("Mobil")
                    tab.setIcon(R.drawable.ic_car)
                }
                1 -> {
                    tab.setText("Motor")
                    tab.setIcon(R.drawable.ic_motorcycle)
                }
            }
        }.attach()

        return view
    }
}