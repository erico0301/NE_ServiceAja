package com.example.serviceaja.fragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import com.example.serviceaja.DaftardanEditProfilMitra
import com.example.serviceaja.LoginRegister.MainActivity
import com.example.serviceaja.R

class ProfilMitraFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val fragment = inflater.inflate(R.layout.fragment_profil_mitra, container, false)

        fragment.findViewById<Button>(R.id.profilMitra_btnUser).setOnClickListener{
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.fragment_container, ProfilUserFragment())
                commit()
            }
        }

        fragment.findViewById<ImageButton>(R.id.profilMitra_btnEdit).setOnClickListener {
            val intent = Intent(requireActivity(), DaftardanEditProfilMitra::class.java)
            intent.setData(Uri.parse("Edit"))
            startActivity(intent)
        }

        fragment.findViewById<ImageButton>(R.id.btn_logout).setOnClickListener {
            var dialog = AlertDialog.Builder(activity)
                    .setTitle("Keluar")
                    .setMessage("Apakah Anda yakin ingin keluar ?")
                    .setPositiveButton("Ya", DialogInterface.OnClickListener { dialogInterface, i ->
                        startActivity(Intent(activity, MainActivity::class.java))
                        activity?.finishAffinity()
                    })
                    .setNegativeButton("Tidak", DialogInterface.OnClickListener { dialogInterface, i ->

                    })
            dialog.show()
        }

        childFragmentManager.beginTransaction().apply {
            add(R.id.profilMitra_fragmentContainer, DaftarService())
            commit()
        }

        return fragment
    }
}