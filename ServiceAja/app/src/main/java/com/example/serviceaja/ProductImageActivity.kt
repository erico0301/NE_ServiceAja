package com.example.serviceaja

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.net.toUri
import kotlinx.android.synthetic.main.activity_product_image.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

class ProductImageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_image)

        saveBtn.setOnClickListener {
            //cek apakah external storage dapat dibaca dan tersedia
            if(isExternalStorageReadable()) {
                //fun untuk menyimpan image ke external storage
                saveImage()
            }
        }
    }

    private fun saveImage() {
        //directory external file
        var dir = File(getExternalFilesDir(Environment.DIRECTORY_PICTURES)?.toString())

        //jika tidak tersedia maka langsung dibuat folder
        if (!dir.exists()) {
            dir.mkdir()
        }

        //file untuk save image
        val file = File(dir, "Product.jpg ")

        try {
            val stream = FileOutputStream(file)

            //mengambil bitmap dari image view
            var drawable = imageView.getDrawable()
            var bitmap : Bitmap = (drawable as BitmapDrawable).bitmap

            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)

            //simpan menggunakan fungsi flush dari stream
            stream.flush()
            stream.close()

            Toast.makeText(this, "Image saved", Toast.LENGTH_SHORT).show()
        } catch (e:IOException) {
            Toast.makeText(this, "Image cannot be saved", Toast.LENGTH_SHORT).show()
        }
    }

    private fun isExternalStorageReadable(): Boolean {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE), 32)
        }
        var status = Environment.getExternalStorageState()
        if(Environment.MEDIA_MOUNTED.equals(status) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(status)) {
            return true
        }
        return false
    }
}