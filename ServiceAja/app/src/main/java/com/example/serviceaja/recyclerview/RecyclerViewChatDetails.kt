package com.example.serviceaja.recyclerview

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.serviceaja.R
import com.example.serviceaja.chatreview.ChatActivity
import com.example.serviceaja.transaction.OnGoingTransactionActivity
import kotlinx.android.synthetic.main.recyclerview_chat_details.view.*

class RecyclerViewChatDetails : RecyclerView.Adapter<RecyclerViewChatDetails.ViewHolder>() {

    private val itemImgBengkel = intArrayOf(R.drawable.mitsubishi_logo, R.drawable.bmw_logo, R.drawable.peugeot_logo)
    private val itemImgBengkel = intArrayOf(R.drawable.mitsubishi_logo, R.drawable.bmw_logo_1, R.drawable.peugeot_logo)
    private val itemNamaBengkel = arrayOf("Mitsubishi", "BMW", "Peugeot")
    private val itemLastMessage = arrayOf("Terima kasih!", "Oke", "Harga OTR Medan")
    private val itemLastHour = arrayOf("10:32 pm", "10:23 am", "23 Des 20")
    private val itemTotalUnreadMessage = arrayOf("2", "3", "1")

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var imgBengkel: ImageView
        var namaBengkel: TextView
        var lastMessage: TextView
        var lastHour: TextView
        var totalUnreadMessage: TextView

        init {
            imgBengkel = itemView.findViewById(R.id.bengkelImg)
            namaBengkel = itemView.findViewById(R.id.namaMitra)
            lastMessage = itemView.findViewById(R.id.lastMessage)
            lastHour = itemView.findViewById(R.id.lastMessageHour)
            totalUnreadMessage = itemView.findViewById(R.id.totalUnreadMessage)

            itemView.setOnClickListener {
                if (adapterPosition == 1) {
                    val chatIntent = Intent(itemView.context, ChatActivity::class.java)
                    itemView.context.startActivity(chatIntent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_chat_details, parent, false)
        view.setOnClickListener{
            parent.context.startActivity(Intent(parent.context, ChatActivity::class.java))
        }
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.imgBengkel.setImageResource(itemImgBengkel[position])
        holder.namaBengkel.text = itemNamaBengkel[position]
        holder.lastMessage.text = itemLastMessage[position]
        holder.lastHour.text = itemLastHour[position]
        holder.totalUnreadMessage.text = itemTotalUnreadMessage[position]
        holder.itemView.apply {
            bengkelImg.setImageResource(itemImgBengkel[position])
            namaMitra.text = itemNamaBengkel[position]
            lastMessage.text = itemLastMessage[position]
            lastMessageHour.text = itemLastHour[position]
            totalUnreadMessage.text = itemTotalUnreadMessage[position]
        }
    }

    override fun getItemCount(): Int {
        return itemNamaBengkel.size
    }

}