package com.example.travelmarket.logic.data.repositories

import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.data.mappers.FlightMapper
import com.example.travelmarket.logic.data.models.request.flights.CreateFlightRequest
import com.example.travelmarket.logic.data.remote.flights.FlightsApiService
import com.example.travelmarket.logic.domain.models.Flight
import com.example.travelmarket.logic.domain.repositories.FlightsRepository
import javax.inject.Inject

class FlightsRepositoryImpl @Inject constructor(
    private val api: FlightsApiService
) : FlightsRepository {

    override suspend fun getFlights(): NetworkResult<List<Flight>> {
        return try {
            val response = api.list()
            if (response.isSuccessful && response.body() != null) {
                // ✅ CAMBIADO: usar .results
                val flights = response.body()!!.results?.map { FlightMapper.toDomain(it) } ?: emptyList()
                NetworkResult.Success(flights)
            } else {
                NetworkResult.Error(response.code(), response.message())
            }
        } catch (e: Exception) {
            NetworkResult.Error(null, e.message ?: "Unknown error")
        }
    }

    override suspend fun getFlightById(id: Long): NetworkResult<Flight> {
        return try {
            val response = api.read(id)
            if (response.isSuccessful && response.body() != null) {
                NetworkResult.Success(FlightMapper.toDomain(response.body()!!))
            } else {
                NetworkResult.Error(response.code(), response.message())
            }
        } catch (e: Exception) {
            NetworkResult.Error(null, e.message ?: "Unknown error")
        }
    }

    override suspend fun createFlight(request: CreateFlightRequest): NetworkResult<Flight> {
        return try {
            val response = api.create(request)
            if (response.isSuccessful && response.body() != null) {
                NetworkResult.Success(FlightMapper.toDomain(response.body()!!))
            } else {
                NetworkResult.Error(response.code(), response.message())
            }
        } catch (e: Exception) {
            NetworkResult.Error(null, e.message ?: "Unknown error")
        }
    }

    override suspend fun updateFlight(id: Long, request: CreateFlightRequest): NetworkResult<Flight> {
        return try {
            val response = api.update(id, request)
            if (response.isSuccessful && response.body() != null) {
                NetworkResult.Success(FlightMapper.toDomain(response.body()!!))
            } else {
                NetworkResult.Error(response.code(), response.message())
            }
        } catch (e: Exception) {
            NetworkResult.Error(null, e.message ?: "Unknown error")
        }
    }

    override suspend fun deleteFlight(id: Long): NetworkResult<Unit> {
        return try {
            val response = api.delete(id)
            if (response.isSuccessful) {
                NetworkResult.Success(Unit)
            } else {
                NetworkResult.Error(response.code(), response.message())
            }
        } catch (e: Exception) {
            NetworkResult.Error(null, e.message ?: "Unknown error")
        }
    }
}
