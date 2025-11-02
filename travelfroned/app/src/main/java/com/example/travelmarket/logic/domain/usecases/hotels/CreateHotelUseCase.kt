package com.example.travelmarket.logic.domain.usecases.hotels

import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.data.models.request.hotels.CreateHotelRequest
import com.example.travelmarket.logic.domain.models.Hotel
import com.example.travelmarket.logic.domain.repositories.HotelsRepository
import javax.inject.Inject

class CreateHotelUseCase @Inject constructor(
    private val repository: HotelsRepository
) {
    suspend operator fun invoke(request: CreateHotelRequest): NetworkResult<Hotel> {
        return repository.createHotel(request)
    }
}
