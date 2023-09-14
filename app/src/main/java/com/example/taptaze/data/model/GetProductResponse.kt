package com.example.taptaze.data.model


data class GetProductResponse(
    val message: String?,
    val products: List<Product>?,
    val status: Int?
)