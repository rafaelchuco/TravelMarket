package com.example.travelmarket.data.remote

import com.example.travelmarket.data.models.BookingsApiResponse
import com.example.travelmarket.domain.models.Booking
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BookingsApiService {
    @GET("bookings/")
    suspend fun getMyBookings(
        @Query("page") page: Int = 1,
        @Query("page_size") pageSize: Int = 100
    ): Response<BookingsApiResponse>
    
    @GET("bookings/{id}/")
    suspend fun getBookingById(@Path("id") id: Int): Response<Booking>
}
