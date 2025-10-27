package com.example.travelmarket.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelmarket.core.network.ApiClient
import com.example.travelmarket.data.remote.PackagesApiService
import com.example.travelmarket.domain.models.Package
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PackagesViewModel : ViewModel() {
    
    private val packagesApiService: PackagesApiService = ApiClient.retrofit.create(PackagesApiService::class.java)
    
    private val _packages = MutableStateFlow<List<Package>>(emptyList())
    val packages: StateFlow<List<Package>> = _packages.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()
    
    init {
        loadPackages()
    }
    
    fun loadPackages() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            
            try {
                val response = packagesApiService.getPackages()
                _packages.value = response
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _isLoading.value = false
            }
        }
    }
}
