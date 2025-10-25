package com.example.travelmarket.logic.viewmodels.destinations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.data.models.request.destinations.CreateDestinationRequest
import com.example.travelmarket.logic.domain.models.Destination
import com.example.travelmarket.logic.domain.usecases.destinations.CreateDestinationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateDestinationViewModel @Inject constructor(
    private val createDestinationUseCase: CreateDestinationUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<CreateDestinationState>(CreateDestinationState.Idle)
    val state: StateFlow<CreateDestinationState> = _state.asStateFlow()

    fun createDestination(request: CreateDestinationRequest) {
        viewModelScope.launch {
            _state.value = CreateDestinationState.Loading
            when (val result = createDestinationUseCase(request)) {
                is NetworkResult.Success -> {
                    _state.value = CreateDestinationState.Success(result.data)
                }
                is NetworkResult.Error -> {
                    _state.value = CreateDestinationState.Error(result.message)
                }
                else -> {}
            }
        }
    }

    fun resetState() {
        _state.value = CreateDestinationState.Idle
    }
}

sealed class CreateDestinationState {
    data object Idle : CreateDestinationState()
    data object Loading : CreateDestinationState()
    data class Success(val destination: Destination) : CreateDestinationState()
    data class Error(val message: String) : CreateDestinationState()
}
