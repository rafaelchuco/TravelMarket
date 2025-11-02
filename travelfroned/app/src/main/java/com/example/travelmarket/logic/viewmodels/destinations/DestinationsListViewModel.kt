package com.example.travelmarket.logic.viewmodels.destinations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.domain.models.Destination
import com.example.travelmarket.logic.domain.usecases.destinations.GetDestinationsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DestinationsListViewModel @Inject constructor(
    private val getDestinationsUseCase: GetDestinationsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<DestinationsListState>(DestinationsListState.Loading)
    val state: StateFlow<DestinationsListState> = _state.asStateFlow()

    init {
        loadDestinations()
    }

    fun loadDestinations() {
        viewModelScope.launch {
            _state.value = DestinationsListState.Loading
            when (val result = getDestinationsUseCase()) {
                is NetworkResult.Success -> {
                    _state.value = DestinationsListState.Success(result.data)
                }
                is NetworkResult.Error -> {
                    // ✅ CONVERSIÓN SEGURA A STRING
                    val errorMessage = (result.message as? String)
                        ?: result.message?.toString()
                        ?: "Error al cargar destinos"
                    _state.value = DestinationsListState.Error(errorMessage)
                }
                NetworkResult.Loading -> {
                    _state.value = DestinationsListState.Loading
                }
            }
        }
    }
}

sealed class DestinationsListState {
    data object Loading : DestinationsListState()
    data class Success(val destinations: List<Destination>) : DestinationsListState()
    data class Error(val message: String) : DestinationsListState()  // ✅ String, NO Any
}
