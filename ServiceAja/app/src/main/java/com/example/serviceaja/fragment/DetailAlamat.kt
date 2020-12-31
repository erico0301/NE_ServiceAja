package com.example.serviceaja.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.DialogFragment
import com.example.serviceaja.R

class DetailAlamat : DialogFragment() {

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
    ): View {
        val rootView = inflater.inflate(R.layout.fragment_detail_alamat, container, false)

        val toolbar = rootView.findViewById(R.id.toolbar) as androidx.appcompat.widget.Toolbar
        toolbar.apply {
            setNavigationOnClickListener { dismiss() }
            setTitle("Tambah Alamat")
        }

        val adapterProvinsi = ArrayAdapter.createFromResource(requireContext(), R.array.provinsi, android.R.layout.simple_spinner_dropdown_item)
        val adapterKabKota = ArrayAdapter.createFromResource(requireContext(), R.array.kabupaten_kota, android.R.layout.simple_spinner_dropdown_item)
        val adapterKecamatan = ArrayAdapter.createFromResource(requireContext(), R.array.kecamatan, android.R.layout.simple_spinner_dropdown_item)
        rootView.findViewById<Spinner>(R.id.detailAlamat_provinsi)?.adapter = adapterProvinsi
        rootView.findViewById<Spinner>(R.id.detailAlamat_kabupatenKota)?.adapter = adapterKabKota
        rootView.findViewById<Spinner>(R.id.detailAlamat_kecamatan)?.adapter = adapterKecamatan

        rootView.findViewById<Button>(R.id.detailAlamat_btnBatal).setOnClickListener {
            dismiss()
        }

        return rootView
    }
}