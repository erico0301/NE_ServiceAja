package com.example.serviceaja.classes

import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Intent
import android.util.Log
import com.example.serviceaja.EXTRA_PRAKIRAAN
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.json.JSONObject
import kotlin.math.round

class GetPrakiraanCuaca: JobService() {
    val TAG = this::class.java.simpleName
    val kota = "Medan"
    val apiKey = "7ad99a6209af2cd9843f3e69e5b7b3c7"
    lateinit var prakiraan: HashMap<String, String>
    override fun onStartJob(params: JobParameters?): Boolean {
        getPrakiraanCuaca(params)
        prakiraan = HashMap()
        return true
    }

    override fun onStopJob(params: JobParameters?): Boolean {
        return true
    }

    private fun getPrakiraanCuaca(params: JobParameters?) {
        val client = AsyncHttpClient()
        val url = "http://api.openweathermap.org/data/2.5/weather?q=$kota&appid=$apiKey"
        val charset = Charsets.UTF_8
        val handler = object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?) {
                val data = JSONObject(responseBody?.toString(charset) ?: "Kosong")
                val cuaca = data.getJSONArray("weather").getJSONObject(0).getString("description").toUpperCase()
                val suhu = (data.getJSONObject("main").getString("temp").toDouble() - 273.15).round(1).toString()
                val kelembaban = data.getJSONObject("main").getString("humidity") + "%"
                val lokasi = data.getString("name") + ", " + data.getJSONObject("sys").getString("country")
                prakiraan.put("cuaca", cuaca)
                prakiraan.put("suhu", suhu)
                prakiraan.put("kelembaban", kelembaban)
                prakiraan.put("lokasi", lokasi)
                val intent = Intent("PRAKIRAAN_CUACA")
                intent.putExtra(EXTRA_PRAKIRAAN, prakiraan)
                sendBroadcast(intent)
                jobFinished(params, false)
            }

            override fun onFailure(statusCode: Int, headers: Array<out Header>?, responseBody: ByteArray?, error: Throwable?) {
                jobFinished(params, true)
            }
        }
        client.get(url, handler)
    }

    fun Double.round(decimals: Int): Double {
        var multiplier = 1.0
        repeat(decimals) { multiplier *= 10 }
        return round(this * multiplier) / multiplier
    }
}