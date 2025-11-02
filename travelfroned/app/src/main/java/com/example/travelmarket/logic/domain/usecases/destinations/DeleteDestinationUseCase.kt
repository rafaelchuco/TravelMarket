package com.example.travelmarket.logic.domain.usecases.destinations
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.domain.repositories.DestinationsRepository
import javax.inject.Inject

class DeleteDestinationUseCase @Inject constructor(
    private val repository: DestinationsRepository
) {
    suspend operator fun invoke(id: Long): NetworkResult<Unit> {
        return repository.deleteDestination(id)
    }
}
