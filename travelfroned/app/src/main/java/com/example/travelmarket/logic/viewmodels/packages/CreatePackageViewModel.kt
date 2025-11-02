package com.example.travelmarket.logic.viewmodels.packages

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.data.models.request.packages.CreatePackageRequest
import com.example.travelmarket.logic.domain.models.Package
import com.example.travelmarket.logic.domain.usecases.packages.CreatePackageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreatePackageViewModel @Inject constructor(
    private val createPackageUseCase: CreatePackageUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<CreatePackageState>(CreatePackageState.Idle)
    val state: StateFlow<CreatePackageState> = _state.asStateFlow()

    fun createPackage(request: CreatePackageRequest) {
        viewModelScope.launch {
            _state.value = CreatePackageState.Loading
            when (val result = createPackageUseCase(request)) {
                is NetworkResult.Success -> {
                    _state.value = CreatePackageState.Success(result.data)
                }
                is NetworkResult.Error -> {
                    _state.value = CreatePackageState.Error(result.message)
                }
                else -> {}
            }
        }
    }

    fun resetState() {
        _state.value = CreatePackageState.Idle
    }
}

sealed class CreatePackageState {
    data object Idle : CreatePackageState()
    data object Loading : CreatePackageState()
    data class Success(val packageData: Package) : CreatePackageState()
    data class Error(val message: String) : CreatePackageState()
}
