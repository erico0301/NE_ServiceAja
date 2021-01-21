package com.example.serviceaja.classes

import android.os.Parcel
import android.os.Parcelable

class User(
        var nama: String,
        var email: String,
        var noTelp: String,
        var password: String
): Parcelable {
    init {
        var kendaraan = arrayListOf<Kendaraan>()
        var alamat = arrayListOf<Alamat>()
    }

    constructor(parcel: Parcel) : this(
            parcel.readString()!!,
            parcel.readString()!!,
            parcel.readString()!!,
            parcel.readString()!!) {
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(nama)
        dest?.writeString(email)
        dest?.writeString(noTelp)
        dest?.writeString(password)
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }

}