package com.example.serviceaja.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.serviceaja.DaftardanEditProfilMitra
import com.example.serviceaja.EditProfilUser
import com.example.serviceaja.R
import kotlinx.android.synthetic.main.fragment_profil_mitra.view.*

class ProfilMitraFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val fragment = inflater.inflate(R.layout.fragment_profil_mitra, container, false)

        fragment.findViewById<Button>(R.id.profilMitra_btnUser).setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ProfilUserFragment())
                .commit()
        }

        fragment.findViewById<ImageButton>(R.id.profilMitra_btnEdit).setOnClickListener {
            val intent = Intent(requireActivity(), DaftardanEditProfilMitra::class.java)
            intent.setData(Uri.parse("Edit"))
            startActivity(intent)
        }

        childFragmentManager.beginTransaction().apply {
            add(R.id.profilMitra_fragmentContainer, DaftarService())
            commit()
        }

        return fragment
    }

}