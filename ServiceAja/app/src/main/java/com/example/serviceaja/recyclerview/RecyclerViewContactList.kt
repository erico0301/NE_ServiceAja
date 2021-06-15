package com.example.serviceaja.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.serviceaja.R
import com.example.serviceaja.classes.ContactData
import kotlinx.android.synthetic.main.recyclerview_contact_list.view.*

class RecyclerViewContactList(items : List<ContactData>)
    : RecyclerView.Adapter<RecyclerViewContactList.ViewHolder>() {

    private var contactLst = items

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name = itemView.namaContact!!
        var number = itemView.contactNumber!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_contact_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerViewContactList.ViewHolder, position: Int) {
        holder.name.text = contactLst[position].name
        holder.number.text = contactLst[position].number
    }

    override fun getItemCount(): Int {
        return contactLst.size
    }

}