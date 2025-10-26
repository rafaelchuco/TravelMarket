package com.example.travelmarket.logic.data.remote.bookings

import com.example.travelmarket.core.network.PaginatedResponse
import com.example.travelmarket.logic.data.models.request.bookings.CreateBookingRequest
import com.example.travelmarket.logic.data.models.request.bookings.UpdateBookingRequest
import com.example.travelmarket.logic.data.models.response.bookings.BookingDetailResponse
import com.example.travelmarket.logic.data.models.response.bookings.BookingResponse
import com.example.travelmarket.logic.data.models.response.bookings.CreateBookingResponse  // ✅ NUEVO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface BookingsApiService {

    @GET("bookings/")
    suspend fun getBookings(
        @Query("search") search: String? = null,
        @Query("ordering") ordering: String? = null,
        @Query("page") page: Int? = null
    ): Response<PaginatedResponse<BookingResponse>>

    @POST("bookings/")
    suspend fun createBooking(
        @Body request: CreateBookingRequest
    ): Response<CreateBookingResponse>  // ✅ CAMBIO

    @GET("bookings/my_bookings/")
    suspend fun getMyBookings(
        @Query("search") search: String? = null,
        @Query("ordering") ordering: String? = null,
        @Query("page") page: Int? = null
    ): Response<PaginatedResponse<BookingDetailResponse>>

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
