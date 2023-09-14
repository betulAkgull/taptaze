package com.example.taptaze.ui.main.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taptaze.common.Resource
import com.example.taptaze.data.model.Product
import com.example.taptaze.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val productRepository: ProductRepository) :
    ViewModel() {

    private var _homeState = MutableLiveData<HomeState>()
    val homeState: LiveData<HomeState>
        get() = _homeState


    fun getAllProducts() {
        viewModelScope.launch {

            _homeState.value = HomeState.Loading

            val result = productRepository.getAllProducts()

            if (result is Resource.Success) {
                _homeState.value = HomeState.Data(result.data)
            } else if (result is Resource.Error) {
                _homeState.value = HomeState.Error(result.throwable)
            }

        }
    }
}

sealed interface HomeState {
    object Loading : HomeState
    data class Data(val products: List<Product>) : HomeState
    data class Error(val throwable: Throwable) : HomeState
}