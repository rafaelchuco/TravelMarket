package com.example.travelmarket.logic.viewmodels.hotels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.domain.models.Hotel
import com.example.travelmarket.logic.domain.usecases.hotels.GetHotelsUseCase  // ✅ CAMBIAR AQUÍ
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HotelsListViewModel @Inject constructor(
    private val getHotelsUseCase: GetHotelsUseCase  // ✅ CAMBIAR AQUÍ
) : ViewModel() {

    private val _state = MutableStateFlow<HotelsListState>(HotelsListState.Loading)
    val state: StateFlow<HotelsListState> = _state.asStateFlow()

    init {
        loadHotels()
    }

    private fun loadHotels() {
        viewModelScope.launch {
            _state.value = HotelsListState.Loading
            when (val result = getHotelsUseCase()) {  // ✅ YA ESTÁ BIEN
                is NetworkResult.Success -> {
                    _state.value = HotelsListState.Success(result.data)
                }
                is NetworkResult.Error -> {
                    _state.value = HotelsListState.Error(result.message)
                }
                else -> {}
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
    data class Error(val message: String) : HotelsListState()
}
