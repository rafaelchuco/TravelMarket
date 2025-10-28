package com.example.travelmarket.logic.domain.usecases.bookings

import com.example.travelmarket.core.base.BaseUseCaseWithParams
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.domain.models.BookingDetail
import com.example.travelmarket.logic.domain.repositories.BookingsRepository

data class UpdateBookingParams(
    val id: Int,
    val bookingNumber: String?,
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
    val specialRequests: String?,
    val customer: Int?,
    val packageId: Int?
)

class UpdateBookingUseCase(
    private val repository: BookingsRepository
) : BaseUseCaseWithParams<UpdateBookingParams, NetworkResult<BookingDetail>>() {

    override suspend fun invoke(params: UpdateBookingParams): NetworkResult<BookingDetail> {
        return repository.updateBooking(
            id = params.id,
            bookingNumber = params.bookingNumber,
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
            specialRequests = params.specialRequests,
            customer = params.customer,
            packageId = params.packageId
        )
    }
}