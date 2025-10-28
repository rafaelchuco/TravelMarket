package com.example.travelmarket.logic.domain.repositories

import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.data.models.request.inquiries.CreateInquiryRequest
import com.example.travelmarket.logic.data.models.request.inquiries.UpdateInquiryRequest
import com.example.travelmarket.logic.domain.models.Inquiry

/**
 * Repositorio de Inquiries (Consultas de Usuarios).
 *
 * USO PARA USUARIOS:
 *   - createInquiry()    // ✅ Crear consulta (POST sin autenticación)
 *   - getInquiryById()   // ✅ Ver detalle de una consulta (GET)
 *
 * SOLO PARA ADMIN:
 *   - getInquiries()     // ⚠️ Listar todas las consultas
 *   - updateInquiry()    // ⚠️ Actualizar consulta
 *   - deleteInquiry()    // ⚠️ Eliminar consulta
 */
interface InquiriesRepository {
    suspend fun getInquiries(): NetworkResult<List<Inquiry>>
    suspend fun getInquiryById(id: Long): NetworkResult<Inquiry>
    suspend fun createInquiry(request: CreateInquiryRequest): NetworkResult<Inquiry>
    suspend fun updateInquiry(id: Long, request: UpdateInquiryRequest): NetworkResult<Inquiry>
    suspend fun deleteInquiry(id: Long): NetworkResult<Unit>
}
