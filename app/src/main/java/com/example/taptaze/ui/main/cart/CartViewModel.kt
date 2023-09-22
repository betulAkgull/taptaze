package com.example.taptaze.ui.main.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taptaze.common.Resource
import com.example.taptaze.data.model.ProductUI
import com.example.taptaze.data.model.request.ClearCartRequest
import com.example.taptaze.data.model.request.DeleteFromCartRequest
import com.example.taptaze.data.model.response.CRUDResponse
import com.example.taptaze.data.repository.ProductRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(private val productRepository: ProductRepository) :
    ViewModel() {

    private var _cartState = MutableLiveData<CartState>()
    val cartState: LiveData<CartState>
        get() = _cartState

    private val _totalAmount = MutableLiveData(0.0)
    val totalAmount: LiveData<Double> = _totalAmount

    init {
        getCartProducts(FirebaseAuth.getInstance().currentUser!!.uid)
    }


    fun getCartProducts(userId: String) {
        viewModelScope.launch {
            _cartState.value = CartState.Loading
            val result = productRepository.getCartProducts(userId)
            if (result is Resource.Success) {
                _cartState.value = CartState.CartList(result.data)
                _totalAmount.value = result.data.sumOf { product ->
                    if (product.saleState) {
                        product.salePrice
                    } else {
                        product.price
                    }
                }
            } else if (result is Resource.Error) {
                _cartState.value = CartState.Error(result.throwable)
                clear()
            }
        }
    }

    fun deleteFromCart(id: Int) {
        val deleteFromCartRequest = DeleteFromCartRequest(id)
        viewModelScope.launch {
            _cartState.value = CartState.Loading
            val result = productRepository.deleteFromCart(deleteFromCartRequest)
            if (result is Resource.Success) {
                _cartState.value = CartState.PostResponse(result.data)
                getCartProducts(FirebaseAuth.getInstance().currentUser!!.uid)
            } else if (result is Resource.Error) {
                _cartState.value = CartState.Error(result.throwable)
            }
        }
    }

    fun clearCart(clearCartRequest: ClearCartRequest) {
        viewModelScope.launch {
            _cartState.value = CartState.Loading
            val result = productRepository.clearCart(clearCartRequest)
            if (result is Resource.Success) {
                _cartState.value = CartState.PostResponse(result.data)
                getCartProducts(FirebaseAuth.getInstance().currentUser!!.uid)
            } else if (result is Resource.Error) {
                _cartState.value = CartState.Error(result.throwable)
            }
        }
    }

    fun increase(price: Double) {
        _totalAmount.value = _totalAmount.value?.plus(price)
    }

    fun decrease(price: Double) {
        _totalAmount.value = _totalAmount.value?.minus(price)
    }

    fun clear() {
        _totalAmount.value = 0.0
    }

}

sealed interface CartState {
    object Loading : CartState
    data class PostResponse(val crud: CRUDResponse) : CartState
    data class CartList(val products: List<ProductUI>) : CartState
    data class Error(val throwable: Throwable) : CartState
}