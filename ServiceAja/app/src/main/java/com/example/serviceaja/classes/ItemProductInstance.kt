package com.example.serviceaja.classes

data class ItemProductInstance(
        val fotoItem: Int,
        val namaItem: String,
        val hargaItem: String
) {
    var jumlahItem: Int? = null
}