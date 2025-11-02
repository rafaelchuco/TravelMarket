package com.example.travelmarket.logic.domain.usecases.flights

import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.domain.repositories.FlightsRepository
import javax.inject.Inject

class DeleteFlightUseCase @Inject constructor(
    private val repository: FlightsRepository
) {
    suspend operator fun invoke(id: Long): NetworkResult<Unit> {
        return repository.deleteFlight(id)
    }
}
