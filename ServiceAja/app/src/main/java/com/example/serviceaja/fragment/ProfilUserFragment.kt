package com.example.serviceaja.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import com.example.serviceaja.DaftarAlamat
import com.example.serviceaja.EditProfilUser
import com.example.serviceaja.R

class ProfilUserFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val fragment = inflater.inflate(R.layout.fragment_profil_user, container, false)

        fragment.findViewById<Button>(R.id.profilUser_btnMitra).setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ProfilMitraFragment())
                .commit()
        }

        fragment.findViewById<ImageButton>(R.id.profilUser_btnEdit).setOnClickListener {
            startActivity(Intent(requireActivity(), EditProfilUser::class.java))
        }

        fragment.findViewById<ImageButton>(R.id.profilUser_btnEditLocation).setOnClickListener {
            startActivity(Intent(requireActivity(), DaftarAlamat::class.java))
        }

        fragment.findViewById<LinearLayout>(R.id.profilUser_btnKendaraanLain).setOnClickListener {
            startActivity(Intent(requireActivity(), DaftarAlamat::class.java))
        }

        return fragment
    }

}