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

    /**
     * ✅ Obtener SOLO las consultas del usuario logueado
     * Endpoint: GET /api/inquiries/my-inquiries/
     */
    override suspend fun getInquiries(): NetworkResult<List<Inquiry>> {
        return try {
            val response = api.getMyInquiries()  // ✅ Cambio aquí
            if (response.isSuccessful && response.body() != null) {
                val inquiries = response.body()!!.consultas?.map { inquiry ->
                    InquiryMapper.toDomain(inquiry)
                } ?: emptyList()
                NetworkResult.Success(data = inquiries)
            } else {
                NetworkResult.Error(
                    message = response.message() ?: "Error al cargar consultas",
                    code = response.code()
                )
            }
        } catch (e: Exception) {
            NetworkResult.Error(message = e.message ?: "Error de conexión")
        }
    }

    /**
     * ✅ Obtener una consulta por ID
     * Endpoint: GET /api/inquiries/{id}/
     */
    override suspend fun getInquiryById(id: Long): NetworkResult<Inquiry> {
        return try {
            val response = api.read(id)
            if (response.isSuccessful && response.body() != null) {
                NetworkResult.Success(data = InquiryMapper.toDomain(response.body()!!))
            } else {
                NetworkResult.Error(
                    message = response.message() ?: "Consulta no encontrada",
                    code = response.code()
                )
            }
        } catch (e: Exception) {
            NetworkResult.Error(message = e.message ?: "Error de conexión")
        }
    }

    /**
     * ✅ Crear consulta (sin autenticación)
     * Endpoint: POST /api/inquiries/
     */
    override suspend fun createInquiry(request: CreateInquiryRequest): NetworkResult<Inquiry> {
        return try {
            val response = api.create(request)
            if (response.isSuccessful && response.body() != null) {
                val inquiry = response.body()!!.consulta
                if (inquiry != null) {
                    NetworkResult.Success(data = InquiryMapper.toDomain(inquiry))
                } else {
                    NetworkResult.Error(message = "No se recibió la consulta creada")
                }
            } else {
                NetworkResult.Error(
                    message = response.message() ?: "Error al crear consulta",
                    code = response.code()
                )
            }
        } catch (e: Exception) {
            NetworkResult.Error(message = e.message ?: "Error de conexión")
        }
    }

    /**
     * ✅ Actualizar consulta (ADMIN)
     * Endpoint: PUT /api/inquiries/{id}/
     */
    override suspend fun updateInquiry(id: Long, request: UpdateInquiryRequest): NetworkResult<Inquiry> {
        return try {
            val response = api.update(id, request)
            if (response.isSuccessful && response.body() != null) {
                NetworkResult.Success(data = InquiryMapper.toDomain(response.body()!!))
            } else {
                NetworkResult.Error(
                    message = response.message() ?: "Error al actualizar consulta",
                    code = response.code()
                )
            }
        } catch (e: Exception) {
            NetworkResult.Error(message = e.message ?: "Error de conexión")
        }
    }

    /**
     * ✅ Eliminar consulta (ADMIN)
     * Endpoint: DELETE /api/inquiries/{id}/
     */
    override suspend fun deleteInquiry(id: Long): NetworkResult<Unit> {
        return try {
            val response = api.delete(id)
            if (response.isSuccessful) {
                NetworkResult.Success(data = Unit)
            } else {
                NetworkResult.Error(
                    message = response.message() ?: "Error al eliminar consulta",
                    code = response.code()
                )
            }
        } catch (e: Exception) {
            NetworkResult.Error(message = e.message ?: "Error de conexión")
        }
    }
}
