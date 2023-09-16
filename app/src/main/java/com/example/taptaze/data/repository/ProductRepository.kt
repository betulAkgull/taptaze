package com.example.taptaze.data.repository

import com.example.taptaze.common.Resource
import com.example.taptaze.data.model.AddToCartRequest
import com.example.taptaze.data.model.CRUDResponse
import com.example.taptaze.data.model.Product
import com.example.taptaze.data.source.remote.ProductService


class ProductRepository(private val productService: ProductService) {

    suspend fun getProductDetail(id: Int): Resource<Product> {
        return try {
            val result = productService.getProductDetail(id).product

            result?.let {
                Resource.Success(it)
            } ?: kotlin.run {
                Resource.Error(Exception("Product not found"))
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    suspend fun getAllProducts(): Resource<List<Product>> {
        return try {
            val result = productService.getAllProducts().products

            if (result.isNullOrEmpty()) {
                Resource.Error(Exception("Products not found"))
            } else {
                Resource.Success(result)
            }

        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    suspend fun getSaleProducts(): Resource<List<Product>> {
        return try {
            val result = productService.getSaleProducts().products

            if (result.isNullOrEmpty()) {
                Resource.Error(Exception("Products not found"))
            } else {
                Resource.Success(result)
            }

        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    suspend fun getSearchProducts(query: String): Resource<List<Product>> {
        return try {
            val result = productService.getSearchProduct(query).products

            if (result.isNullOrEmpty()) {
                Resource.Error(Exception("Products not found"))
            } else {
                Resource.Success(result)
            }

        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    suspend fun addToCart(addToCartRequest: AddToCartRequest): Resource<CRUDResponse> {
        return try {
            val result = productService.addToCart(addToCartRequest)
            if (result.status == 400) {
                Resource.Error(Exception("Product not added"))
            } else {
                Resource.Success(result)
            }

        } catch (e: Exception) {
            Resource.Error(e)
        }

    }

}
