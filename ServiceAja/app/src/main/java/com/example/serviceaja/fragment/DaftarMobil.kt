package com.example.serviceaja.fragment

import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.size
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.serviceaja.KendaraanActivity
import com.example.serviceaja.MOBIL_ARRAY
import com.example.serviceaja.MOTOR_ARRAY
import com.example.serviceaja.R
import com.example.serviceaja.classes.Kendaraan
import com.example.serviceaja.recyclerview.RVDetailKendaraan
import kotlinx.android.synthetic.main.fragment_daftar_mobil.*
import kotlinx.android.synthetic.main.fragment_daftar_mobil.view.*

class DaftarMobil : Fragment() {
    private var daftarMobil = arrayListOf<Kendaraan>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_daftar_mobil, container, false)

        daftarMobil = (activity!! as KendaraanActivity).daftarMobil

        if (daftarMobil.size == 0)
            view.daftarMobil_rvMobil.visibility = View.GONE
        else view.daftarMobil_daftarKosong.visibility = View.GONE

        view.daftarMobil_rvMobil.adapter = RVDetailKendaraan(activity!!, daftarMobil)
        view.daftarMobil_rvMobil.layoutManager = LinearLayoutManager(context)

        return view
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }


}