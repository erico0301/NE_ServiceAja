package com.example.serviceaja.classes

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.serviceaja.fragment.DaftarProduk
import com.example.serviceaja.fragment.DaftarService

class PagerAdapterServiceProduct(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {
    private val tab = listOf(DaftarService(), DaftarProduk())
    override fun getItemCount(): Int {
        return tab.size
    }

    override fun createFragment(position: Int): Fragment {
        return tab[position]
    }
}