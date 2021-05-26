package com.example.serviceaja.classes

import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Intent
import android.util.Log
import com.example.serviceaja.DAFTAR_KAB_KOTA
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject

class GetDaftarKabupatenKota(): JobService() {
    lateinit var daftarKabupatenKota: HashMap<String, String>
    var idProvinsi: String = ""
    val TAG = this::class.java.simpleName

    override fun onStartJob(params: JobParameters?): Boolean {
        Log.e(TAG, "Job Started!")
        idProvinsi = params?.extras?.getString("ID_PROVINSI", "") ?: ""
        daftarKabupatenKota = HashMap()
        getDaftarKabupatenKota(params)
        return true
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        Log.e(TAG, "Job Ended!")
        return true
    }

    private fun getDaftarKabupatenKota(params: JobParameters?) {
        val client = AsyncHttpClient()
        val url = "https://dev.farizdotid.com/api/daerahindonesia/kota?id_provinsi=$idProvinsi"
        val charset = Charsets.UTF_8
        val handler = object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?) {
                val data = JSONObject(responseBody?.toString(charset) ?: "Kosong")
                val arrayKabKota = data.getJSONArray("kota_kabupaten")
                for (i in 0 until arrayKabKota.length() - 1)
                    daftarKabupatenKota.put(arrayKabKota.getJSONObject(i).getString("id"), arrayKabKota.getJSONObject(i).getString("nama").toUpperCase())
                val intent = Intent("JOBSERVICE_KAB_KOTA")
                intent.putExtra(DAFTAR_KAB_KOTA, daftarKabupatenKota)
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