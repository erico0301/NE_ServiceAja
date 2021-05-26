package com.example.serviceaja

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.widget.Toast

class TestSoundService : Service(), MediaPlayer.OnPreparedListener,
    MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener{

    private var mediaPlayer: MediaPlayer? = null

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if(intent!=null) {
            var actionIntent = intent.action
            when(actionIntent) {
                ACTION_CREATE -> {
                    mediaPlayer = MediaPlayer()
                    mediaPlayer?.setOnPreparedListener(this)
                    mediaPlayer?.setOnPreparedListener(this)
                    mediaPlayer?.setOnErrorListener(this)
                }
                ACTION_PLAY -> {
                    if (!mediaPlayer!!.isPlaying) {
                        val assetFileDescriptor = this.resources.openRawResourceFd(R.raw.xpander_sound_system_tester)
                        mediaPlayer?.run {
                            reset()
                            setDataSource(
                                assetFileDescriptor.fileDescriptor,
                                assetFileDescriptor.startOffset,
                                assetFileDescriptor.declaredLength
                            )
                            prepareAsync()
                        }
                    }
                }
                ACTION_STOP -> mediaPlayer?.stop()
            }
        }

        return flags
    }

    override fun onPrepared(mp: MediaPlayer?) {
        mediaPlayer?.start()
        Toast.makeText(this, "Gunakan earphone untuk kualitas audio lebih baik", Toast.LENGTH_SHORT).show()
    }

    override fun onError(mp: MediaPlayer?, what: Int, extra: Int): Boolean {
        Toast.makeText(this, "Tester Error", Toast.LENGTH_SHORT).show()
        return false
    }

    override fun onCompletion(mp: MediaPlayer?) {

    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
    }
}