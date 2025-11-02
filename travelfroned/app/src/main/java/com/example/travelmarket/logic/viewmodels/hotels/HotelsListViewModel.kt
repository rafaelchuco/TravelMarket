package com.example.travelmarket.logic.viewmodels.hotels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.domain.models.Hotel
import com.example.travelmarket.logic.domain.usecases.hotels.GetHotelsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HotelsListViewModel @Inject constructor(
    private val getHotelsUseCase: GetHotelsUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<HotelsListState>(HotelsListState.Loading)
    val state: StateFlow<HotelsListState> = _state.asStateFlow()

    init {
        loadHotels()
    }

    fun loadHotels() {  // ✅ CAMBIÉ A PÚBLICO para poder llamarlo desde la UI
        viewModelScope.launch {
            _state.value = HotelsListState.Loading
            when (val result = getHotelsUseCase()) {
                is NetworkResult.Success -> {
                    _state.value = HotelsListState.Success(result.data)
                }
                is NetworkResult.Error -> {
                    // ✅ CONVERSIÓN SEGURA A STRING
                    val errorMessage = (result.message as? String)
                        ?: result.message?.toString()
                        ?: "Error al cargar hoteles"
                    _state.value = HotelsListState.Error(errorMessage)
                }
                NetworkResult.Loading -> {
                    _state.value = HotelsListState.Loading
                }
            }
        }
    }

    fun retry() {
        loadHotels()
    }
}

sealed class HotelsListState {
    data object Loading : HotelsListState()
    data class Success(val hotels: List<Hotel>) : HotelsListState()
    data class Error(val message: String) : HotelsListState()  // ✅ String, NO Int?
}
