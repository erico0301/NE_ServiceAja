package com.example.serviceaja.classes

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.serviceaja.fragment.DaftarMobil
import com.example.serviceaja.fragment.DaftarMotor

class PagerAdapterKendaraan(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {
    private val kendaraan = listOf(DaftarMobil(), DaftarMotor())
    override fun getItemCount(): Int {
        return kendaraan.size
    }

    override fun createFragment(position: Int): Fragment {
        return kendaraan[position]
    }
}