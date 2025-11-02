package com.example.travelmarket.logic.domain.usecases.hotels

import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.domain.models.Hotel
import com.example.travelmarket.logic.domain.repositories.HotelsRepository
import javax.inject.Inject

class GetHotelsUseCase @Inject constructor(
    private val repository: HotelsRepository
) {
    suspend operator fun invoke(): NetworkResult<List<Hotel>> {
        return repository.getHotels()
    }
}
