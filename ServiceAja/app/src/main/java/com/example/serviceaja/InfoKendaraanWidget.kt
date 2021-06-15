package com.example.serviceaja

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.RemoteViews
import androidx.core.net.toUri
import com.example.serviceaja.LoginRegister.LoginActivity
import com.example.serviceaja.LoginRegister.MainActivity
import com.example.serviceaja.classes.*

class InfoKendaraanWidget : AppWidgetProvider() {
    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        for (appWidgetId in appWidgetIds) {
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.layoutKendaraanWidget_listKendaraan_listView)
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        val appWidgetId = intent!!.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, -1)
        val sharedPref = WidgetKendaraanSharedPref(context!!, appWidgetId)

        when (intent.action) {
            ACTION_VEHICLE_PICK -> {
                val kendaraan = intent.getParcelableExtra<Kendaraan>("KENDARAAN_TERPILIH")
                sharedPref.no_plat = kendaraan!!.plat
            }
            ACTION_VEHICLE_UNPICK -> sharedPref.clearValues()
        }
        val intentUpdate = Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE)
        intentUpdate.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, intArrayOf(appWidgetId))
        context.sendBroadcast(intentUpdate)

        super.onReceive(context, intent)
    }

    companion object {
        internal fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
            val no_telp = AccountSharedPref(context).no_telp
            val widgetKendaraanSharedPref = WidgetKendaraanSharedPref(context, appWidgetId)
            if (no_telp.equals("Kosong")) {
                widgetKendaraanSharedPref.clearValues()
                val views = RemoteViews(context.packageName, R.layout.layout_kendaraan_widget_not_log_in)
                val loginPendingIntent = PendingIntent.getActivity(context, 1, Intent(context, LoginActivity::class.java), PendingIntent.FLAG_UPDATE_CURRENT)
                views.setOnClickPendingIntent(R.id.layoutKendaraanWidget_notLogin_btnLogin, loginPendingIntent)
                appWidgetManager.updateAppWidget(appWidgetId, views)
                return
            }
            val db = DBHelper(context)
            db.beginTransaction

            val noPlatKendaraanTerpilih = WidgetKendaraanSharedPref(context, appWidgetId).no_plat
            if (noPlatKendaraanTerpilih != null) {
                val kendaraan = db.getKendaraanByPlat(noPlatKendaraanTerpilih)
                if (kendaraan == null)
                    WidgetKendaraanSharedPref(context, appWidgetId).clearValues()
                else {
                    val views = RemoteViews(context.packageName, R.layout.layout_kendaraan_widget_info_kendaraan)
                    views.setTextViewText(R.id.layoutKendaraanWidget_infoKendaraan_nama, kendaraan!!.merk + " " + kendaraan.nama)
                    views.setTextViewText(R.id.layoutKendaraanWidget_infoKendaraan_plat, kendaraan.plat)
                    views.setTextViewText(R.id.layoutKendaraanWidget_infoKendaraan_serviceTerakhir, kendaraan.serviceTerakhir)

                    val bitmap = context.openFileInput(kendaraan.uri_photo + ".jpg").use {
                        BitmapFactory.decodeStream(it)
                    }
                    views.setImageViewBitmap(R.id.layoutKendaraanWidget_infoKendaraan_imageView, bitmap)

                    val intent = Intent(context, InfoKendaraanWidget::class.java)
                    intent.setAction(ACTION_VEHICLE_UNPICK)
                    intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
                    val pendingIntent = PendingIntent.getBroadcast(context, 4, intent, PendingIntent.FLAG_UPDATE_CURRENT)
                    views.setOnClickPendingIntent(R.id.layoutKendaraanWidget_infoKendaraan_btnKendaraanLain, pendingIntent)

                    appWidgetManager.updateAppWidget(appWidgetId, views)
                    return
                }
            }

            val daftarKendaraan = db.getAllKendaraan(no_telp!!)
            if (daftarKendaraan.size <= 0) {
                val views = RemoteViews(context.packageName, R.layout.layout_kendaraan_widget_empty_kendaraan)
                val user = db.getUserWithNoTelp(no_telp)

                val kendaraanIntent = Intent(context, KendaraanActivity::class.java)
                kendaraanIntent.putExtra(EXTRA_USER, user)

                val pendingIntent = PendingIntent.getActivity(context, 2, kendaraanIntent, PendingIntent.FLAG_UPDATE_CURRENT)
                views.setOnClickPendingIntent(R.id.layoutKendaraanWidget_emptyKendaraan_btnDaftarKendaraan, pendingIntent)

                appWidgetManager.updateAppWidget(appWidgetId, views)
            }
            else {
                val intent = Intent(context, WidgetKendaraanRVS::class.java)
                intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)

                val views = RemoteViews(context.packageName, R.layout.layout_kendaraan_widget_list_kendaraan)
                views.setRemoteAdapter(R.id.layoutKendaraanWidget_listKendaraan_listView, intent)

                /*val intentBroadcast = Intent(context, InfoKendaraanWidget::class.java)
                intentBroadcast.setAction(ACTION_VEHICLE_PICK)
                val pendingIntent = PendingIntent.getBroadcast(context, 3, intentBroadcast, PendingIntent.FLAG_UPDATE_CURRENT)
                views.setPendingIntentTemplate(R.id.layoutKendaraanWidget_listKendaraan_listView, pendingIntent)

                 */

                val intentActivity = Intent(context, MainActivity::class.java)
                val pendingIntentActivity = PendingIntent.getActivity(context, 3, intentActivity, PendingIntent.FLAG_UPDATE_CURRENT)
                views.setPendingIntentTemplate(R.id.layoutKendaraanWidget_listKendaraan_listView, pendingIntentActivity)

                appWidgetManager.updateAppWidget(appWidgetId, views)
            }
            db.successTransaction
            db.endTransaction
            db.close()
        }
    }
}

