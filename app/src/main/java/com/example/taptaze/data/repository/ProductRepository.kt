package com.example.taptaze.data.repository

import com.example.taptaze.common.Resource
import com.example.taptaze.data.model.Product
import com.example.taptaze.data.model.request.AddToCartRequest
import com.example.taptaze.data.model.request.ClearCartRequest
import com.example.taptaze.data.model.request.DeleteFromCartRequest
import com.example.taptaze.data.model.response.CRUDResponse
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

    suspend fun getCartProducts(userId: String): Resource<List<Product>> {
        return try {
            val result = productService.getCartProducts(userId).products

            if (result.isNullOrEmpty()) {
                Resource.Error(Exception("Cart Empty"))
            } else {
                Resource.Success(result)
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    suspend fun deleteFromCart(deleteFromCartRequest: DeleteFromCartRequest): Resource<CRUDResponse> {
        return try {
            val result = productService.deleteFromCart(deleteFromCartRequest)
            if (result.status == 200) {
                Resource.Success(result)
            } else {
                Resource.Error(Exception("Product not deleted"))
            }

        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    suspend fun clearCart(clearCartRequest: ClearCartRequest): Resource<CRUDResponse> {

        return try {
            val result = productService.clearCart(clearCartRequest)
            if (result.status == 200) {
                Resource.Success(result)
            } else {
                Resource.Error(Exception("Cart not Cleared !"))
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }

    }

}
