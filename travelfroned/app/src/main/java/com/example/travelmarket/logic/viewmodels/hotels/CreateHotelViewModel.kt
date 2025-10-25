package com.example.travelmarket.logic.viewmodels.hotels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.data.models.request.hotels.CreateHotelRequest
import com.example.travelmarket.logic.domain.models.Hotel
import com.example.travelmarket.logic.domain.usecases.hotels.CreateHotelUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateHotelViewModel @Inject constructor(
    private val createHotelUseCase: CreateHotelUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<CreateHotelState>(CreateHotelState.Idle)
    val state: StateFlow<CreateHotelState> = _state.asStateFlow()

    fun createHotel(request: CreateHotelRequest) {
        viewModelScope.launch {
            _state.value = CreateHotelState.Loading
            when (val result = createHotelUseCase(request)) {
                is NetworkResult.Success -> {
                    _state.value = CreateHotelState.Success(result.data)
                }
                is NetworkResult.Error -> {
                    _state.value = CreateHotelState.Error(result.message)
                }
                else -> {}
            }
        }
    }

    fun resetState() {
        _state.value = CreateHotelState.Idle
    }
}

sealed class CreateHotelState {
    data object Idle : CreateHotelState()
    data object Loading : CreateHotelState()
    data class Success(val hotel: Hotel) : CreateHotelState()
    data class Error(val message: String) : CreateHotelState()
}
