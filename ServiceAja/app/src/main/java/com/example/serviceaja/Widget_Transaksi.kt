package com.example.serviceaja

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import com.example.serviceaja.classes.DBHelper
import com.example.serviceaja.classes.Transaksi
import com.example.serviceaja.classes.User

/**
 * Implementation of App Widget functionality.
 */
class Widget_Transaksi : AppWidgetProvider() {

    private var currentUser = "admin@gmail.com"

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
    companion object{
        internal fun updateAppWidget(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {
            //variable untuk mengetahui user yang sedang login
            val curentUser = "admin@gmail.com"
            //Database SQLite
            val mydb = DBHelper(context)
            var transaksi :Transaksi? = null
            var allTransaksi: ArrayList<Transaksi>
            allTransaksi = mydb.getAllTransaksi()

            //Melakukan pencarian transaksi pada database
            for (i in allTransaksi) {
                if (curentUser == i.userID) {
                    transaksi = i
                    break
                }
            }
            // Construct the RemoteViews object
            val views = RemoteViews(context.packageName, R.layout.widget__transaksi)

            //mengset text pada widget sesuai dengan pencarian transaksi pada database
            if (transaksi==null){
                views.setTextViewText(R.id.payment_value, "0")
                views.setTextViewText(R.id.konfirmasi_value, "0")
                views.setTextViewText(R.id.packing_value, "0")
                views.setTextViewText(R.id.send_value, "0")
                views.setTextViewText(R.id.receive_value, "0")
            }
            else{
                views.setTextViewText(R.id.payment_value, transaksi.payment)
                views.setTextViewText(R.id.konfirmasi_value, transaksi.confirm)
                views.setTextViewText(R.id.packing_value, transaksi.packing)
                views.setTextViewText(R.id.send_value, transaksi.send)
                views.setTextViewText(R.id.receive_value, transaksi.receive)
            }
            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}

