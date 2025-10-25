package com.example.travelmarket.logic.data.repositories

import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.data.mappers.HotelMapper
import com.example.travelmarket.logic.data.models.request.hotels.CreateHotelRequest
import com.example.travelmarket.logic.data.remote.hotels.HotelsApiService
import com.example.travelmarket.logic.domain.models.Hotel
import com.example.travelmarket.logic.domain.repositories.HotelsRepository
import javax.inject.Inject

class HotelsRepositoryImpl @Inject constructor(
    private val api: HotelsApiService
) : HotelsRepository {

    override suspend fun getHotels(): NetworkResult<List<Hotel>> {
        return try {
            val response = api.list()
            if (response.isSuccessful && response.body() != null) {
                // ✅ CAMBIADO: .data?.map → .results?.map
                val hotels = response.body()!!.results?.map { HotelMapper.toDomain(it) } ?: emptyList()
                NetworkResult.Success(hotels)
            } else {
                NetworkResult.Error(response.code(), response.message())
            }
        } catch (e: Exception) {
            NetworkResult.Error(null, e.message ?: "Unknown error")
        }
    }

    override suspend fun getHotelById(id: Long): NetworkResult<Hotel> {
        return try {
            val response = api.read(id)
            if (response.isSuccessful && response.body() != null) {
                NetworkResult.Success(HotelMapper.toDomain(response.body()!!))
            } else {
                NetworkResult.Error(response.code(), response.message())
            }
        } catch (e: Exception) {
            NetworkResult.Error(null, e.message ?: "Unknown error")
        }
    }

    override suspend fun createHotel(request: CreateHotelRequest): NetworkResult<Hotel> {
        return try {
            val response = api.create(request)
            if (response.isSuccessful && response.body() != null) {
                NetworkResult.Success(HotelMapper.toDomain(response.body()!!))
            } else {
                NetworkResult.Error(response.code(), response.message())
            }
        } catch (e: Exception) {
            NetworkResult.Error(null, e.message ?: "Unknown error")
        }
    }

    override suspend fun updateHotel(id: Long, request: CreateHotelRequest): NetworkResult<Hotel> {
        return try {
            val response = api.update(id, request)
            if (response.isSuccessful && response.body() != null) {
                NetworkResult.Success(HotelMapper.toDomain(response.body()!!))
            } else {
                NetworkResult.Error(response.code(), response.message())
            }
        } catch (e: Exception) {
            NetworkResult.Error(null, e.message ?: "Unknown error")
        }
    }

    override suspend fun deleteHotel(id: Long): NetworkResult<Unit> {
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
