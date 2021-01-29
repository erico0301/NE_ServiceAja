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
import androidx.fragment.app.Fragment
import com.example.serviceaja.*
import com.example.serviceaja.classes.Alamat
import kotlinx.android.synthetic.main.fragment_detail_alamat.view.*

class DetailAlamat : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val rootView = inflater.inflate(R.layout.fragment_detail_alamat, container, false)

        val toolbar = rootView.findViewById(R.id.detailAlamat_toolbar) as androidx.appcompat.widget.Toolbar
        toolbar.apply {
            setNavigationOnClickListener {
                activity?.supportFragmentManager?.beginTransaction()?.apply {
                    replace(R.id.alamat_fragmentContainer, DaftarAlamat())
                    commit()
                }
            }
            setTitle("Tambah Alamat")
        }

        val adapterProvinsi = ArrayAdapter.createFromResource(requireActivity(), R.array.provinsi, android.R.layout.simple_dropdown_item_1line)
        val adapterKabKota = ArrayAdapter.createFromResource(requireActivity(), R.array.kabupaten_kota, android.R.layout.simple_dropdown_item_1line)
        val adapterKecamatan = ArrayAdapter.createFromResource(requireActivity(), R.array.kecamatan, android.R.layout.simple_dropdown_item_1line)

        var kecamatan = ""
        var kabupatenKota = ""
        var provinsi = ""

        rootView.detailAlamat_provinsi.setAdapter(adapterProvinsi)
        rootView.detailAlamat_provinsi.setOnItemClickListener { parent, view, position, id ->
            provinsi = parent.getItemAtPosition(position).toString()
        }

        rootView.detailAlamat_kabupatenKota.setAdapter(adapterKabKota)
        rootView.detailAlamat_kabupatenKota.setOnItemClickListener { parent, view, position, id ->
            kabupatenKota = parent.getItemAtPosition(position).toString()
        }

        rootView.detailAlamat_kecamatan.setAdapter(adapterKecamatan)
        rootView.detailAlamat_kecamatan.setOnItemClickListener { parent, view, position, id ->
            kecamatan = parent.getItemAtPosition(position).toString()
        }

        if (arguments != null) {
            rootView.apply {
                detailAlamat_btnExecute.text = "Edit"
                detailAlamat_namaAlamat.setText(arguments?.getString(ALAMAT_NAMA_ALAMAT))
                detailAlamat_namaPenerima.setText(arguments?.getString(ALAMAT_NAMA_PENERIMA))
                detailAlamat_noTelp.setText(arguments?.getString(ALAMAT_NO_TELP))
                detailAlamat_detailAlamat.setText(arguments?.getString(ALAMAT_DETAIL_ALAMAT))
                detailAlamat_provinsi.setText(arguments?.getString(ALAMAT_PROVINSI))
                detailAlamat_kabupatenKota.setText(arguments?.getString(ALAMAT_KABUPATEN_KOTA))
                detailAlamat_kecamatan.setText(arguments?.getString(ALAMAT_KECAMATAN))
            }
        }

        rootView.findViewById<Button>(R.id.detailAlamat_btnExecute).setOnClickListener {
            val namaAlamat = rootView.detailAlamat_namaAlamat.text.toString()
            val namaPenerima = rootView.detailAlamat_namaPenerima.text.toString()
            val noTelepon = rootView.detailAlamat_noTelp.text.toString()
            val detailAlamat = rootView.detailAlamat_detailAlamat.text.toString()

            kecamatan = rootView.detailAlamat_kecamatan.text.toString()
            kabupatenKota = rootView.detailAlamat_kabupatenKota.text.toString()
            provinsi = rootView.detailAlamat_provinsi.text.toString()

            val alamat = Alamat(namaAlamat, namaPenerima, noTelepon, kecamatan, kabupatenKota, provinsi, detailAlamat)

            if (rootView.detailAlamat_btnExecute.text == "Tambah") (activity as AlamatActivity).daftarAlamat.add(alamat)
            else (activity as AlamatActivity).daftarAlamat[arguments?.getInt(ALAMAT_POSITION)!!] = alamat

            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.alamat_fragmentContainer, DaftarAlamat())
                commit()
            }
        }

        return rootView
    }
}