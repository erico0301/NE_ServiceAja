package com.example.serviceaja.classes

import android.content.Context
import android.content.SharedPreferences
import com.example.serviceaja.LOGIN_INFO_FILE

class AccountSharedPref(context: Context) {
    val EMAIL = "EMAIL"
    private var sharePref: SharedPreferences

    init {
        sharePref = context.getSharedPreferences(LOGIN_INFO_FILE, Context.MODE_PRIVATE)
    }

    var email: String?
        get() = sharePref.getString(EMAIL, "Kosong")
        set(value) {
            sharePref.edit().putString(EMAIL, value).apply()
        }

    fun clearValues() {
        sharePref.edit().clear().apply()
    }
}