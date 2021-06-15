package com.example.serviceaja.classes

import android.content.Context
import android.content.SharedPreferences

class WidgetKendaraanSharedPref(context: Context, key: Int) {
    private val sharedPref: SharedPreferences
    init {
        sharedPref = context.getSharedPreferences("WidgetKendaraan_$key", Context.MODE_PRIVATE)
    }

    var no_plat: String?
        get() = sharedPref.getString("NO_PLAT", null)
        set(value) = sharedPref.edit().putString("NO_PLAT", value).apply()

    fun clearValues() {
        sharedPref.edit().clear().apply()
    }
}