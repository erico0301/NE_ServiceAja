package com.example.serviceaja.fragment

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.*
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.Fragment
import com.example.serviceaja.*
import com.example.serviceaja.classes.Alamat
import com.example.serviceaja.classes.GetDaftarKabupatenKota
import com.example.serviceaja.classes.GetDaftarKecamatan
import com.example.serviceaja.classes.GetDaftarProvinsi
import kotlinx.android.synthetic.main.fragment_detail_alamat.*
import kotlinx.android.synthetic.main.fragment_detail_alamat.view.*

class DetailAlamat : Fragment() {
    private lateinit var jobscheduler: JobScheduler

    lateinit var daftarProvinsi: HashMap<String, String>
    lateinit var daftarKabupatenKota: HashMap<String, String>
    lateinit var daftarKecamatan: ArrayList<String>

    lateinit var spinnerProvinsi: AutoCompleteTextView
    lateinit var spinnerKabKota: AutoCompleteTextView
    lateinit var spinnerKecamatan: AutoCompleteTextView

    val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action?.equals("JOBSERVICE_PROVINSI") == true) {
                daftarProvinsi = intent.extras?.getSerializable(DAFTAR_PROVINSI) as HashMap<String, String>
                val namaProvinsi = arrayListOf<String>()
                for ((key, value) in daftarProvinsi)
                    namaProvinsi.add(value)
                val adapterProvinsi = ArrayAdapter(context!!, android.R.layout.simple_spinner_dropdown_item, namaProvinsi)
                adapterProvinsi.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
                spinnerProvinsi.setAdapter(adapterProvinsi)
            }
            else if (intent?.action?.equals("JOBSERVICE_KAB_KOTA") == true) {
                daftarKabupatenKota = intent.extras?.getSerializable(DAFTAR_KAB_KOTA) as HashMap<String, String>
                val namaKabKota = arrayListOf<String>()
                for ((key, value) in daftarKabupatenKota)
                    namaKabKota.add(value)
                val adapterKabKota = ArrayAdapter(context!!, android.R.layout.simple_spinner_dropdown_item, namaKabKota)
                adapterKabKota.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
                spinnerKabKota.setAdapter(adapterKabKota)
            }
            else if (intent?.action?.equals("JOBSERVICE_KECAMATAN") == true) {
                daftarKecamatan = intent.extras?.getSerializable(DAFTAR_KECAMATAN) as ArrayList<String>
                val adapterKecamatan = ArrayAdapter(context!!, android.R.layout.simple_spinner_dropdown_item, daftarKecamatan)
                adapterKecamatan.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
                spinnerKecamatan.setAdapter(adapterKecamatan)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.title = "Tambah Alamat"
        activity?.actionBar?.hide()

        val filter = IntentFilter()
        filter.addAction("JOBSERVICE_PROVINSI")
        filter.addAction("JOBSERVICE_KAB_KOTA")
        filter.addAction("JOBSERVICE_KECAMATAN")
        context?.registerReceiver(receiver, filter)

        jobscheduler = context?.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
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
        getDaftarProvinsi()

        var kecamatan = ""
        var kabupatenKota = ""
        var provinsi = ""

        spinnerProvinsi = rootView.detailAlamat_provinsi
        spinnerKabKota = rootView.detailAlamat_kabupatenKota
        spinnerKecamatan = rootView.detailAlamat_kecamatan

        rootView.detailAlamat_provinsi.setOnItemClickListener { parent, view, position, id ->
            provinsi = parent.getItemAtPosition(position).toString()
            var index = ""
            for ((key, value) in daftarProvinsi) {
                if (provinsi.equals(value)) index = key
            }
            getDaftarKabupatenKota(index)
        }

        rootView.detailAlamat_kabupatenKota.setOnItemClickListener { parent, view, position, id ->
            kabupatenKota = parent.getItemAtPosition(position).toString()
            var index = ""
            for ((key, value) in daftarKabupatenKota) {
                if (kabupatenKota.equals(value)) index = key
            }
            getDaftarKecamatan(index)
        }

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

            if (rootView.detailAlamat_btnExecute.text.toString() == "TAMBAH") (activity as AlamatActivity).user.alamat.add(alamat)
            else (activity as AlamatActivity).user.alamat[arguments?.getInt(ALAMAT_POSITION)!!] = alamat

            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.alamat_fragmentContainer, DaftarAlamat())
                commit()
            }
        }

        return rootView
    }

    private fun getDaftarProvinsi() {
        jobscheduler.cancel(JOB_ID)
        val serviceComponent = ComponentName(context!!, GetDaftarProvinsi::class.java)
        val jobInfo = JobInfo.Builder(JOB_ID, serviceComponent)
            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
            .setRequiresDeviceIdle(false)
            .setRequiresCharging(false)
        jobscheduler.schedule(jobInfo.build())
    }

    private fun getDaftarKabupatenKota(idProvinsi: String) {
        jobscheduler.cancel(JOB_ID)
        val serviceComponent = ComponentName(context!!, GetDaftarKabupatenKota::class.java)
        val bundle = PersistableBundle()
        bundle.putString("ID_PROVINSI", idProvinsi)
        val jobInfo = JobInfo.Builder(JOB_ID, serviceComponent)
            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
            .setRequiresDeviceIdle(false)
            .setRequiresCharging(false)
            .setExtras(bundle)
        jobscheduler.schedule(jobInfo.build())
    }

    private fun getDaftarKecamatan(idKabupatenKota: String) {
        jobscheduler.cancel(JOB_ID)
        val serviceComponent = ComponentName(context!!, GetDaftarKecamatan::class.java)
        val bundle = PersistableBundle()
        bundle.putString("ID_KAB_KOTA", idKabupatenKota)
        val jobInfo = JobInfo.Builder(JOB_ID, serviceComponent)
            .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
            .setRequiresDeviceIdle(false)
            .setRequiresCharging(false)
            .setExtras(bundle)
        jobscheduler.schedule(jobInfo.build())
    }
}