package com.example.travelmarket.logic.viewmodels.destinations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.domain.models.Destination
import com.example.travelmarket.logic.domain.usecases.destinations.GetDestinationByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DestinationDetailViewModel @Inject constructor(
    private val getDestinationByIdUseCase: GetDestinationByIdUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<DestinationDetailState>(DestinationDetailState.Loading)
    val state: StateFlow<DestinationDetailState> = _state.asStateFlow()

    fun loadDestination(id: Long) {
        viewModelScope.launch {
            _state.value = DestinationDetailState.Loading
            when (val result = getDestinationByIdUseCase(id)) {
                is NetworkResult.Success -> {
                    _state.value = DestinationDetailState.Success(result.data)
                }
                is NetworkResult.Error -> {
                    _state.value = DestinationDetailState.Error(result.message)
                }
                else -> {}
            }
        }
    }
}

sealed class DestinationDetailState {
    data object Loading : DestinationDetailState()
    data class Success(val destination: Destination) : DestinationDetailState()
    data class Error(val message: String) : DestinationDetailState()
}
