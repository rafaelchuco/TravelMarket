package com.example.travelmarket.logic.domain.usecases.destinations

import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.data.models.request.destinations.UpdateDestinationRequest
import com.example.travelmarket.logic.domain.models.Destination
import com.example.travelmarket.logic.domain.repositories.DestinationsRepository
import javax.inject.Inject

class UpdateDestinationUseCase @Inject constructor(
    private val repository: DestinationsRepository
) {
    suspend operator fun invoke(id: Long, request: UpdateDestinationRequest): NetworkResult<Destination> {
        return repository.updateDestination(id, request)
    }
}
