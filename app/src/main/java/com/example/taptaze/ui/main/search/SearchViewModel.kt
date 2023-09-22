package com.example.taptaze.ui.main.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taptaze.common.Resource
import com.example.taptaze.data.model.Product
import com.example.taptaze.data.model.ProductUI
import com.example.taptaze.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val productRepository: ProductRepository) :
    ViewModel() {

    private var _searchState = MutableLiveData<SearchState>()
    val searchState: LiveData<SearchState>
        get() = _searchState


    fun getProductSearch(query: String) {
        viewModelScope.launch {

            _searchState.value = SearchState.Loading

            val result = productRepository.getSearchProducts(query)
            if (result is Resource.Success) {
                _searchState.value = SearchState.Data(result.data)
            } else if (result is Resource.Error) {
                _searchState.value = SearchState.Error(result.throwable)
            }
        }
    }

    fun addToFavorites(product: ProductUI) {
        viewModelScope.launch {
            productRepository.addToFavorites(product)
        }
    }

}

sealed interface SearchState {
    object Loading : SearchState
    data class Data(val products: List<ProductUI>) : SearchState
    data class Error(val throwable: Throwable) : SearchState
}