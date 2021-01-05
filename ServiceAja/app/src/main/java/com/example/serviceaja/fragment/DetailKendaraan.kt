package com.example.serviceaja.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.DialogFragment
import com.example.serviceaja.R

class DetailKendaraan : DialogFragment() {

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        if (dialog != null) {
            dialog.getWindow()?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppTheme_FullScreenDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_detail_kendaraan, container, false)

        val toolbar = rootView.findViewById(R.id.detailKendaraan_toolbar) as androidx.appcompat.widget.Toolbar
        toolbar.apply {
            setNavigationOnClickListener { dismiss() }
            setTitle("Tambah Alamat")
        }

        val adapterMerk = ArrayAdapter.createFromResource(requireActivity(), R.array.merkMobil, android.R.layout.simple_spinner_dropdown_item)
        val adapterNama = ArrayAdapter.createFromResource(requireActivity(), R.array.namaMobil, android.R.layout.simple_spinner_dropdown_item)
        val adapterBahanBakar = ArrayAdapter.createFromResource(requireActivity(), R.array.bahanBakar, android.R.layout.simple_spinner_dropdown_item)

        rootView.findViewById<Spinner>(R.id.detailKendaraan_merk).adapter = adapterMerk
        rootView.findViewById<Spinner>(R.id.detailKendaraan_nama).adapter = adapterNama
        rootView.findViewById<Spinner>(R.id.detailKendaraan_bahanBakar).adapter = adapterBahanBakar

        return rootView
    }
}