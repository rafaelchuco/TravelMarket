package com.example.travelmarket.logic.viewmodels.flights

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.domain.models.Flight
import com.example.travelmarket.logic.domain.usecases.flights.GetFlightsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FlightsListViewModel @Inject constructor(
    private val getFlightsUseCase: GetFlightsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<FlightsListState>(FlightsListState.Loading)
    val state: StateFlow<FlightsListState> = _state.asStateFlow()

    init {
        loadFlights()
    }

    fun loadFlights() {
        viewModelScope.launch {
            _state.value = FlightsListState.Loading
            when (val result = getFlightsUseCase()) {
                is NetworkResult.Success -> {
                    _state.value = FlightsListState.Success(result.data)
                }
                is NetworkResult.Error -> {
                    _state.value = FlightsListState.Error(result.message)
                }
                else -> {}
            }
        }
    }
}

sealed class FlightsListState {
    data object Loading : FlightsListState()
    data class Success(val flights: List<Flight>) : FlightsListState()
    data class Error(val message: String) : FlightsListState()
}
