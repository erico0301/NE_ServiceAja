package com.example.serviceaja

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StatFs
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_settings.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        var spaceAvailable = StatFs(filesDir.path).availableBytes / (1024 * 1024)
        settings_sisaSpace.text = "Sisa Space Penyimpanan: $spaceAvailable MB"

        settings_btnDelete2GB.visibility = View.GONE
        settings_btnDelete4GB.visibility = View.GONE
        settings_btnDelete6GB.visibility = View.GONE

        settings_btnDownload2GB.setOnClickListener {
            if (spaceAvailable >= 2048) {
                activateBtnDownload2GB()
                it.setOnClickListener {  }
                it.setBackgroundColor(getColor(R.color.gray))
                spaceAvailable -= 2048
                settings_sisaSpace.text = "Sisa Space Penyimpanan: $spaceAvailable MB"
                return@setOnClickListener
            }
            Toast.makeText(this, "Sisa Penyimpanan Perangkat tidak mencukupi. Tidak dapat melakukan download", Toast.LENGTH_SHORT).show()
        }

        settings_btnDelete2GB.setOnClickListener {
            val dialog = AlertDialog.Builder(this)
                .setTitle("Menghapus Additional Data 2GB")
                .setMessage("Apakah Anda yakin akan menghapus Data Additional 2GB?")
                .setPositiveButton("YA", DialogInterface.OnClickListener { dialog, which ->
                    settings_btnDownload2GB.setBackgroundColor(getColor(R.color.darkestBlue))
                    settings_btnDownload2GB.setOnClickListener {
                        activateBtnDownload2GB()
                        it.setOnClickListener {  }
                        it.setBackgroundColor(getColor(R.color.gray))
                    }
                    Toast.makeText(this, "Additional Data 2GB berhasil dihapus", Toast.LENGTH_SHORT).show()
                    settings_btnDelete2GB.visibility = View.GONE
                    spaceAvailable += 2048
                    settings_sisaSpace.text = "Sisa Space Penyimpanan: $spaceAvailable MB"
                })
                .setNegativeButton("TIDAK", DialogInterface.OnClickListener { dialog, which ->  })

            dialog.show()
        }

        settings_btnDownload4GB.setOnClickListener {
            if (spaceAvailable >= 4096) {
                activateBtnDownload4GB()
                it.setOnClickListener {  }
                it.setBackgroundColor(getColor(R.color.gray))
                spaceAvailable -= 4096
                settings_sisaSpace.text = "Sisa Space Penyimpanan: $spaceAvailable MB"
                return@setOnClickListener
            }
            Toast.makeText(this, "Sisa Penyimpanan Perangkat tidak mencukupi. Tidak dapat melakukan download", Toast.LENGTH_SHORT).show()
        }

        settings_btnDelete4GB.setOnClickListener {
            val dialog = AlertDialog.Builder(this)
                .setTitle("Menghapus Additional Data 4GB")
                .setMessage("Apakah Anda yakin akan menghapus Data Additional 4GB?")
                .setPositiveButton("YA", DialogInterface.OnClickListener { dialog, which ->
                    settings_btnDownload4GB.setBackgroundColor(getColor(R.color.darkestBlue))
                    settings_btnDownload4GB.setOnClickListener {
                        activateBtnDownload4GB()
                        it.setOnClickListener {  }
                        it.setBackgroundColor(getColor(R.color.gray))
                    }
                    Toast.makeText(this, "Additional Data 4GB berhasil dihapus", Toast.LENGTH_SHORT).show()
                    settings_btnDelete4GB.visibility = View.GONE
                    spaceAvailable += 4096
                    settings_sisaSpace.text = "Sisa Space Penyimpanan: $spaceAvailable MB"
                })
                .setNegativeButton("TIDAK", DialogInterface.OnClickListener { dialog, which ->  })

            dialog.show()
        }

        settings_btnDownload6GB.setOnClickListener {
            if (spaceAvailable >= 6144) {
                activateBtnDownload6GB()
                it.setOnClickListener {  }
                it.setBackgroundColor(getColor(R.color.gray))
                spaceAvailable -= 6144
                settings_sisaSpace.text = "Sisa Space Penyimpanan: $spaceAvailable MB"
                return@setOnClickListener
            }
            Toast.makeText(this, "Sisa Penyimpanan Perangkat tidak mencukupi. Tidak dapat melakukan download", Toast.LENGTH_SHORT).show()
        }

        settings_btnDelete6GB.setOnClickListener {
            val dialog = AlertDialog.Builder(this)
                .setTitle("Menghapus Additional Data 6GB")
                .setMessage("Apakah Anda yakin akan menghapus Data Additional 6GB?")
                .setPositiveButton("YA", DialogInterface.OnClickListener { dialog, which ->
                    settings_btnDownload6GB.setBackgroundColor(getColor(R.color.darkestBlue))
                    settings_btnDownload6GB.setOnClickListener {
                        activateBtnDownload6GB()
                        it.setOnClickListener {  }
                        it.setBackgroundColor(getColor(R.color.gray))
                    }
                    Toast.makeText(this, "Additional Data 6GB berhasil dihapus", Toast.LENGTH_SHORT).show()
                    settings_btnDelete6GB.visibility = View.GONE
                    spaceAvailable += 6144
                    settings_sisaSpace.text = "Sisa Space Penyimpanan: $spaceAvailable MB"
                })
                .setNegativeButton("TIDAK", DialogInterface.OnClickListener { dialog, which ->  })

            dialog.show()
        }
    }

    private fun activateBtnDownload2GB() {
        Toast.makeText(this, "Sedang Mendownload", Toast.LENGTH_SHORT).show()
        Thread.sleep(5000)
        settings_btnDelete2GB.visibility = View.VISIBLE
        Toast.makeText(this, "Download Additional 2GB Berhasil", Toast.LENGTH_SHORT).show()
    }

    private fun activateBtnDownload4GB() {
        Toast.makeText(this, "Sedang Mendownload", Toast.LENGTH_SHORT).show()
        Thread.sleep(10000)
        settings_btnDelete4GB.visibility = View.VISIBLE
        Toast.makeText(this, "Download Additional 4GB Berhasil", Toast.LENGTH_SHORT).show()
    }

    private fun activateBtnDownload6GB() {
        Toast.makeText(this, "Sedang Mendownload", Toast.LENGTH_SHORT).show()
        Thread.sleep(15000)
        settings_btnDelete6GB.visibility = View.VISIBLE
        Toast.makeText(this, "Download Additional 6GB Berhasil", Toast.LENGTH_SHORT).show()
    }
}