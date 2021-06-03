package com.example.serviceaja.classes

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class User(
        var nama: String = "",
        var email: String = "",
        var noTelp: String = "",
        var password: String = ""
): Parcelable {
    var kendaraan: ArrayList<Kendaraan> = arrayListOf()
    var alamat: ArrayList<Alamat> = arrayListOf()
}