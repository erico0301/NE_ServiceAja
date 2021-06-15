package com.example.serviceaja.classes

import android.content.Context
import android.content.SharedPreferences
import com.example.serviceaja.LOGIN_INFO_FILE

class AccountSharedPref(context: Context) {
    val NO_TELP = "NO_TELP"
    private var sharePref: SharedPreferences

    init {
        sharePref = context.getSharedPreferences(LOGIN_INFO_FILE, Context.MODE_PRIVATE)
    }

    var no_telp: String?
        get() = sharePref.getString(NO_TELP, "Kosong")
        set(value) {
            sharePref.edit().putString(NO_TELP, value).apply()
        }

    fun clearValues() {
        sharePref.edit().clear().apply()
    }
}