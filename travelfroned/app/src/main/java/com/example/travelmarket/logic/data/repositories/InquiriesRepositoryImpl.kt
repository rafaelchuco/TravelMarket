package com.example.travelmarket.logic.data.repositories

import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.data.mappers.InquiryMapper
import com.example.travelmarket.logic.data.models.request.inquiries.CreateInquiryRequest
import com.example.travelmarket.logic.data.models.request.inquiries.UpdateInquiryRequest
import com.example.travelmarket.logic.data.remote.inquiries.InquiriesApiService
import com.example.travelmarket.logic.domain.models.Inquiry
import com.example.travelmarket.logic.domain.repositories.InquiriesRepository
import javax.inject.Inject

class InquiriesRepositoryImpl @Inject constructor(
    private val api: InquiriesApiService
) : InquiriesRepository {

    override suspend fun getInquiries(): NetworkResult<List<Inquiry>> {
        return try {
            val response = api.list()
            if (response.isSuccessful && response.body() != null) {
                // ✅ CAMBIADO: usar .results
                val inquiries = response.body()!!.results?.map { InquiryMapper.toDomain(it) } ?: emptyList()
                NetworkResult.Success(inquiries)
            } else {
                NetworkResult.Error(response.code(), response.message())
            }
        } catch (e: Exception) {
            NetworkResult.Error(null, e.message ?: "Unknown error")
        }
    }

    override suspend fun getInquiryById(id: Long): NetworkResult<Inquiry> {
        return try {
            val response = api.read(id)
            if (response.isSuccessful && response.body() != null) {
                NetworkResult.Success(InquiryMapper.toDomain(response.body()!!))
            } else {
                NetworkResult.Error(response.code(), response.message())
            }
        } catch (e: Exception) {
            NetworkResult.Error(null, e.message ?: "Unknown error")
        }
    }

    override suspend fun createInquiry(request: CreateInquiryRequest): NetworkResult<Inquiry> {
        return try {
            val response = api.create(request)
            if (response.isSuccessful && response.body() != null) {
                NetworkResult.Success(InquiryMapper.toDomain(response.body()!!))
            } else {
                NetworkResult.Error(response.code(), response.message())
            }
        } catch (e: Exception) {
            NetworkResult.Error(null, e.message ?: "Unknown error")
        }
    }

    override suspend fun updateInquiry(id: Long, request: UpdateInquiryRequest): NetworkResult<Inquiry> {
        return try {
            val response = api.update(id, request)
            if (response.isSuccessful && response.body() != null) {
                NetworkResult.Success(InquiryMapper.toDomain(response.body()!!))
            } else {
                NetworkResult.Error(response.code(), response.message())
            }
        } catch (e: Exception) {
            NetworkResult.Error(null, e.message ?: "Unknown error")
        }
    }

    override suspend fun deleteInquiry(id: Long): NetworkResult<Unit> {
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
