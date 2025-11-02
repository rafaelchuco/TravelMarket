package com.example.travelmarket.logic.domain.usecases.hotels

import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.domain.repositories.HotelsRepository
import javax.inject.Inject

class DeleteHotelUseCase @Inject constructor(
    private val repository: HotelsRepository
) {
    suspend operator fun invoke(id: Long): NetworkResult<Unit> {
        return repository.deleteHotel(id)
    }
}
