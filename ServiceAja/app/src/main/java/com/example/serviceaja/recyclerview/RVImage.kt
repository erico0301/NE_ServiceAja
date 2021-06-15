package com.example.serviceaja.recyclerview

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.serviceaja.R
import kotlinx.android.synthetic.main.layout_image.view.*

class RVImage(val context: Context, val imagePaths: ArrayList<String>) : RecyclerView.Adapter<RVImage.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_image, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            if (imagePaths[position].equals(""))
                layoutImage_btnRemove.visibility = View.GONE
            else {
                layoutImage_txtNoPic.visibility = View.GONE
                val bitmap = BitmapFactory.decodeStream(context.contentResolver.openInputStream(imagePaths[position].toUri()))
                layoutImage_imageView.setImageBitmap(bitmap)
                layoutImage_btnRemove.setOnClickListener {
                    
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return imagePaths.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}