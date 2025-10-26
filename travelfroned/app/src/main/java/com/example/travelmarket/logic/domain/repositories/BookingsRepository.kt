package com.example.travelmarket.logic.domain.repositories

import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.data.models.request.bookings.CreateBookingRequest
import com.example.travelmarket.logic.data.models.request.bookings.UpdateBookingRequest
import com.example.travelmarket.logic.domain.models.Booking
import com.example.travelmarket.logic.domain.models.BookingDetail

interface BookingsRepository {

    suspend fun getBookings(
        search: String? = null,
        ordering: String? = null,
        page: Int? = null
    ): NetworkResult<List<Booking>>

    // ✅ CAMBIADO: Ahora recibe CreateBookingRequest
    suspend fun createBooking(
        request: CreateBookingRequest
    ): NetworkResult<Booking>

    suspend fun getMyBookings(
        search: String? = null,
        ordering: String? = null,
        page: Int? = null
    ): NetworkResult<List<BookingDetail>>

    suspend fun getBookingById(id: Int): NetworkResult<BookingDetail>

    // ✅ PUEDES DEJARLO ASÍ O CREAR UpdateBookingRequest TAMBIÉN
    suspend fun updateBooking(
        id: Int,
        request: UpdateBookingRequest
    ): NetworkResult<BookingDetail>

    suspend fun deleteBooking(id: Int): NetworkResult<Unit>

    suspend fun cancelBooking(id: Int): NetworkResult<BookingDetail>
}
