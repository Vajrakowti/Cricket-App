package com.example.spoetz.model


data class Item(
    val id: Int,
    val item: String,
    val price: String,
    val rating: String,
    val description: List<String>,
    val imageUrl: String
)
