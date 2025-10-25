package com.example.travelmarket.logic.domain.usecases.hotels

import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.domain.models.Hotel
import com.example.travelmarket.logic.domain.repositories.HotelsRepository
import javax.inject.Inject

class GetHotelByIdUseCase @Inject constructor(
    private val repository: HotelsRepository
) {
    suspend operator fun invoke(id: Long): NetworkResult<Hotel> {
        return repository.getHotelById(id)
    }
}
