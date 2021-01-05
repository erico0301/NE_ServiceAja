package com.example.serviceaja.fragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.serviceaja.LoginRegister.MainActivity
import com.example.serviceaja.R
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {

    private var userProfileFragment = UserProfileFragment()
    private var mitraProfileFragment = MitraProfileFragment()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        logoutIcon.setOnClickListener {
            var dialog = AlertDialog.Builder(activity)
                .setTitle("Konfirmasi Keluar")
                .setMessage("Apakah Anda yakin ingin keluar ?")
                .setPositiveButton("Ya", DialogInterface.OnClickListener { dialog, which ->
                    var mainIntent = Intent(activity, MainActivity::class.java)
                    startActivity(mainIntent)
                    activity?.finishAffinity()
                })
                .setNegativeButton("Tidak", DialogInterface.OnClickListener { dialog, which ->
                    dialog.cancel()
                })
            dialog.show()
        }

        replaceFragment(userProfileFragment)
        profileMenu.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId) {
                R.id.user -> {
                    user.setTextColor(Color.WHITE)
                    mitra.setTextColor(Color.BLACK)
                    replaceFragment(userProfileFragment)
                }
                R.id.mitra  -> {
                    mitra.setTextColor(Color.WHITE)
                    user.setTextColor(Color.BLACK)
                    replaceFragment(mitraProfileFragment)
                }
            }
        }
    }

    private fun replaceFragment(fragment : Fragment) =
        childFragmentManager.beginTransaction().apply {
            replace(R.id.userMitra_fragment, fragment)
            commit()
        }

    companion object {

    }
}