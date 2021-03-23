package com.example.serviceaja.recyclerview

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.serviceaja.R
import com.example.serviceaja.transaction.OnGoingTransactionActivity
import kotlinx.android.synthetic.main.layout_transaction_preview.view.*

class RVTransactionPreview : RecyclerView.Adapter<RVTransactionPreview.ViewHolder>() {

    private val itemImgBengkel = intArrayOf(R.drawable.mitsubishi_logo, R.drawable.bmw_logo)
    private val itemNamaBengkel = arrayOf("Mitsubishi", "BMW")
    private val itemImgItem = intArrayOf(R.drawable.service_xpander, R.drawable.bodykit_x5)
    private val itemNamaService = arrayOf("Service 1 tahun", "Bodykit X5 M Performance")
    private val itemQuantityService = arrayOf("1", "1")
    private val itemHargaService = arrayOf("0", "23.120.000")
    private val itemTotalHarga = arrayOf("820.000", "24.350.000")
    private val itemStatus = arrayOf("Sedang Dikirim", "Belum Dibayar")

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_transaction_preview, parent, false)

        view.setOnClickListener {
            parent.context.startActivity(Intent(parent.context, OnGoingTransactionActivity::class.java))
        }

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            previewTransaksi_fotoMitra.setImageResource(itemImgBengkel[position])
            previewTransaksi_namaMitra.text = itemNamaBengkel[position]
            previewTransaksi_fotoItem.setImageResource(itemImgItem[position])
            previewTransaksi_namaItem.text = itemNamaService[position]
            previewTransaksi_jumlahItem.text = itemQuantityService[position]
            previewTransaksi_hargaItem.text = itemHargaService[position]
            previewTransaksi_totalHarga.text = itemTotalHarga[position]
            previewTransaksi_statusTransaksi.text = itemStatus[position]
        }
    }

    override fun getItemCount(): Int {
        return itemNamaBengkel.size
    }

}