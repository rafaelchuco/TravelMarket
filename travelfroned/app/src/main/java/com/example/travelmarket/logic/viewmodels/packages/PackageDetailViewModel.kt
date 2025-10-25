package com.example.travelmarket.logic.viewmodels.packages

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.domain.models.Package
import com.example.travelmarket.logic.domain.usecases.packages.GetPackageByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PackageDetailViewModel @Inject constructor(
    private val getPackageByIdUseCase: GetPackageByIdUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<PackageDetailState>(PackageDetailState.Loading)
    val state: StateFlow<PackageDetailState> = _state.asStateFlow()

    fun loadPackage(id: Long) {
        viewModelScope.launch {
            _state.value = PackageDetailState.Loading
            when (val result = getPackageByIdUseCase(id)) {
                is NetworkResult.Success -> {
                    _state.value = PackageDetailState.Success(result.data)
                }
                is NetworkResult.Error -> {
                    _state.value = PackageDetailState.Error(result.message)
                }
                else -> {}
            }
        }
    }
}

sealed class PackageDetailState {
    data object Loading : PackageDetailState()
    data class Success(val packageData: Package) : PackageDetailState()
    data class Error(val message: String) : PackageDetailState()
}
