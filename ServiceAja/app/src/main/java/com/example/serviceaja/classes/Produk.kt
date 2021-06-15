package com.example.serviceaja.classes

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Produk(
        var nama: String,
        var harga: Long
): Parcelable {
}