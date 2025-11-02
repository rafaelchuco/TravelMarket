package com.example.travelmarket.logic.data.remote.bookings

import com.example.travelmarket.logic.data.models.request.bookings.CreateBookingRequest
import com.example.travelmarket.logic.data.models.request.bookings.UpdateBookingRequest
import com.example.travelmarket.logic.data.models.response.bookings.*
import retrofit2.Response
import retrofit2.http.*

interface BookingsApiService {

    @GET("bookings/")
    suspend fun getBookings(
        @Query("search") search: String? = null,
        @Query("ordering") ordering: String? = null,
        @Query("page") page: Int? = null
    ): Response<BookingsApiResponse>

    @POST("bookings/")
    suspend fun createBooking(
        @Body request: CreateBookingRequest
    ): Response<CreateBookingResponse>

    @GET("bookings/my_bookings/")
    suspend fun getMyBookings(
        @Query("search") search: String? = null,
        @Query("ordering") ordering: String? = null,
        @Query("page") page: Int? = null
    ): Response<MyBookingsApiResponse>

    @GET("bookings/{id}/")
    suspend fun getBookingById(
        @Path("id") id: Int
    ): Response<BookingDetailResponse>

    @PATCH("bookings/{id}/")
    suspend fun updateBooking(
        @Path("id") id: Int,
        @Body request: UpdateBookingRequest
    ): Response<BookingDetailResponse>

    @DELETE("bookings/{id}/")
    suspend fun deleteBooking(
        @Path("id") id: Int
    ): Response<Unit>

    @PATCH("bookings/{id}/cancel/")
    suspend fun cancelBooking(
        @Path("id") id: Int,
        @Body request: UpdateBookingRequest
    ): Response<BookingDetailResponse>
}
