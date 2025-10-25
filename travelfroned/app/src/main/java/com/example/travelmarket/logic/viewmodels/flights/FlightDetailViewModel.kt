package com.example.travelmarket.logic.viewmodels.flights

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.domain.models.Flight
import com.example.travelmarket.logic.domain.usecases.flights.GetFlightByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FlightDetailViewModel @Inject constructor(
    private val getFlightByIdUseCase: GetFlightByIdUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<FlightDetailState>(FlightDetailState.Loading)
    val state: StateFlow<FlightDetailState> = _state.asStateFlow()

    fun loadFlight(id: Long) {
        viewModelScope.launch {
            _state.value = FlightDetailState.Loading
            when (val result = getFlightByIdUseCase(id)) {
                is NetworkResult.Success -> {
                    _state.value = FlightDetailState.Success(result.data)
                }
                is NetworkResult.Error -> {
                    _state.value = FlightDetailState.Error(result.message)
                }
                else -> {}
            }
        }
    }
}

sealed class FlightDetailState {
    data object Loading : FlightDetailState()
    data class Success(val flight: Flight) : FlightDetailState()
    data class Error(val message: String) : FlightDetailState()
}
