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
            customerId = response.customerId,
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
            bookingNumber = response.bookingNumber,
            travelDate = response.travelDate,
            returnDate = response.returnDate,
            numAdults = response.numAdults,
            numChildren = response.numChildren,
            numInfants = response.numInfants,
            subtotal = response.subtotal,
            discountAmount = response.discountAmount,
            taxAmount = response.taxAmount,
            totalAmount = response.totalAmount,
            paidAmount = response.paidAmount,
            status = response.status,
            paymentStatus = response.paymentStatus,
            specialRequests = response.specialRequests,
            bookingDate = response.bookingDate,
            updatedAt = response.updatedAt,
            customer = response.customer,
            packageId = response.packageId
        )
    }

    fun detailToDomainList(responseList: List<BookingDetailResponse>): List<BookingDetail> {
        return responseList.map { detailToDomain(it) }
    }
}