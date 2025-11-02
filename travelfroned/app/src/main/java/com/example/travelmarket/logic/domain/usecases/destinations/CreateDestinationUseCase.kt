package com.example.travelmarket.logic.domain.usecases.destinations

import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.data.models.request.destinations.CreateDestinationRequest
import com.example.travelmarket.logic.domain.models.Destination
import com.example.travelmarket.logic.domain.repositories.DestinationsRepository
import javax.inject.Inject

class CreateDestinationUseCase @Inject constructor(
    private val repository: DestinationsRepository
) {
    suspend operator fun invoke(request: CreateDestinationRequest): NetworkResult<Destination> {
        return repository.createDestination(request)
    }
}
