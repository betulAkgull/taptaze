package com.example.taptaze.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taptaze.common.Resource
import com.example.taptaze.data.repository.AuthRepo
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val repo: AuthRepo) : ViewModel() {

    private var _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState>
        get() = _authState

    val currentUser: FirebaseUser?
        get() = repo.currentUser

    fun signin(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading

            val result = repo.signin(email, password)

            if (result is Resource.Success) {
                _authState.value = AuthState.Data(result.data)
            } else if (result is Resource.Error) {
                _authState.value = AuthState.Error(result.throwable)
            }
        }
    }

    fun signup(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading

            val result = repo.signup(email, password)

            if (result is Resource.Success) {
                _authState.value = AuthState.Data(result.data)
            } else if (result is Resource.Error) {
                _authState.value = AuthState.Error(result.throwable)
            }
        }
    }

    fun logout() {

        viewModelScope.launch {
            repo.logout()
        }
    }

}

sealed interface AuthState {
    object Loading : AuthState
    data class Data(val user: FirebaseUser) : AuthState
    data class Error(val throwable: Throwable) : AuthState
}

