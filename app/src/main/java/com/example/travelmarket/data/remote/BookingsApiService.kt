package com.example.travelmarket.data.remote

import com.example.travelmarket.domain.models.Booking
import retrofit2.http.GET
import retrofit2.http.Query

interface BookingsApiService {
    @GET("bookings/")
    suspend fun getMyBookings(): List<Booking>
    
    @GET("bookings/{id}/")
    suspend fun getBookingById(id: Int): Booking
}
