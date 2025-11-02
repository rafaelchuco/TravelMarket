package com.example.travelmarket.logic.viewmodels.flights

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.data.models.request.flights.CreateFlightRequest
import com.example.travelmarket.logic.domain.models.Flight
import com.example.travelmarket.logic.domain.usecases.flights.CreateFlightUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateFlightViewModel @Inject constructor(
    private val createFlightUseCase: CreateFlightUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<CreateFlightState>(CreateFlightState.Idle)
    val state: StateFlow<CreateFlightState> = _state.asStateFlow()

    fun createFlight(request: CreateFlightRequest) {
        viewModelScope.launch {
            _state.value = CreateFlightState.Loading
            when (val result = createFlightUseCase(request)) {
                is NetworkResult.Success -> {
                    _state.value = CreateFlightState.Success(result.data)
                }
                is NetworkResult.Error -> {
                    _state.value = CreateFlightState.Error(result.message)
                }
                else -> {}
            }
        }
    }

    fun resetState() {
        _state.value = CreateFlightState.Idle
    }
}

sealed class CreateFlightState {
    data object Idle : CreateFlightState()
    data object Loading : CreateFlightState()
    data class Success(val flight: Flight) : CreateFlightState()
    data class Error(val message: String) : CreateFlightState()
}
