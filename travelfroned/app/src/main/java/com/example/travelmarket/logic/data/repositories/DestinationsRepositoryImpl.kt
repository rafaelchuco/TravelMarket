package com.example.travelmarket.logic.data.repositories

import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.data.mappers.DestinationMapper
import com.example.travelmarket.logic.data.models.request.destinations.CreateDestinationRequest
import com.example.travelmarket.logic.data.models.request.destinations.UpdateDestinationRequest
import com.example.travelmarket.logic.data.remote.destinations.DestinationsApiService
import com.example.travelmarket.logic.domain.models.Destination
import com.example.travelmarket.logic.domain.repositories.DestinationsRepository
import javax.inject.Inject

class DestinationsRepositoryImpl @Inject constructor(
    private val api: DestinationsApiService
) : DestinationsRepository {

    override suspend fun getDestinations(): NetworkResult<List<Destination>> {
        return try {
            val response = api.list()
            if (response.isSuccessful && response.body() != null) {
                val destinations = response.body()!!.results?.map { DestinationMapper.toDomain(it) } ?: emptyList()
                NetworkResult.Success(destinations)
            } else {
                // ✅ CORREGIDO: message primero, luego code
                NetworkResult.Error(response.message() ?: "Unknown error", response.code())
            }
        } catch (e: Exception) {
            // ✅ CORREGIDO: solo message (sin null)
            NetworkResult.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun getDestinationById(id: Long): NetworkResult<Destination> {
        return try {
            val response = api.read(id)
            if (response.isSuccessful && response.body() != null) {
                NetworkResult.Success(DestinationMapper.toDomain(response.body()!!))
            } else {
                // ✅ CORREGIDO
                NetworkResult.Error(response.message() ?: "Unknown error", response.code())
            }
        } catch (e: Exception) {
            // ✅ CORREGIDO
            NetworkResult.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun createDestination(request: CreateDestinationRequest): NetworkResult<Destination> {
        return try {
            val response = api.create(request)
            if (response.isSuccessful && response.body() != null) {
                NetworkResult.Success(DestinationMapper.toDomain(response.body()!!))
            } else {
                // ✅ CORREGIDO
                NetworkResult.Error(response.message() ?: "Unknown error", response.code())
            }
        } catch (e: Exception) {
            // ✅ CORREGIDO
            NetworkResult.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun updateDestination(id: Long, request: UpdateDestinationRequest): NetworkResult<Destination> {
        return try {
            val response = api.update(id, request)
            if (response.isSuccessful && response.body() != null) {
                NetworkResult.Success(DestinationMapper.toDomain(response.body()!!))
            } else {
                // ✅ CORREGIDO
                NetworkResult.Error(response.message() ?: "Unknown error", response.code())
            }
        } catch (e: Exception) {
            // ✅ CORREGIDO
            NetworkResult.Error(e.message ?: "Unknown error")
        }
    }

    override suspend fun deleteDestination(id: Long): NetworkResult<Unit> {
        return try {
            val response = api.delete(id)
            if (response.isSuccessful) {
                NetworkResult.Success(Unit)
            } else {
                // ✅ CORREGIDO
                NetworkResult.Error(response.message() ?: "Unknown error", response.code())
            }
        } catch (e: Exception) {
            // ✅ CORREGIDO
            NetworkResult.Error(e.message ?: "Unknown error")
        }
    }
}
