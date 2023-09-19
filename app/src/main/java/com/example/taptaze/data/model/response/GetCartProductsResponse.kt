package com.example.taptaze.data.model.response


import com.example.taptaze.data.model.Product

data class GetCartProductsResponse(
    val message: String?,
    val products: List<Product>?,
    val status: Int?
)