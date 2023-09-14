package com.example.taptaze.data.model


import com.google.gson.annotations.SerializedName

data class GetProductDetailResponse(
    val message: String?,
    val product: Product?,
    val status: Int?
)