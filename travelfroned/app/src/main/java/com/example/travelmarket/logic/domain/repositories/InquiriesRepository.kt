package com.example.travelmarket.logic.domain.repositories

import com.example.travelmarket.core.network.NetworkResult
import com.example.travelmarket.logic.data.models.request.inquiries.CreateInquiryRequest
import com.example.travelmarket.logic.data.models.request.inquiries.UpdateInquiryRequest
import com.example.travelmarket.logic.domain.models.Inquiry

interface InquiriesRepository {
    suspend fun getInquiries(): NetworkResult<List<Inquiry>>
    suspend fun getInquiryById(id: Long): NetworkResult<Inquiry>
    suspend fun createInquiry(request: CreateInquiryRequest): NetworkResult<Inquiry>
    suspend fun updateInquiry(id: Long, request: UpdateInquiryRequest): NetworkResult<Inquiry>
    suspend fun deleteInquiry(id: Long): NetworkResult<Unit>
}


/**
 * Repositorio de solicitudes/consultas.
 *
 * USO EN APP MÓVIL USUARIO:
 *   - getInquiries()         // ✅ Listar consultas (GET)
 *   - getInquiryById()       // ✅ Detalle de consulta (GET)
 *
 * SOLO ADMIN (NO USAR EN VISTAS USUARIO):
 *   - createInquiry()
 *   - updateInquiry()
 *   - deleteInquiry()
 */