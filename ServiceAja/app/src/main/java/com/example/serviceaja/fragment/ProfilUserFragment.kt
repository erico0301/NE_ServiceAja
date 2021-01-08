package com.example.serviceaja.fragment

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils.replace
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import com.example.serviceaja.DaftarAlamat
import com.example.serviceaja.DaftarKendaraan
import com.example.serviceaja.EditProfilUser
import com.example.serviceaja.R
import kotlinx.android.synthetic.main.fragment_profil_user.*

class ProfilUserFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profil_user, container, false)

        view.findViewById<Button>(R.id.profilUser_btnMitra).setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.fragment_container, ProfilMitraFragment())
                commit()
            }
        }

        view.findViewById<ImageButton>(R.id.profilUser_btnEdit).setOnClickListener {
            startActivity(Intent(activity, EditProfilUser::class.java))
        }

        view.findViewById<ImageButton>(R.id.profilUser_btnEditLocation).setOnClickListener {
            startActivity(Intent(activity, DaftarAlamat::class.java))
        }

        view.findViewById<LinearLayout>(R.id.profilUser_btnKendaraanLain).setOnClickListener {
            startActivity(Intent(activity, DaftarKendaraan::class.java))
        }

        return view
    }

}