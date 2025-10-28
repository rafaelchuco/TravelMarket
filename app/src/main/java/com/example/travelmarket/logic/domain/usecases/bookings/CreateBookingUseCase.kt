package com.example.travelmarket.logic.domain.usecases.bookings

import com.example.travelmarket.core.base.BaseUseCaseWithParams
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.domain.models.Booking
import com.example.travelmarket.logic.domain.repositories.BookingsRepository

data class CreateBookingParams(
    val bookingNumber: String?,
    val packageId: Int?,
    val travelDate: String?,
    val returnDate: String?,
    val numAdults: Int?,
    val numChildren: Int?,
    val numInfants: Int?,
    val subtotal: String?,
    val discountAmount: String?,
    val taxAmount: String?,
    val totalAmount: String?,
    val paidAmount: String?,
    val status: String?,
    val paymentStatus: String?,
    val specialRequests: String?
)

class CreateBookingUseCase(
    private val repository: BookingsRepository
) : BaseUseCaseWithParams<CreateBookingParams, NetworkResult<Booking>>() {

    override suspend fun invoke(params: CreateBookingParams): NetworkResult<Booking> {
        return repository.createBooking(
            bookingNumber = params.bookingNumber,
            packageId = params.packageId,
            travelDate = params.travelDate,
            returnDate = params.returnDate,
            numAdults = params.numAdults,
            numChildren = params.numChildren,
            numInfants = params.numInfants,
            subtotal = params.subtotal,
            discountAmount = params.discountAmount,
            taxAmount = params.taxAmount,
            totalAmount = params.totalAmount,
            paidAmount = params.paidAmount,
            status = params.status,
            paymentStatus = params.paymentStatus,
            specialRequests = params.specialRequests
        )
    }
}