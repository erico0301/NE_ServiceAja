package com.example.serviceaja.classes

data class ItemServiceInstance(
        val fotoItem: Int,
        val namaItem: String,
        val hargaItem: String
) {
    var namaMobil: String? = null
    var platMobil: String? = null
}