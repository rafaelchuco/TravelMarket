package com.example.travelmarket.logic.domain.usecases.flights

import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.data.models.request.flights.CreateFlightRequest
import com.example.travelmarket.logic.domain.models.Flight
import com.example.travelmarket.logic.domain.repositories.FlightsRepository
import javax.inject.Inject

class CreateFlightUseCase @Inject constructor(
    private val repository: FlightsRepository
) {
    suspend operator fun invoke(request: CreateFlightRequest): NetworkResult<Flight> {
        return repository.createFlight(request)
    }
}
