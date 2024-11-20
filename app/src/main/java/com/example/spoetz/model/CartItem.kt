package com.example.spoetz.model

import android.graphics.Bitmap

data class CartItem(
    val id: Int,
    val title: String,
    val image: ByteArray,
    val price: String
)
