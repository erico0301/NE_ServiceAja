package com.example.serviceaja

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.app.JobIntentService

class UploadImageService : JobIntentService() {
    override fun onHandleWork(intent: Intent) {
        val image = intent.getParcelableExtra<Uri>(EXTRA_IMAGE)
        var uploadProgress = 20

        while (uploadProgress < 100) {
            val intent = Intent(ACTION_UPLOAD_IMAGE)
            Thread.sleep(1000)
            intent.putExtra(EXTRA_UPLOAD_PROGRESS, uploadProgress)
            sendBroadcast(intent)
            uploadProgress += 20
        }

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