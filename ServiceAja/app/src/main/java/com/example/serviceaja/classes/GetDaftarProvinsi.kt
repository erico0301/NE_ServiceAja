package com.example.serviceaja.classes

import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Intent
import android.util.Log
import com.example.serviceaja.DAFTAR_PROVINSI
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject

class GetDaftarProvinsi: JobService() {
    lateinit var daftarProvinsi: HashMap<String, String>
    val TAG = this::class.java.simpleName

    override fun onStartJob(params: JobParameters?): Boolean {
        Log.e(TAG, "Job Started!")
        daftarProvinsi = HashMap()
        getDaftarProvinsi(params)
        return true
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        Log.e(TAG, "Job Ended!")
        return true
    }

    private fun getDaftarProvinsi(params: JobParameters?) {
        val client = AsyncHttpClient()
        val url = "https://dev.farizdotid.com/api/daerahindonesia/provinsi"
        val charset = Charsets.UTF_8
        val handler = object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?) {
                val data = JSONObject(responseBody?.toString(charset) ?: "Kosong")
                val arrayProvinsi = data.getJSONArray("provinsi")
                for (i in 0 until arrayProvinsi.length() - 1) {
                    daftarProvinsi.put(arrayProvinsi.getJSONObject(i).getString("id"), arrayProvinsi.getJSONObject(i).getString("nama").toUpperCase())
                }
                val intent = Intent("JOBSERVICE_PROVINSI")
                intent.putExtra(DAFTAR_PROVINSI, daftarProvinsi)
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