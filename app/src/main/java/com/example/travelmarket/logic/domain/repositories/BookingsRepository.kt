package com.example.travelmarket.logic.domain.repositories

import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.domain.models.Booking
import com.example.travelmarket.logic.domain.models.BookingDetail

interface BookingsRepository {

    suspend fun getBookings(
        search: String? = null,
        ordering: String? = null,
        page: Int? = null
    ): NetworkResult<List<Booking>>

    suspend fun createBooking(
        bookingNumber: String?,
        packageId: Int?,
        travelDate: String?,
        returnDate: String?,
        numAdults: Int?,
        numChildren: Int?,
        numInfants: Int?,
        subtotal: String?,
        discountAmount: String?,
        taxAmount: String?,
        totalAmount: String?,
        paidAmount: String?,
        status: String?,
        paymentStatus: String?,
        specialRequests: String?
    ): NetworkResult<Booking>

    suspend fun getMyBookings(
        search: String? = null,
        ordering: String? = null,
        page: Int? = null
    ): NetworkResult<List<BookingDetail>>

    suspend fun getBookingById(id: Int): NetworkResult<BookingDetail>

    suspend fun updateBooking(
        id: Int,
        bookingNumber: String?,
        travelDate: String?,
        returnDate: String?,
        numAdults: Int?,
        numChildren: Int?,
        numInfants: Int?,
        subtotal: String?,
        discountAmount: String?,
        taxAmount: String?,
        totalAmount: String?,
        paidAmount: String?,
        status: String?,
        paymentStatus: String?,
        specialRequests: String?,
        customer: Int?,
        packageId: Int?
    ): NetworkResult<BookingDetail>

    suspend fun deleteBooking(id: Int): NetworkResult<Unit>

    suspend fun cancelBooking(id: Int): NetworkResult<BookingDetail>
}