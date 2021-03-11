package com.example.serviceaja.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.serviceaja.*
import com.example.serviceaja.LoginRegister.MainActivity
import com.example.serviceaja.classes.User
import kotlinx.android.synthetic.main.fragment_profil_user.*
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
            val intent = Intent(activity, EditProfilUser::class.java)
            intent.putExtra(EXTRA_USER, user)
            startActivityForResult(intent, 101)
        }

        view.findViewById<ImageButton>(R.id.profilUser_btnEditLocation).setOnClickListener {
            startActivity(Intent(activity, AlamatActivity::class.java))
        }

        view.profilUser_btnKendaraanLain.setOnClickListener {
            startActivity(Intent(activity, KendaraanActivity::class.java))
        }

        view.findViewById<ImageButton>(R.id.btn_logout).setOnClickListener {
            startActivity(Intent(activity, MainActivity::class.java))
            activity?.finishAffinity()
        }

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 101) {
            if (resultCode == AppCompatActivity.RESULT_OK && data != null) {
                (activity as HomeActivity).apply {
                    user = data.getParcelableExtra<User>(EXTRA_USER)!!
                    activateFragment("Profile")
                }
            }
        }
    }
}