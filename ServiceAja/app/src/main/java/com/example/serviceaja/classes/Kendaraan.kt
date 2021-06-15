package com.example.serviceaja.classes

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@Parcelize
class Kendaraan (
    var jenis: String,
    var plat: String,
    var merk: String,
    var nama: String,
    var tahun: Int,
    var bahanBakar: String,
    var warna: String,
    var noRangka: String,
    var noMesin: String,
    var noBPKB: String,
    var serviceTerakhir: String,
    var uri_photo: String = ""
) : Parcelable {

}