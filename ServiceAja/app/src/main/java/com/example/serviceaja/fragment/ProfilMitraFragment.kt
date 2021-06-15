package com.example.serviceaja.fragment

import DBClass.DBProduk
import android.content.Intent
import android.database.sqlite.SQLiteException
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.serviceaja.*
import com.example.serviceaja.classes.DBHelper
import com.example.serviceaja.classes.DataDownloadedSharedPref
import com.example.serviceaja.classes.Produk
import com.example.serviceaja.recyclerview.RVDaftarProdukDownload
import kotlinx.android.synthetic.main.fragment_profil_mitra.*
import kotlinx.android.synthetic.main.fragment_profil_mitra.view.*
import org.jetbrains.anko.Orientation
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class ProfilMitraFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val fragment = inflater.inflate(R.layout.fragment_profil_mitra, container, false)

        fragment.findViewById<Button>(R.id.profilMitra_btnUser).setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ProfilUserFragment())
                .commit()
        }

        fragment.findViewById<ImageButton>(R.id.profilMitra_btnEdit).setOnClickListener {
            val intent = Intent(requireActivity(), DaftardanEditProfilMitra::class.java)
            intent.setData(Uri.parse("Edit"))
            startActivity(intent)
        }

        val db = DBHelper(context!!)

        if (!DataDownloadedSharedPref(context!!).dataDownloaded) {
            fragment.profilMitra_daftarProduk.visibility = View.GONE
            fragment.profilMitra_fabAddProduct.visibility = View.GONE
            fragment.profilMitra_fabEditProduct.visibility = View.GONE

            val produk = arrayListOf<Produk>()
            for (i in 1 until 500) {
                produk.add(
                    Produk("Produk $i", (10000 + (i % 9 * 1000).toLong()))
                )
            }
            fragment.profilMitra_btnDownloadData.setOnClickListener {
                profilMitra_btnDownloadData.isEnabled = false
                profilMitra_downloadProgress.progress = 0
                profilMitra_downloadProgress.max = produk.size
                db.beginTransaction
                try {
                    doAsync {
                        for (i in produk) {
                            db.addProduct(i)
                            uiThread {
                                fragment.profilMitra_downloadProgress.progress = fragment.profilMitra_downloadProgress.progress + 1
                            }
                        }
                        DataDownloadedSharedPref(context!!).dataDownloaded = true
                        uiThread {
                            val products = db.getAllProducts()
                            dataDownloaded(fragment, products)
                        }
                    }
                    db.successTransaction
                } catch (e: SQLiteException) {
                    Log.e("Error while Downloading Data and Inserting to Database", e.toString())
                } finally {
                    db.endTransaction
                }
            }
            return fragment
        }

        val products = db.getAllProducts()
        dataDownloaded(fragment, products)

        return fragment

        /*childFragmentManager.beginTransaction().apply {
            add(R.id.profilMitra_fragmentContainer, DaftarService())
            commit()
        }*/


    }

    private fun dataDownloaded(fragment: View, products: ArrayList<Produk>) {
        fragment.profilMitra_downloadData.visibility = View.GONE
        val db = DBHelper(context!!)
        val cursor = db.dbReader.query(DBProduk.tableProduk.TABLE_NAME, null, null, null, null, null, null)
        with(cursor) {
            while (moveToNext()) {
                Log.e("Data Produk", "${getString(0)}, ${getString(1)}, ${getString(2)}")
            }
        }
        products.add(0, Produk("", 0))
        fragment.profilMitra_daftarProduk.apply {
            visibility = View.VISIBLE
            adapter = RVDaftarProdukDownload(products)
            layoutManager = LinearLayoutManager(context!!)
        }
        val intent = Intent(context!!, ManageProduct::class.java)
        fragment.profilMitra_fabAddProduct.apply {
            visibility = View.VISIBLE
            setOnClickListener {
                intent.putExtra("MANAGE_PRODUCT", "Tambah")
                startActivity(intent)
            }
        }

        fragment.profilMitra_fabEditProduct.apply {
            visibility = View.VISIBLE
            setOnClickListener {
                products.removeAt(0)
                intent.putExtra(DAFTAR_PRODUK, products)
                intent.putExtra("MANAGE_PRODUCT", "Edit")
                activity!!.startActivityForResult(intent, 201)
            }
        }
    }

}