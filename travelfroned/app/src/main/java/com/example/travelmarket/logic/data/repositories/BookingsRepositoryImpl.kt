package com.example.travelmarket.logic.data.repositories

import com.example.travelmarket.core.base.BaseRepository
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.data.mappers.BookingMapper
import com.example.travelmarket.logic.data.models.request.bookings.CreateBookingRequest
import com.example.travelmarket.logic.data.models.request.bookings.UpdateBookingRequest
import com.example.travelmarket.logic.data.models.response.bookings.BookingDetailResponse
import com.example.travelmarket.logic.data.models.response.bookings.BookingResponse
import com.example.travelmarket.logic.data.remote.bookings.BookingsApiService
import com.example.travelmarket.logic.domain.models.Booking
import com.example.travelmarket.logic.domain.models.BookingDetail
import com.example.travelmarket.logic.domain.repositories.BookingsRepository
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class BookingsRepositoryImpl(
    private val apiService: BookingsApiService
) : BaseRepository(), BookingsRepository {

    @Suppress("UNCHECKED_CAST", "USELESS_IS_CHECK")
    override suspend fun getBookings(
        search: String?,
        ordering: String?,
        page: Int?
    ): NetworkResult<List<Booking>> {
        val result = executeApiCall {
            apiService.getBookings(
                search = search,
                ordering = ordering,
                page = page
            )
        }

        return when (result) {
            is NetworkResult.Success -> {
                // ✅ Parsear manualmente
                val bookingResponses: List<BookingResponse> = try {
                    val items = result.data.results

                    if (items.isNotEmpty() && items.first() is Map<*, *>) {
                        val gson = Gson()
                        val json = gson.toJson(items)
                        val type = object : TypeToken<List<BookingResponse>>() {}.type
                        gson.fromJson(json, type)
                    } else {
                        items as List<BookingResponse>
                    }
                } catch (e: Exception) {
                    android.util.Log.e("BOOKINGS_REPO", "Error parsing: ${e.message}")
                    emptyList()
                }

                val bookings = BookingMapper.toDomainList(bookingResponses)
                NetworkResult.Success(bookings)
            }
            is NetworkResult.Error -> NetworkResult.Error(result.message, result.code)
            is NetworkResult.Loading -> NetworkResult.Loading
        }
    }

    override suspend fun createBooking(
        request: CreateBookingRequest
    ): NetworkResult<Booking> {
        val result = executeApiCall {
            apiService.createBooking(request)
        }

        return when (result) {
            is NetworkResult.Success -> {
                // ✅ Extraer booking del wrapper
                val bookingDetail = result.data.booking

                // Convertir a Booking
                val booking = Booking(
                    id = bookingDetail.id,
                    bookingNumber = bookingDetail.bookingNumber ?: "",
                    customerId = bookingDetail.customer.toString(),  // ✅ CORREGIDO
                    travelDate = bookingDetail.travelDate,
                    returnDate = bookingDetail.returnDate,
                    numAdults = bookingDetail.numAdults ?: 0,
                    numChildren = bookingDetail.numChildren ?: 0,
                    numInfants = bookingDetail.numInfants ?: 0,
                    totalAmount = bookingDetail.totalAmount ?: "0.00",
                    status = bookingDetail.status ?: "pending",
                    paymentStatus = bookingDetail.paymentStatus ?: "unpaid",
                    bookingDate = bookingDetail.bookingDate ?: ""
                )
                NetworkResult.Success(booking)
            }
            is NetworkResult.Error -> NetworkResult.Error(result.message, result.code)
            is NetworkResult.Loading -> NetworkResult.Loading
        }
    }

    @Suppress("UNCHECKED_CAST", "USELESS_IS_CHECK")
    override suspend fun getMyBookings(
        search: String?,
        ordering: String?,
        page: Int?
    ): NetworkResult<List<BookingDetail>> {
        val result = executeApiCall {
            apiService.getMyBookings(
                search = search,
                ordering = ordering,
                page = page
            )
        }

        return when (result) {
            is NetworkResult.Success -> {
                // ✅ Parsear manualmente
                val bookingDetailResponses: List<BookingDetailResponse> = try {
                    val items = result.data.results

                    if (items.isNotEmpty() && items.first() is Map<*, *>) {
                        val gson = Gson()
                        val json = gson.toJson(items)
                        val type = object : TypeToken<List<BookingDetailResponse>>() {}.type
                        gson.fromJson(json, type)
                    } else {
                        items as List<BookingDetailResponse>
                    }
                } catch (e: Exception) {
                    android.util.Log.e("BOOKINGS_REPO", "Error parsing: ${e.message}")
                    emptyList()
                }

                val bookings = BookingMapper.detailToDomainList(bookingDetailResponses)
                NetworkResult.Success(bookings)
            }
            is NetworkResult.Error -> NetworkResult.Error(result.message, result.code)
            is NetworkResult.Loading -> NetworkResult.Loading
        }
    }

    override suspend fun getBookingById(id: Int): NetworkResult<BookingDetail> {
        val result = executeApiCall {
            apiService.getBookingById(id)
        }

        return when (result) {
            is NetworkResult.Success -> {
                val booking = BookingMapper.detailToDomain(result.data)
                NetworkResult.Success(booking)
            }
            is NetworkResult.Error -> NetworkResult.Error(result.message, result.code)
            is NetworkResult.Loading -> NetworkResult.Loading
        }
    }

    override suspend fun updateBooking(
        id: Int,
        request: UpdateBookingRequest
    ): NetworkResult<BookingDetail> {
        val result = executeApiCall {
            apiService.updateBooking(id, request)
        }

        return when (result) {
            is NetworkResult.Success -> {
                val booking = BookingMapper.detailToDomain(result.data)
                NetworkResult.Success(booking)
            }
            is NetworkResult.Error -> NetworkResult.Error(result.message, result.code)
            is NetworkResult.Loading -> NetworkResult.Loading
        }
    }

    override suspend fun deleteBooking(id: Int): NetworkResult<Unit> {
        return executeApiCall {
            apiService.deleteBooking(id)
        }
    }

    override suspend fun cancelBooking(id: Int): NetworkResult<BookingDetail> {
        val request = UpdateBookingRequest(
            bookingNumber = null,
            travelDate = null,
            returnDate = null,
            numAdults = null,
            numChildren = null,
            numInfants = null,
            subtotal = null,
            discountAmount = null,
            taxAmount = null,
            totalAmount = null,
            paidAmount = null,
            status = "cancelled",
            paymentStatus = null,
            specialRequests = null,
            customer = null,
            packageId = null
        )

        val result = executeApiCall {
            apiService.cancelBooking(id, request)
        }

        return when (result) {
            is NetworkResult.Success -> {
                val booking = BookingMapper.detailToDomain(result.data)
                NetworkResult.Success(booking)
            }
            is NetworkResult.Error -> NetworkResult.Error(result.message, result.code)
            is NetworkResult.Loading -> NetworkResult.Loading
        }
    }
}
