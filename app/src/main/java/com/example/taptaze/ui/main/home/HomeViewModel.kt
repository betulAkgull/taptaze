package com.example.taptaze.ui.main.home

import androidx.lifecycle.ViewModel
import com.example.taptaze.data.repository.AuthRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repo: AuthRepo): ViewModel() {

}