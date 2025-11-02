package com.example.travelmarket.logic.domain.usecases.destinations

import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.domain.models.Destination
import com.example.travelmarket.logic.domain.repositories.DestinationsRepository
import javax.inject.Inject

class GetDestinationByIdUseCase @Inject constructor(
    private val repository: DestinationsRepository
) {
    suspend operator fun invoke(id: Long): NetworkResult<Destination> {
        return repository.getDestinationById(id)
    }
}
