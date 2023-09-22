package com.example.taptaze.data.model.response


import com.example.taptaze.data.model.Product

data class GetProductDetailResponse(
    val message: String?,
    val product: Product,
    val status: Int?
)