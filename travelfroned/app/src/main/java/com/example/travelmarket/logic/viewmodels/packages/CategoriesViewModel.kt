package com.example.travelmarket.logic.viewmodels.packages

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.domain.models.PackageCategory
import com.example.travelmarket.logic.domain.usecases.packages.GetCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<CategoriesState>(CategoriesState.Loading)
    val state: StateFlow<CategoriesState> = _state.asStateFlow()

    init {
        loadCategories()
    }

    fun loadCategories() {
        viewModelScope.launch {
            _state.value = CategoriesState.Loading
            when (val result = getCategoriesUseCase()) {
                is NetworkResult.Success -> {
                    _state.value = CategoriesState.Success(result.data)
                }
                is NetworkResult.Error -> {
                    // ✅ FORZAR CONVERSIÓN A STRING
                    val errorMessage = (result.message as? String) ?: result.message?.toString() ?: "Error desconocido"
                    _state.value = CategoriesState.Error(errorMessage)
                }
                NetworkResult.Loading -> {
                    _state.value = CategoriesState.Loading
                }
            }
        }
    }
}

sealed class CategoriesState {
    data object Loading : CategoriesState()
    data class Success(val categories: List<PackageCategory>) : CategoriesState()
    data class Error(val message: String) : CategoriesState()  // ✅ String
}
