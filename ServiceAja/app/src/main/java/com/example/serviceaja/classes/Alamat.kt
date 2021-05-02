package com.example.serviceaja.classes

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Alamat(
        var namaAlamat: String,
        var namaPenerima: String,
        var noTelepon: String,
        var kecamatan: String,
        var kabupatenKota: String,
        var provinsi: String,
        var detailAlamat: String
): Parcelable {
}