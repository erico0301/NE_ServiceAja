package com.example.serviceaja.classes

import android.content.Context
import android.content.SharedPreferences

class WatchAdsSharedPref(context: Context) {
    val name = "TIMES_WATCH_ADS"
    val key_times = "TIMES"
    val key_date = "DATE"

    val sharedPref: SharedPreferences
    init {
        sharedPref = context.getSharedPreferences(name, Context.MODE_PRIVATE)
    }

    var watchAdsTime: Int?
        get() = sharedPref.getInt(key_times, 0)
        set(value) = sharedPref.edit().putInt(key_times, value ?: 0).apply()

    var lastWatchDate: String?
        get() = sharedPref.getString(key_date, null)
        set(value) = sharedPref.edit().putString(key_date, value ?: "").apply()
    fun clearValues() {
        sharedPref.edit().clear().apply()
    }
}