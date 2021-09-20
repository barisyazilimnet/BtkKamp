package com.example.btk_kamp.model

data class Product(
    val id: String,
    val name: String,
    val price: String,
    val url: String
) {
    var quantity = 0
}
