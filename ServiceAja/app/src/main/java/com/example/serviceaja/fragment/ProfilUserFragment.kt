package com.example.serviceaja.fragment

import android.app.Activity
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.example.serviceaja.classes.AccountSharedPref
import com.example.serviceaja.classes.User
import kotlinx.android.synthetic.main.fragment_profil_user.*
import kotlinx.android.synthetic.main.fragment_profil_user.view.*

class ProfilUserFragment : Fragment() {
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        user = arguments?.getParcelable<User>(EXTRA_USER) ?: User("admin", "admin@gmail" ,"01812135484", "asdfghjkl")
        Log.e("profilUserFragment", "${user.nama}, ${user.email}, ${user.noTelp}")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profil_user, container, false)
        Log.e("profilUserFragment onCreateVIew", "${user.nama}, ${user.email}, ${user.noTelp}")
        view.profilUser_namaUser.text = user.nama
        view.profilUser_emailUser.text = user.email
        view.profilUser_noTelpUser.text = "(+62)-${user.noTelp!!.substring(1)}"

        view.findViewById<Button>(R.id.profilUser_btnMitra).setOnClickListener {
            val profilMitraFragment = ProfilMitraFragment()
            val bundle = Bundle()
            bundle.putParcelable(EXTRA_USER, user)
            profilMitraFragment.arguments = bundle
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.fragment_container, profilMitraFragment)
                commit()
            }
        }

        /*View ImageButton berikut ditambahkan event OnClickListener sehingga pada saat diklik,
        akan membuka Activity EditProfilUser.kt.
        Langkah ini merupakan demonstrasi dari penggunaan Intent Eksplisit
         */
        view.findViewById<ImageButton>(R.id.profilUser_btnEdit).setOnClickListener {
            val intent = Intent(activity, EditProfilUser::class.java)

            intent.putExtra(EXTRA_USER, user)
            activity?.startActivityForResult(intent, REQ_CODE_EDIT_PROFILE)
            activity?.supportFragmentManager?.beginTransaction()
                    ?.remove(this)?.commit()
        }

        // Event ini digunakan untuk membuka Intent Eksplisit AlamatActivity.kt
        view.findViewById<ImageButton>(R.id.profilUser_btnEditLocation).setOnClickListener {
            val intent = Intent(activity, AlamatActivity::class.java)
            intent.putExtra(EXTRA_USER, user)
            activity?.startActivityForResult(intent, REQ_CODE_EDIT_LOCATION)
        }

        // Event ini digunakan untuk membuka Intent Eksplisit KendaraanActivity.kt
        view.profilUser_btnKendaraanLain.setOnClickListener {
            val intent = Intent(activity, KendaraanActivity::class.java)
            intent.putExtra(EXTRA_USER, user)
            startActivity(intent)
        }

        view.profilUser_btnSettings.setOnClickListener {
            startActivity(Intent(activity, SettingsActivity::class.java))
        }

        view.findViewById<ImageButton>(R.id.btn_logout).setOnClickListener {
            startActivity(Intent(activity, MainActivity::class.java))
            AccountSharedPref(activity!!).clearValues()
            updateWidget()
            activity?.finishAffinity()
        }

        return view
    }

    private fun updateWidget() {
        val appWidgetManager = AppWidgetManager.getInstance(context!!)
        val ids = appWidgetManager.getAppWidgetIds(ComponentName(context!!, InfoKendaraanWidget::class.java))
        val intent = Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE)
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
        context!!.sendBroadcast(intent)
    }
}