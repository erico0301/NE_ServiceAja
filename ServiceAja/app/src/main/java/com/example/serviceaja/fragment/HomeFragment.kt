package com.example.serviceaja.fragment

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.*
import android.os.Bundle
import android.os.Environment
import android.os.StatFs
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.serviceaja.*
import com.example.serviceaja.classes.GetPrakiraanCuaca
import com.example.serviceaja.classes.User
import com.example.serviceaja.recyclerview.RVBengkelPreview
import com.example.serviceaja.search.SearchActivity
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.recyclerview_review_details.*

class HomeFragment : Fragment() {
    private var layoutManager :RecyclerView.LayoutManager? = null
    private var adapter : RecyclerView.Adapter<RVBengkelPreview.ViewHolder>? = null
    lateinit var jobScheduler: JobScheduler

    lateinit var prakiraanCuaca: HashMap<String, String>
    lateinit var viewCuaca: ConstraintLayout
    lateinit var progressBar: ProgressBar
    lateinit var rekomendasi: TextView
    lateinit var suhu: TextView
    lateinit var cuaca: TextView
    lateinit var lokasi: TextView
    lateinit var kelembaban: TextView

    val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            Thread.sleep(2000L)
            viewCuaca.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
            prakiraanCuaca = intent?.extras?.getSerializable(EXTRA_PRAKIRAAN) as HashMap<String, String>
            suhu.text = prakiraanCuaca["suhu"]
            cuaca.text = prakiraanCuaca["cuaca"]
            lokasi.text = prakiraanCuaca["lokasi"]
            kelembaban.text = prakiraanCuaca["kelembaban"]
            if (prakiraanCuaca["cuaca"]!!.contains("Sunny", true))
                rekomendasi.text = "Hari yang cerah, waktunya cuci mobil!"
            else if (prakiraanCuaca["cuaca"]!!.contains("Cloud", true))
                rekomendasi.text = "Pertimbangin dulu yah kalau mau cuci mobil, agak berawan!"
            else if (prakiraanCuaca["cuaca"]!!.contains("Rain", true))
                rekomendasi.text = "Lebih baik tunggu besok deh. Entar Dicuci juga percuma!"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val filter = IntentFilter("PRAKIRAAN_CUACA")
        context!!.registerReceiver(receiver, filter)

        jobScheduler = context?.getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        viewCuaca = view.home_informasiCuaca
        progressBar = view.home_progressBar
        rekomendasi = view.home_pesanRekomendasi
        suhu = view.home_suhu
        cuaca = view.home_cuacaSaatIni
        lokasi = view.home_lokasi
        kelembaban = view.home_kelembabanUdara
        getPrakiraanCuaca()
        viewCuaca.visibility = View.GONE
        val user = arguments?.getParcelable<User>(EXTRA_USER)

        view.home_refreshCuaca.setOnClickListener { getPrakiraanCuaca() }

        view.hello.text = "Halo, ${user?.nama}"

        if (user?.kendaraan!!.size > 0)
            view.homePage_kendaraanKosong.visibility = View.GONE
        else
            view.layout_vehicle_info.visibility = View.GONE
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        layoutManager = LinearLayoutManager(activity)
        recyclerViewBengkelDetails.layoutManager = layoutManager

        adapter = RVBengkelPreview()
        recyclerViewBengkelDetails.adapter = adapter

        home_inputSearch.setOnFocusChangeListener { v, hasFocus ->
            if (v == home_inputSearch && hasFocus) startActivity(Intent(activity, SearchActivity::class.java))
        }

        home_inputSearch.setOnClickListener {
            var searchIntent = Intent(activity, SearchActivity::class.java)
            startActivity(searchIntent)
        }

        wishlistIcon.setOnClickListener {
            var wishlistIntent = Intent(activity, WishlistActivity::class.java)
            startActivity(wishlistIntent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        context!!.unregisterReceiver(receiver)
    }

    private fun getPrakiraanCuaca() {
        viewCuaca.visibility = View.GONE
        progressBar.visibility = View.VISIBLE
        jobScheduler.cancel(JOB_ID_CUACA)
        val serviceComponent = ComponentName(context!!, GetPrakiraanCuaca::class.java)
        val jobInfo = JobInfo.Builder(JOB_ID_CUACA, serviceComponent)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setRequiresCharging(false)
                .setRequiresDeviceIdle(false)
                .setPeriodic(15 * 60 * 1000)
        jobScheduler.schedule(jobInfo.build())
    }
}