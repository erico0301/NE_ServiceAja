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
import com.example.serviceaja.*
import com.example.serviceaja.classes.User
import kotlinx.android.synthetic.main.fragment_profil_user.view.*

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

        val user = arguments?.getParcelable<User>(EXTRA_USER)

        view.profilUser_namaUser.text = user?.nama
        view.profilUser_emailUser.text = user?.email
        view.profilUser_noTelpUser.text = "(+62)-${user?.noTelp!!.substring(1)}"

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
            startActivity(Intent(activity, AlamatActivity::class.java))
        }

        view.profilUser_btnKendaraanLain.setOnClickListener {
            startActivity(Intent(activity, KendaraanActivity::class.java))
        }

        return view
    }

}