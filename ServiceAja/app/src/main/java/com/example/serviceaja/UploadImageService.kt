package com.example.serviceaja

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.app.JobIntentService

class UploadImageService : JobIntentService() {
    override fun onHandleWork(intent: Intent) {
        // Mendapatkan foto berupa URI hasil upload pengguna
        val image = intent.getParcelableExtra<Uri>(EXTRA_IMAGE)
        var uploadProgress = 20
        // Melakukan proses upload dengan waktu sekitar 5 detik (5000 ms)
        while (uploadProgress < 100) {
            val intent = Intent(ACTION_UPLOAD_IMAGE)
            Thread.sleep(1000)
            // Extra yang menunjukkan progress upload yang dilakukan, dikirim sebagai broadcast ke EditProfilUser.kt
            intent.putExtra(EXTRA_UPLOAD_PROGRESS, uploadProgress)
            sendBroadcast(intent)           // Pengiriman broadcast
            uploadProgress += 20
        }
        // Setelah upload progress mencapai 100, maka akan dikirimkan kembali foto tersebut kepada aktivitas pemanggil melalui broadcast.
        val intent = Intent(ACTION_UPLOAD_IMAGE)
        intent.putExtra(EXTRA_IMAGE, image)
        intent.putExtra(EXTRA_UPLOAD_PROGRESS, uploadProgress)
        sendBroadcast(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    companion object {
        fun enqueueWork(context: Context, intent: Intent) {
            enqueueWork(context, UploadImageService::class.java, UPLOAD_IMAGE_JOB_ID, intent)
        }
    }
}