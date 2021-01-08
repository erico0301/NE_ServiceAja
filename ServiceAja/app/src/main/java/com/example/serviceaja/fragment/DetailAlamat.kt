package com.example.serviceaja.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.DialogFragment
import com.example.serviceaja.*

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

        when (arguments?.getString(ALAMAT_TIPE)) {
            "EDIT" -> {
                rootView.findViewById<EditText>(R.id.detailAlamat_namaAlamat).setText(arguments?.getString(ALAMAT_NAMA_ALAMAT))
                rootView.findViewById<EditText>(R.id.detailAlamat_namaPenerima).setText(arguments?.getString(ALAMAT_NAMA_PENERIMA))
                rootView.findViewById<EditText>(R.id.detailAlamat_noTelp).setText(arguments?.getString(ALAMAT_NO_TELP))
                rootView.findViewById<EditText>(R.id.detailAlamat_detailAlamat).setText(arguments?.getString(ALAMAT_DETAIL_ALAMAT))
                /*rootView.findViewById<Spinner>(R.id.detailAlamat_provinsi).setSelection(arguments?.getInt(ALAMAT_PROVINSI) ?: 0)
                rootView.findViewById<Spinner>(R.id.detailAlamat_kabupatenKota).setSelection(arguments?.getInt(ALAMAT_KABUPATEN_KOTA) ?: 0)
                rootView.findViewById<Spinner>(R.id.detailAlamat_kecamatan).setSelection(arguments?.getInt(ALAMAT_KECAMATAN) ?: 0)*/
            }
        }

        return rootView
    }
}