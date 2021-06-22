package com.example.serviceaja.classes

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

class Transaksi(
        var userID: String,
        var payment: String,
        var confirm: String,
        var packing: String,
        var send: String,
        var receive: String
)