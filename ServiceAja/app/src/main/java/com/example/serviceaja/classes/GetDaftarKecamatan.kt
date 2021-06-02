package com.example.serviceaja.classes

import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Intent
import android.util.Log
import com.example.serviceaja.DAFTAR_KECAMATAN
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject

class GetDaftarKecamatan(): JobService() {
    lateinit var daftarKecamatan: ArrayList<String>
    var idKabupatenKota: String = ""
    val TAG = this::class.java.simpleName

    override fun onStartJob(params: JobParameters?): Boolean {
        Log.e(TAG, "Job Started!")
        idKabupatenKota = params?.extras?.getString("ID_KAB_KOTA", "") ?: ""
        daftarKecamatan = arrayListOf()
        getDaftarKecamatan(params)
        return true
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        Log.e(TAG, "Job Started!")
        return true
    }

    private fun getDaftarKecamatan(params: JobParameters?) {
        val client = AsyncHttpClient()
        val url = "https://dev.farizdotid.com/api/daerahindonesia/kecamatan?id_kota=$idKabupatenKota"
        val charset = Charsets.UTF_8
        val handler = object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?) {
                val data = JSONObject(responseBody?.toString(charset) ?: "Kosong")
                val arrayKecamatan = data.getJSONArray("kecamatan")
                for (i in 0 until arrayKecamatan.length() - 1)
                    daftarKecamatan.add(arrayKecamatan.getJSONObject(i).getString("nama").toUpperCase())
                val intent = Intent("JOBSERVICE_KECAMATAN")
                intent.putExtra(DAFTAR_KECAMATAN, daftarKecamatan)
                sendBroadcast(intent)
                jobFinished(params, false)
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?, error: Throwable?) {
                jobFinished(params, true)
            }
        }
        client.get(url, handler)
    }
}