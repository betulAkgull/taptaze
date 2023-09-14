package com.example.taptaze.data.source.remote


import com.example.taptaze.common.Constants.Endpoint.GET_ALL_PRODUCTS
import com.example.taptaze.common.Constants.Endpoint.GET_PRODUCT_DETAIL
import com.example.taptaze.data.model.GetProductDetailResponse
import com.example.taptaze.data.model.GetProductResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface ProductService {

    @Headers("store:taptaze")
    @GET(GET_ALL_PRODUCTS)
    suspend fun getAllProducts(): GetProductResponse

    @Headers("store:taptaze")
    @GET(GET_PRODUCT_DETAIL)
    suspend fun getProductDetail(
        @Query("id") id: Int
    ): GetProductDetailResponse
}