package com.example.serviceaja.classes

data class ShoppingCartInstance(
        var namaMitra: String,
        var fotoMitra: Int,
        var kotaMitra: String,
        var itemServiceInstances: MutableList<ItemServiceInstance>,
        var itemProductInstances: MutableList<ItemProductInstance>
) {
}