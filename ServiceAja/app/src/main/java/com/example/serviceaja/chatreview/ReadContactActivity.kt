package com.example.serviceaja.chatreview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.serviceaja.R
import com.example.serviceaja.classes.ContactData
import com.example.serviceaja.recyclerview.RecyclerViewContactList
import kotlinx.android.synthetic.main.activity_read_contact.*

class ReadContactActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_read_contact)

        contactListRecyclerView.layoutManager = LinearLayoutManager(this)

        //array untuk menyimpan contact
        val contactLists : MutableList<ContactData> = ArrayList()
        //content resolver untuk membaca database pada contact
        val contacts = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null)

        //untuk mengambil database pada contact, jika cursor dapat di nextkan, maka akan dilakukan pembacaan data
        while (contacts!!.moveToNext()) {
            val name = contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
            val number = contacts.getString(contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
            val obj = ContactData()
            obj.name = name
            obj.number = number

            //add database contact ke array contact
            contactLists.add(obj)
        }

        //adapter recycler view untuk contact list
        contactListRecyclerView.adapter = RecyclerViewContactList(contactLists)
        contacts.close()

        //back ke activity chat
        contact_list_toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }
}