package com.example.travelmarket.logic.viewmodels.bookings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.data.models.request.bookings.CreateBookingRequest
import com.example.travelmarket.logic.domain.models.Booking
import com.example.travelmarket.logic.domain.usecases.bookings.CreateBookingUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate

class CreateBookingViewModel(
    private val createBookingUseCase: CreateBookingUseCase
) : ViewModel() {

    private val _createBookingState = MutableStateFlow<NetworkResult<Booking>?>(null)
    val createBookingState: StateFlow<NetworkResult<Booking>?> = _createBookingState.asStateFlow()

    fun createBooking(
        packageId: Int,
        travelDate: String,
        returnDate: String?,
        numAdults: Int,
        numChildren: Int,
        numInfants: Int,
        totalAmount: Double,  // ✅ AGREGADO
        specialRequests: String?
    ) {
        viewModelScope.launch {
            // RF-065: validar travel_date < return_date
            try {
                if (!returnDate.isNullOrEmpty()) {
                    val t = LocalDate.parse(travelDate)
                    val r = LocalDate.parse(returnDate)
                    if (!t.isBefore(r)) {
                        _createBookingState.value = NetworkResult.Error("La fecha de ida debe ser menor que la de retorno")
                        return@launch
                    }
                }
            } catch (e: Exception) {
                _createBookingState.value = NetworkResult.Error("Formato de fecha inválido (yyyy-MM-dd)")
                return@launch
            }

            _createBookingState.value = NetworkResult.Loading

            // Cálculos base RF-067, RF-069, RF-070 (sin cupón):
            val subtotal = 0.0  // se calculará en Paso 3 con precios reales
            val discountAmount = 0.0
            val taxAmount = (subtotal - discountAmount) * 0.18
            val total = if (totalAmount > 0) totalAmount else subtotal - discountAmount + taxAmount

            val request = CreateBookingRequest(
                packageId = packageId,
                travelDate = travelDate,
                returnDate = returnDate,
                numAdults = numAdults,
                numChildren = numChildren,
                numInfants = numInfants,
                totalAmount = total,  // usar total calculado si no viene
                subtotal = subtotal,
                discountAmount = discountAmount,
                taxAmount = taxAmount,
                specialRequests = specialRequests
            )

            _createBookingState.value = createBookingUseCase(request)
        }
    }

    fun resetCreateState() {
        _createBookingState.value = null
    }
}