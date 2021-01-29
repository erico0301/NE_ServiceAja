package com.example.serviceaja.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.size
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.serviceaja.KendaraanActivity
import com.example.serviceaja.R
import com.example.serviceaja.classes.Kendaraan
import com.example.serviceaja.recyclerview.RVDetailKendaraan
import kotlinx.android.synthetic.main.fragment_daftar_mobil.*
import kotlinx.android.synthetic.main.fragment_daftar_motor.*
import kotlinx.android.synthetic.main.fragment_daftar_motor.view.*

class DaftarMotor : Fragment() {
    private var daftarMotor = arrayListOf<Kendaraan>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_daftar_motor, container, false)

        daftarMotor = (activity!! as KendaraanActivity).daftarMotor

        if (daftarMotor.size == 0)
            view.daftarMotor_rvMotor.visibility = View.GONE
        else view.daftarMotor_daftarKosong.visibility = View.GONE

        view.daftarMotor_rvMotor.adapter = RVDetailKendaraan(activity!!, daftarMotor)
        view.daftarMotor_rvMotor.layoutManager = LinearLayoutManager(context)

        return view
    }
}