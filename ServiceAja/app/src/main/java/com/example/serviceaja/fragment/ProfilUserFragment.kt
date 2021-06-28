package com.example.serviceaja.fragment

import android.app.Activity
import android.app.AlarmManager
import android.app.AlertDialog
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.*
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import com.example.serviceaja.*
import com.example.serviceaja.LoginRegister.MainActivity
import com.example.serviceaja.classes.AccountSharedPref
import com.example.serviceaja.classes.DBHelper
import com.example.serviceaja.classes.User
import com.example.serviceaja.classes.WatchAdsSharedPref
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback
import kotlinx.android.synthetic.main.fragment_profil_user.*
import kotlinx.android.synthetic.main.fragment_profil_user.view.*
import java.text.SimpleDateFormat
import java.util.*

class ProfilUserFragment : Fragment() {
    private lateinit var user: User
    private lateinit var adRequest: AdRequest

    private lateinit var pendingIntentInterstitialAd: PendingIntent
    private lateinit var pendingIntentRewardedAd: PendingIntent
    private lateinit var mAlarmManager: AlarmManager

    private var mInterstitialAd: InterstitialAd? = null
    private var mRewardedAd: RewardedAd? = null
    private var sdf = SimpleDateFormat("dd/MM/yyyy")
    private lateinit var watchAdsSharedPref : WatchAdsSharedPref

    companion object {
        val ACTION_REWARDED_ADS_READY = "com.example.serviceaja.ACTION_REWARDED_ADS_READY"
        val ACTION_INTERSTITIAL_ADS_READY = "com.example.serviceaja.ACTION_INTERSTITIAL_ADS_READY"
    }

