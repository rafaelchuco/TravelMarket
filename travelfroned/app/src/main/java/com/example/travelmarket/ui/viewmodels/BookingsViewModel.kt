package com.example.travelmarket.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelmarket.core.network.ApiClient
import com.example.travelmarket.data.remote.BookingsApiService
import com.example.travelmarket.domain.models.Booking
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BookingsViewModel : ViewModel() {
    
    private val bookingsApiService: BookingsApiService = ApiClient.retrofit.create(BookingsApiService::class.java)
    
    private val _bookings = MutableStateFlow<List<Booking>>(emptyList())
    val bookings: StateFlow<List<Booking>> = _bookings.asStateFlow()
    
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()
    
    init {
        loadBookings()
    }
    
    fun loadBookings() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            
            try {
                val response = bookingsApiService.getMyBookings()
                if (response.isSuccessful && response.body() != null) {
                    _bookings.value = response.body()!!.results
                } else {
                    _error.value = "Error al cargar reservas: ${response.code()}"
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "Error desconocido"
            } finally {
                _isLoading.value = false
            }
        }
    }
    
    val upcomingBookings: StateFlow<List<Booking>> = _bookings.asStateFlow()
    val pastBookings: StateFlow<List<Booking>> = _bookings.asStateFlow()
    val cancelledBookings: StateFlow<List<Booking>> = _bookings.asStateFlow()
}
