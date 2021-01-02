package com.example.serviceaja.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.serviceaja.R

private const val NUM_PAGES = 2

class ProfilMitraFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val fragment = inflater.inflate(R.layout.fragment_profil_mitra, container, false)

        val viewPager = fragment.findViewById<ViewPager2>(R.id.profilMitra_viewPager)
        val pagerAdapter = PagerAdapterConstructor(this)
        viewPager.adapter = pagerAdapter

        return fragment
    }

    private inner class PagerAdapterConstructor(fa: Fragment): FragmentStateAdapter(fa) {
        override fun getItemCount(): Int = NUM_PAGES

        override fun createFragment(position: Int): Fragment = DaftarService()

    }

}