    private var adsLoadReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent!!.action.equals(ACTION_REWARDED_ADS_READY)) {
                watchAdsSharedPref.lastWatchDate = sdf.format(Calendar.getInstance().time)
                if (WatchAdsSharedPref(context!!).watchAdsTime!! < 5) {
                    loadRewardedAds()
                    return
                }
                profilUser_btnWatchAds.isEnabled = false
                profilUser_txtWatchAdsInfo.text = "Anda telah mencapai limit menonton video hari ini (5 kali)"
                mAlarmManager.cancel(pendingIntentRewardedAd)
            }
            else if (intent.action.equals(ACTION_INTERSTITIAL_ADS_READY)) {
                loadInterstitialAds()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        user = arguments?.getParcelable(EXTRA_USER) ?: User("admin", "admin@gmail" ,"01812135484", "asdfghjkl")
        watchAdsSharedPref = WatchAdsSharedPref(context!!)
        adRequest = AdRequest.Builder().build()
        mAlarmManager = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        if (!(watchAdsSharedPref.lastWatchDate.equals(sdf.format(Calendar.getInstance().time)) && (watchAdsSharedPref.watchAdsTime ?: 0) == 5)) {
            loadRewardedAds()
            context!!.registerReceiver(adsLoadReceiver, IntentFilter(ACTION_REWARDED_ADS_READY))

            val intentRewardedAd = Intent(ACTION_REWARDED_ADS_READY)
            pendingIntentRewardedAd = PendingIntent.getBroadcast(context, 0, intentRewardedAd, PendingIntent.FLAG_UPDATE_CURRENT)
            mAlarmManager.setRepeating(AlarmManager.RTC, 60000, 60000, pendingIntentRewardedAd)
        }

        if (!user.premium_user) {
            loadInterstitialAds()
            val intentInterstitialAd = Intent(ACTION_INTERSTITIAL_ADS_READY)
            pendingIntentInterstitialAd = PendingIntent.getBroadcast(context, 1, intentInterstitialAd, PendingIntent.FLAG_UPDATE_CURRENT)
            mAlarmManager.setRepeating(AlarmManager.RTC, 60000, 60000, pendingIntentInterstitialAd)

            context!!.registerReceiver(adsLoadReceiver, IntentFilter(ACTION_INTERSTITIAL_ADS_READY))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_profil_user, container, false)
        Log.e("profilUserFragment onCreateVIew", "${user.nama}, ${user.email}, ${user.noTelp}")

        val db = DBHelper(this.requireContext())
        db.getAllUsers()

        view.profilUser_namaUser.text = user.nama
        view.profilUser_emailUser.text = user.email
        view.profilUser_noTelpUser.text = "(+62)-${user.noTelp}"
        view.profilUser_txtUserPoints.text = user.points.toString()

        view.findViewById<Button>(R.id.profilUser_btnMitra).setOnClickListener {
            val profilMitraFragment = ProfilMitraFragment()
            val bundle = Bundle()
            bundle.putParcelable(EXTRA_USER, user)
            profilMitraFragment.arguments = bundle
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.fragment_container, profilMitraFragment)
                commit()
            }
        }

        /*View ImageButton berikut ditambahkan event OnClickListener sehingga pada saat diklik,
        akan membuka Activity EditProfilUser.kt.
        Langkah ini merupakan demonstrasi dari penggunaan Intent Eksplisit
         */
        view.findViewById<ImageButton>(R.id.profilUser_btnEdit).setOnClickListener {
            if (mInterstitialAd != null) {
                mInterstitialAd!!.fullScreenContentCallback = object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {
                        super.onAdDismissedFullScreenContent()
                        loadInterstitialAds()
                        val intent = Intent(activity, EditProfilUser::class.java)
                        intent.putExtra(EXTRA_USER, user)
                        activity?.startActivityForResult(intent, REQ_CODE_EDIT_PROFILE)
                    }
                }
                mInterstitialAd!!.show(activity!!)
                return@setOnClickListener
            }
            val intent = Intent(activity, EditProfilUser::class.java)
            intent.putExtra(EXTRA_USER, user)
            activity?.startActivityForResult(intent, REQ_CODE_EDIT_PROFILE)
        }

        // Event ini digunakan untuk membuka Intent Eksplisit AlamatActivity.kt
        view.findViewById<ImageButton>(R.id.profilUser_btnEditLocation).setOnClickListener {
            if (mInterstitialAd != null) {
                mInterstitialAd!!.fullScreenContentCallback = object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {
                        super.onAdDismissedFullScreenContent()
                        loadInterstitialAds()
                        val intent = Intent(activity, AlamatActivity::class.java)
                        intent.putExtra(EXTRA_USER, user)
                        activity?.startActivityForResult(intent, REQ_CODE_EDIT_LOCATION)
                    }
                }
                mInterstitialAd!!.show(activity!!)
                return@setOnClickListener
            }
            val intent = Intent(activity, AlamatActivity::class.java)
            intent.putExtra(EXTRA_USER, user)
            activity?.startActivityForResult(intent, REQ_CODE_EDIT_LOCATION)
        }

        // Event ini digunakan untuk membuka Intent Eksplisit KendaraanActivity.kt
        view.profilUser_btnKendaraanLain.setOnClickListener {
            if (mInterstitialAd != null) {
                mInterstitialAd!!.fullScreenContentCallback = object : FullScreenContentCallback() {
                    override fun onAdDismissedFullScreenContent() {
                        super.onAdDismissedFullScreenContent()
                        loadInterstitialAds()
                        val intent = Intent(activity, KendaraanActivity::class.java)
                        intent.putExtra(EXTRA_USER, user)
                        startActivity(intent)
                    }
                }
                mInterstitialAd!!.show(activity!!)
                return@setOnClickListener
            }
            val intent = Intent(activity, KendaraanActivity::class.java)
            intent.putExtra(EXTRA_USER, user)
            startActivity(intent)
        }

        view.profilUser_btnSettings.setOnClickListener {
            val intent = Intent(activity, setting::class.java)
            intent.putExtra(EXTRA_USER, user)
            startActivity(intent)
        }

        view.findViewById<ImageButton>(R.id.btn_logout).setOnClickListener {
            startActivity(Intent(activity, MainActivity::class.java))
            AccountSharedPref(activity!!).clearValues()
            updateWidget()
            activity?.finishAffinity()
        }

        view.profilUser_btnWatchAds.isEnabled = false
        if (watchAdsSharedPref.lastWatchDate.equals(sdf.format(Calendar.getInstance().time)) && (watchAdsSharedPref.watchAdsTime ?: 0) == 5)
            view.profilUser_txtWatchAdsInfo.text = "Anda telah mencapai limit menonton video hari ini (5 kali)"
        view.profilUser_btnWatchAds.setOnClickListener {
            loadRewardedAds()
            if (mRewardedAd != null)
                mRewardedAd!!.show(activity!!) {
                    user.points += 20
                    db.updateUser(user.noTelp, user)
                    view.profilUser_txtUserPoints.text = user.points.toString()
                    Toast.makeText(context, "Berhasil mendapatkan 20 Point!", Toast.LENGTH_SHORT).show()
                    watchAdsSharedPref.watchAdsTime = (watchAdsSharedPref.watchAdsTime ?: 0) + 1
                    view.profilUser_btnWatchAds.isEnabled = false
                    Log.e("SharedPreferences", "${watchAdsSharedPref.watchAdsTime}, ${watchAdsSharedPref.lastWatchDate}")
                    if ((watchAdsSharedPref.watchAdsTime ?: 0) == 5)
                        profilUser_txtWatchAdsInfo.text = "Anda telah mencapai limit menonton video hari ini (5 kali)"
                    else
                        profilUser_txtWatchAdsInfo.text = "Iklan tidak tersedia untuk sementara waktu."
                }
        }

        view.profilUser_layoutPremium.visibility = View.GONE

        if (!user.premium_user) {
            view.profilUser_layoutPremium.visibility = View.VISIBLE
            view.profilUser_btnBeliPremium.setOnClickListener {
                val dialog = AlertDialog.Builder(context)
                    .setTitle("Beli Premium")
                    .setMessage("Anda akan membeli Premium seharga Rp50.000,- berlaku hingga 1 bulan. Konfirmasi pembelian?")
                    .setPositiveButton("YA") { dialogInterface: DialogInterface, i: Int ->
                        user.premium_user = true
                        db.updateUser(user.noTelp, user)
                        Toast.makeText(context, "Anda Berhasil membeli status Premium untuk akun Anda!", Toast.LENGTH_SHORT).show()
                        view.profilUser_layoutPremium.visibility = View.GONE
                        mInterstitialAd = null
                        mAlarmManager.cancel(pendingIntentInterstitialAd)
                    }
                    .setNegativeButton("BATAL") { dialogInterface: DialogInterface, i: Int -> }

                dialog.show()
            }
        }

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        context!!.unregisterReceiver(adsLoadReceiver)
    }

    private fun updateWidget() {
        val appWidgetManager = AppWidgetManager.getInstance(context!!)
        val ids = appWidgetManager.getAppWidgetIds(ComponentName(context!!, InfoKendaraanWidget::class.java))
        val intent = Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE)
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
        context!!.sendBroadcast(intent)
    }

    private fun loadRewardedAds() {
        if (watchAdsSharedPref.lastWatchDate.equals(sdf.format(Calendar.getInstance().time)) && (watchAdsSharedPref.watchAdsTime ?: 0) == 5) {
            mRewardedAd = null
            profilUser_btnWatchAds.isEnabled = false
            profilUser_txtWatchAdsInfo.text = "Anda telah mencapai limit menonton video hari ini (5 kali)"
            return
        }
        RewardedAd.load(context!!, "ca-app-pub-3940256099942544/5224354917", adRequest, object : RewardedAdLoadCallback() {
            override fun onAdFailedToLoad(p0: LoadAdError) {
                super.onAdFailedToLoad(p0)
                mRewardedAd = null
                profilUser_btnWatchAds.isEnabled = false
                profilUser_txtWatchAdsInfo.text = "Iklan tidak tersedia untuk sementara waktu."
            }

            override fun onAdLoaded(p0: RewardedAd) {
                super.onAdLoaded(p0)
                mRewardedAd = p0
                profilUser_btnWatchAds.isEnabled = true
                profilUser_txtWatchAdsInfo.text = "Iklan dapat ditonton sekarang."
            }
        })
    }

    private fun loadInterstitialAds() {
        InterstitialAd.load(context, "ca-app-pub-3940256099942544/1033173712", adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(p0: LoadAdError) {
                super.onAdFailedToLoad(p0)
                mInterstitialAd = null
            }

            override fun onAdLoaded(p0: InterstitialAd) {
                super.onAdLoaded(p0)
                mInterstitialAd = p0
            }
        })
    }
}