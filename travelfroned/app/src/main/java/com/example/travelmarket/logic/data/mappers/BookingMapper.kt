package com.example.travelmarket.logic.data.mappers

import com.example.travelmarket.logic.data.models.response.bookings.BookingDetailResponse
import com.example.travelmarket.logic.data.models.response.bookings.BookingResponse
import com.example.travelmarket.logic.domain.models.Booking
import com.example.travelmarket.logic.domain.models.BookingDetail

object BookingMapper {

    fun toDomain(response: BookingResponse): Booking {
        return Booking(
            id = response.id,
            bookingNumber = response.bookingNumber,
            customerId = response.customerId.toString(),
            travelDate = response.travelDate,
            returnDate = response.returnDate,
            numAdults = response.numAdults,
            numChildren = response.numChildren,
            numInfants = response.numInfants,
            totalAmount = response.totalAmount,
            status = response.status,
            paymentStatus = response.paymentStatus,
            bookingDate = response.bookingDate
        )
    }

    fun toDomainList(responseList: List<BookingResponse>): List<Booking> {
        return responseList.map { toDomain(it) }
    }

    fun detailToDomain(response: BookingDetailResponse): BookingDetail {
        return BookingDetail(
            id = response.id,
            bookingNumber = response.bookingNumber ?: "",
            travelDate = response.travelDate,
            returnDate = response.returnDate,
            numAdults = response.numAdults ?: 0,
            numChildren = response.numChildren ?: 0,
            numInfants = response.numInfants ?: 0,
            subtotal = response.subtotal ?: "0.00",
            discountAmount = response.discountAmount ?: "0.00",
            taxAmount = response.taxAmount ?: "0.00",
            totalAmount = response.totalAmount ?: "0.00",
            paidAmount = response.paidAmount ?: "0.00",
            status = response.status ?: "pending",
            paymentStatus = response.paymentStatus ?: "unpaid",
            specialRequests = response.specialRequests,
            bookingDate = response.bookingDate ?: "",
            updatedAt = response.updatedAt ?: "",
            customer = response.customer ?: 0,
            packageId = response.packageId
        )
    }

    fun detailToDomainList(responseList: List<BookingDetailResponse>): List<BookingDetail> {
        return responseList.map { detailToDomain(it) }
    }
}