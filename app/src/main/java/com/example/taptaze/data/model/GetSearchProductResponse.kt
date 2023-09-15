package com.example.taptaze.data.model


import com.google.gson.annotations.SerializedName

data class GetSearchProductResponse(
    val message: String?,
    val products: List<Product>?,
    val status: Int?
)