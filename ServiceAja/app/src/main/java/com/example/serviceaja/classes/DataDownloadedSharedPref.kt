package com.example.serviceaja.classes

import android.content.Context
import android.content.SharedPreferences
import com.example.serviceaja.DATA_DOWNLOADED_INFO_FILE

class DataDownloadedSharedPref(context: Context) {
    val key = "DataDownloaded"
    private var sharedPref: SharedPreferences

    init {
        sharedPref = context.getSharedPreferences(DATA_DOWNLOADED_INFO_FILE, Context.MODE_PRIVATE)
    }
    var dataDownloaded: Boolean
        get() = sharedPref.getBoolean(key, false)
        set(value) {
            sharedPref.edit().putBoolean(key, value).apply()
        }
}