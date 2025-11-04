package com.example.travelmarket.core.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelmarket.core.network.NetworkResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel<T> : ViewModel() {
    protected val _uiState = MutableStateFlow<NetworkResult<T>>(NetworkResult.Loading)
    val uiState: StateFlow<NetworkResult<T>> = _uiState.asStateFlow()

    protected fun executeOperation(operation: suspend () -> NetworkResult<T>) {
        viewModelScope.launch {
            _uiState.value = NetworkResult.Loading
            _uiState.value = operation()
        }
    }
}
