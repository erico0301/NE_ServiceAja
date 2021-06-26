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
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.serviceaja.*
import com.example.serviceaja.R
import com.example.serviceaja.classes.DBHelper
import com.example.serviceaja.classes.GetPrakiraanCuaca
import com.example.serviceaja.classes.User
import com.example.serviceaja.recyclerview.RVBengkelPreview
import com.example.serviceaja.search.SearchActivity
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.mediation.MediationInterstitialAdCallback
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.android.synthetic.main.recyclerview_review_details.*

class HomeFragment : Fragment() {
    private var layoutManager :RecyclerView.LayoutManager? = null
    private var adapter : RecyclerView.Adapter<RVBengkelPreview.ViewHolder>? = null
    lateinit var jobScheduler: JobScheduler

    private var mInterAds : InterstitialAd? = null
    private var clickCount = 0

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

        var db = DBHelper(this.requireContext())
        db?.getAllUsers()

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

        //Inisialisasi mobile ads
        MobileAds.initialize(activity){}

        loadInterstitialAds()

        home_inputSearch.setOnClickListener {
            var searchIntent = Intent(activity, SearchActivity::class.java)
            startActivity(searchIntent)
        }

        wishlistIcon.setOnClickListener {
            clickCount +=1 //tambah click 1
            var wishlistIntent = Intent(activity, WishlistActivity::class.java)
            //jika click sudah 3 kali (untuk contoh ini disimulasikan 3 kali saja, maka akan memunculkan interAds
            if(clickCount==3) {
                if (mInterAds!=null )
                startActivity(wishlistIntent)
                mInterAds?.show(activity)
                clickCount = 0
            }
            else {
                startActivity(wishlistIntent)
            }
        }

        //adsView mengload ads
        bannerAds.loadAd(AdRequest.Builder().build())

        //Listener untuk adsView Banner
        bannerAds.adListener = object : AdListener() {
            override fun onAdFailedToLoad(p0: LoadAdError) {
                super.onAdFailedToLoad(p0)
                //Jika ads gagal di load, maka akan di munculkan Toast
                Toast.makeText(activity, "Iklan sedang tidak tersedia", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadInterstitialAds() {
        InterstitialAd.load(activity, "ca-app-pub-3940256099942544/5224354917",
            AdRequest.Builder().build(), object : InterstitialAdLoadCallback(){
                override fun onAdFailedToLoad(p0: LoadAdError) {
                    super.onAdFailedToLoad(p0)
                    Toast.makeText(activity, "Iklan sedang tidak tersedia", Toast.LENGTH_SHORT).show()
                    mInterAds = null
                }

                override fun onAdLoaded(p0: InterstitialAd) {
                    mInterAds = p0
                }
            })

        mInterAds?.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdShowedFullScreenContent() {
                super.onAdShowedFullScreenContent()
                mInterAds = null
            }
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

    override fun onResume() {
        super.onResume()
        mInterAds = null
        loadInterstitialAds()
    }
}