package com.example.serviceaja.chatreview

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.core.net.toUri
import com.example.serviceaja.R
import kotlinx.android.synthetic.main.activity_review.*
import java.io.*

class ReviewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)

        review_toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        ratingIcon.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            ratingTxt.setText(rating.toString())
        }

        reviewBtn.setOnClickListener {
            finish()
        }

        addImage.setOnClickListener {
            openImage()
        }
    }

    private fun openImage() {
        //directory external file
        var dir = File(getExternalFilesDir(Environment.DIRECTORY_PICTURES)?.toString())
        //file name
        var image = File(dir, "Product.jpg")

        try {
            var stream = FileInputStream(image)
            //Decode stream
            var bitmap = BitmapFactory.decodeStream(stream)
            stream.close()

            //Set Image ke image view
            addImage.setImageBitmap(bitmap)
        } catch (e: IOException) {
            Toast.makeText(this, "Image cannot be opened", Toast.LENGTH_SHORT).show()
        }

    }
}