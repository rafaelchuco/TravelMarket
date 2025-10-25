package com.example.travelmarket.logic.domain.usecases.flights
import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.domain.models.Flight
import com.example.travelmarket.logic.domain.repositories.FlightsRepository
import javax.inject.Inject

class GetFlightByIdUseCase @Inject constructor(
    private val repository: FlightsRepository
) {
    suspend operator fun invoke(id: Long): NetworkResult<Flight> {
        return repository.getFlightById(id)
    }
}
