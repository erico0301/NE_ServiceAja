package com.example.serviceaja.fragment

import android.content.Intent
import android.database.sqlite.SQLiteException
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.serviceaja.AlamatActivity
import com.example.serviceaja.EXTRA_USER
import com.example.serviceaja.EXTRA_USER_RETURN
import com.example.serviceaja.R
import com.example.serviceaja.classes.Alamat
import com.example.serviceaja.classes.DBHelper
import com.example.serviceaja.classes.User
import com.example.serviceaja.recyclerview.RVDetailAlamat
import kotlinx.android.synthetic.main.fragment_daftar_alamat.*
import kotlinx.android.synthetic.main.fragment_daftar_alamat.view.*

class DaftarAlamat : Fragment() {
    private var daftarAlamat = arrayListOf<Alamat>()
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        user = (activity as AlamatActivity).user
        val db = DBHelper(context!!)
        db.beginTransaction
        try {
            user.alamat = db.getAllAlamat(user.noTelp)
            db.successTransaction
        } catch (e: SQLiteException) {
            Log.e("Error while executing query in Database", e.toString())
        } finally {
            db.endTransaction
        }

        activity?.title = "Daftar Alamat"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_daftar_alamat, container, false)

        view.daftarAlamat_fab.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                val detailAlamat = DetailAlamat()
                val bundle = Bundle()
                bundle.putParcelable(EXTRA_USER, user)
                detailAlamat.arguments = bundle
                replace(R.id.alamat_fragmentContainer, DetailAlamat())
                commit()
            }
        }

        view.daftarAlamat_toolbar.setOnMenuItemClickListener {
            Log.e("Finish Activity", "Dijalankan!")
            val intent = Intent()
            intent.putExtra(EXTRA_USER_RETURN, user)
            activity?.setResult(102, intent)
            activity?.finish()

            true
        }

        daftarAlamat = user.alamat

        if (daftarAlamat.size == 0) view.daftarAlamat_rvInstanceAlamat.visibility = View.GONE
        else view.daftarAlamat_daftarKosong.visibility = View.GONE

        view.daftarAlamat_rvInstanceAlamat.adapter = RVDetailAlamat(activity!!, daftarAlamat)
        view.daftarAlamat_rvInstanceAlamat.layoutManager = LinearLayoutManager(context)

        return view
    }
}