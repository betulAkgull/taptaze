package com.example.taptaze.data.source.remote


import com.example.taptaze.common.Constants.Endpoint.ADD_TO_CART
import com.example.taptaze.common.Constants.Endpoint.GET_ALL_PRODUCTS
import com.example.taptaze.common.Constants.Endpoint.GET_PRODUCT_DETAIL
import com.example.taptaze.common.Constants.Endpoint.GET_SALE_PRODUCTS
import com.example.taptaze.common.Constants.Endpoint.SEARCH_PRODUCT
import com.example.taptaze.data.model.AddToCartRequest
import com.example.taptaze.data.model.CRUDResponse
import com.example.taptaze.data.model.GetProductDetailResponse
import com.example.taptaze.data.model.GetProductResponse
import com.example.taptaze.data.model.GetSaleProductResponse
import com.example.taptaze.data.model.GetSearchProductResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
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

    @Headers("store:taptaze")
    @GET(GET_SALE_PRODUCTS)
    suspend fun getSaleProducts(): GetSaleProductResponse

    @Headers("store:taptaze")
    @GET(SEARCH_PRODUCT)
    suspend fun getSearchProduct(
        @Query("query") query: String
    ): GetSearchProductResponse

    @Headers("store:taptaze")
    @POST(ADD_TO_CART)
    suspend fun addToCart(
        @Body addToCartRequest : AddToCartRequest
    ) : CRUDResponse

}