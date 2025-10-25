package com.example.travelmarket.logic.viewmodels.packages

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.domain.models.Package
import com.example.travelmarket.logic.domain.usecases.packages.GetPackagesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PackagesListViewModel @Inject constructor(
    private val getPackagesUseCase: GetPackagesUseCase
) : ViewModel() {

    private val _packages = MutableStateFlow<List<Package>>(emptyList())
    val packages: StateFlow<List<Package>> = _packages

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun loadPackages() {
        viewModelScope.launch {
            _loading.value = true
            when (val result = getPackagesUseCase()) {
                is NetworkResult.Success -> {
                    _packages.value = result.data
                    _error.value = null
                }
                is NetworkResult.Error -> {
                    _error.value = result.message
                }
                is NetworkResult.Loading -> {
                    _loading.value = true
                }
            }
            _loading.value = false
        }
    }
}
