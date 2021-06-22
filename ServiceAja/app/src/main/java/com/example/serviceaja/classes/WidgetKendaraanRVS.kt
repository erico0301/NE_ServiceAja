package com.example.serviceaja.classes

import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.example.serviceaja.KENDARAAN_NO_PLAT
import com.example.serviceaja.R

class WidgetKendaraanRVS : RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory {
        return WidgetKendaraanFactory(applicationContext, intent)
    }

    class WidgetKendaraanFactory(val context: Context, val intent: Intent?) : RemoteViewsFactory {
        var daftarKendaraan = arrayListOf<Kendaraan>()

        override fun onCreate() {
            val no_telp = AccountSharedPref(context).no_telp
            val db = DBHelper(context)
            db.beginTransaction
            daftarKendaraan = db.getAllKendaraan(no_telp!!)
            db.successTransaction
            db.endTransaction
        }

        override fun onDataSetChanged() {
            val no_telp = AccountSharedPref(context).no_telp
            val db = DBHelper(context)
            db.beginTransaction
            daftarKendaraan = db.getAllKendaraan(no_telp!!)
            db.successTransaction
            db.endTransaction
        }

        override fun onDestroy() {
            daftarKendaraan.clear()
        }

        override fun getCount(): Int {
            return daftarKendaraan.size
        }

        override fun getViewAt(position: Int): RemoteViews {
            val views = RemoteViews(context.packageName, R.layout.layout_listview_kendaraan)
            views.setTextViewText(R.id.layoutListViewKendaraan_plat, daftarKendaraan[position].plat)
            views.setTextViewText(R.id.layoutListViewKendaraan_nama, daftarKendaraan[position].merk + " " + daftarKendaraan[position].nama)
            views.setTextViewText(R.id.layoutListViewKendaraan_serviceTerakhir, daftarKendaraan[position].serviceTerakhir)

            val mIntent = Intent()
            mIntent.putExtra("KENDARAAN_TERPILIH", daftarKendaraan[position])
            mIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, intent?.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, -1))
            views.setOnClickFillInIntent(R.id.layoutListViewKendaraan_listItem, mIntent)
            
            return views
        }

        override fun getLoadingView(): RemoteViews? {
            return null
        }

        override fun getViewTypeCount(): Int {
            return 1
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun hasStableIds(): Boolean {
            return true
        }
    }
}