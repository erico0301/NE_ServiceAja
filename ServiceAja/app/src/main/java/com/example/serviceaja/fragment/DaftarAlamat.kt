package com.example.serviceaja.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.serviceaja.AlamatActivity
import com.example.serviceaja.R
import com.example.serviceaja.classes.Alamat
import com.example.serviceaja.recyclerview.RVDetailAlamat
import kotlinx.android.synthetic.main.fragment_daftar_alamat.view.*

class DaftarAlamat : Fragment() {
    private var daftarAlamat = arrayListOf<Alamat>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_daftar_alamat, container, false)

        view.daftarAlamat_toolbar.setNavigationOnClickListener {
            activity?.onBackPressed()
        }

        view.daftarAlamat_fab.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.alamat_fragmentContainer, DetailAlamat())
                commit()
            }
        }

        daftarAlamat = (activity!! as AlamatActivity).daftarAlamat

        if (daftarAlamat.size == 0) view.daftarAlamat_rvInstanceAlamat.visibility = View.GONE
        else view.daftarAlamat_daftarKosong.visibility = View.GONE

        view.daftarAlamat_rvInstanceAlamat.adapter = RVDetailAlamat(activity!!, daftarAlamat)
        view.daftarAlamat_rvInstanceAlamat.layoutManager = LinearLayoutManager(context)

        return view
    }
}