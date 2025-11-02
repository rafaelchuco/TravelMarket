package com.example.travelmarket.logic.viewmodels.hotels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.domain.models.Hotel
import com.example.travelmarket.logic.domain.usecases.hotels.GetHotelByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HotelDetailViewModel @Inject constructor(
    private val getHotelByIdUseCase: GetHotelByIdUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<HotelDetailState>(HotelDetailState.Loading)
    val state: StateFlow<HotelDetailState> = _state.asStateFlow()

    fun loadHotel(id: Long) {
        viewModelScope.launch {
            _state.value = HotelDetailState.Loading
            when (val result = getHotelByIdUseCase(id)) {
                is NetworkResult.Success -> {
                    _state.value = HotelDetailState.Success(result.data)
                }
                is NetworkResult.Error -> {
                    _state.value = HotelDetailState.Error(result.message)
                }
                else -> {}
            }
        }
    }
}

sealed class HotelDetailState {
    data object Loading : HotelDetailState()
    data class Success(val hotel: Hotel) : HotelDetailState()
    data class Error(val message: String) : HotelDetailState()
}
